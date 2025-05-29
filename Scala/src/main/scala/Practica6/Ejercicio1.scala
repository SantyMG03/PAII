package Practica6

import java.util.concurrent.locks.ReentrantLock
import scala.util.Random
import scala.collection.mutable

class Buffer(ncons:Int,tam:Int){
  //ncons-número de consumidores
  //tam-tamaño del buffer
  private val buffer = new Array[Int](tam)
  private val leidosPorPos = Array.fill(tam)(mutable.Set.empty[Int])
  private val lock = new ReentrantLock(true)
  private val cGenera = lock.newCondition()
  private val cExtrae = lock.newCondition()

  private var writeIndex = 0
  
  def nuevoDato(dato:Int) = {
    //el productor pone un nuevo dato 
    lock.lock()
    try {
      while (leidosPorPos(writeIndex).size < ncons && leidosPorPos(writeIndex).nonEmpty) {
        cGenera.await()
      }

      buffer(writeIndex) = dato
      leidosPorPos(writeIndex).clear() // Reiniciar seguimiento de lectura
      log(s"Productor almacena $dato en posición $writeIndex")

      writeIndex = (writeIndex + 1) % tam
      cExtrae.signalAll()
    } finally {
      lock.unlock()
    }
  }

  def extraerDato(id:Int):Int = {
    lock.lock()
    try {
      var datoOpt: Option[(Int, Int)] = None // (dato, pos)

      while (datoOpt.isEmpty) {
        for (i <- buffer.indices if datoOpt.isEmpty) {
          if (!leidosPorPos(i).contains(id) && leidosPorPos(i).nonEmpty ||
            (leidosPorPos(i).isEmpty && buffer(i) != 0)) {
            leidosPorPos(i) += id
            datoOpt = Some((buffer(i), i))
            log(s"Consumidor $id lee ${buffer(i)} de posición $i")

            if (leidosPorPos(i).size == ncons) {
              // Todos han leído, liberar la posición
              log(s"Elemento ${buffer(i)} totalmente leído, se libera posición $i")
              // No limpiamos buffer(i) para evitar problemas con valores = 0; solo lo dejamos disponible
              cGenera.signalAll()
            }
          }
        }

        if (datoOpt.isEmpty) {
          cExtrae.await()
        }
      }

      datoOpt.get._1
    } finally {
      lock.unlock()
    }
  }
}

object Ejercicio1 {

  def main(args:Array[String]):Unit = {
    val ncons = 4
    val tam = 3
    val nIter = 10
    val buffer  = new Buffer(ncons,tam)
    val consumidor = new Array[Thread](ncons)
    for (i<-consumidor.indices)
      consumidor(i) = thread{
        for (j<-0 until nIter)
          val dato = buffer.extraerDato(i)
          Thread.sleep(Random.nextInt(200))
      }
    val productor = thread{
      for (i<-0 until nIter)
        Thread.sleep(Random.nextInt(50))
        buffer.nuevoDato(i+1)
    }
  }

}
