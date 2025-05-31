package Practica6

import java.util.concurrent.locks.ReentrantLock
import scala.collection.mutable.ListBuffer
import scala.util.Random

class Recursos(rec:Int) {

  private var numRec = rec
  private var cola = new ListBuffer[Int]
  private val lock = new ReentrantLock(true)
  private val cEntra = lock.newCondition()
  private val cSalida = lock.newCondition()
  
  def pidoRecursos(id:Int,num:Int) =  {
    //proceso id solicita num recursos
    lock.lock()
    try {
      log(s"Proceso $id pide $num recursos.")
      while (numRec < num) cEntra.await() // Si no hay recursos espera
      numRec -= num
      cola.append(id)
      log(s"Proceso $id coge $num recursos. Quedan $numRec")
      cSalida.signalAll() // Aviso que q ya se puede salir
    } finally {
      lock.unlock()
    }
  }

  def libRecursos(id:Int,num:Int) =  {
    //proceso id devuelve num recursos
    lock.lock()
    try {
      while (cola.head != id) cSalida.await() // Si no hay nadie que haya tomado recursos
      numRec += num
      cola.remove(0)
      log(s"Proceso $id devuelve $num recursos. Quedan $numRec")
      cEntra.signalAll() // Aviso a todos los que estaban esperando a tomar recursos
    } finally {
      lock.unlock()
    }
  }
}
object Ejercicio2 {

  def main(args:Array[String]):Unit = {
    val rec = 5
    val numProc = 10
    val recursos = new Recursos(rec)
    val proceso = new Array[Thread](numProc)
    for (i<-proceso.indices)
      proceso(i) = thread{
      //  while (true){
          val r = Random.nextInt(rec)+1
          recursos.pidoRecursos(i,r)
          Thread.sleep(Random.nextInt(300))
          recursos.libRecursos(i,r)
     //   }
      }
  }
}
