package Practica5

import java.util.concurrent.*
import scala.util.Random

object gestorAgua {
  
  private val molecula = new Semaphore(0) // Para la ejecucion si todavia no se puede formar agua
  private val mutex = new Semaphore(1) // Para la exclusión mutua entre los hidrógenos y el oxígeno

  // Binarios para bloquear hidrógenos y oxígenos
  private val esperaH = new Semaphore(1) // para despertar hidrógenos
  private val esperaO = new Semaphore(1) // para despertar oxígeno

  // Variables de control
  private var numH = 0
  private var numO = 0


  def oxigeno(id: Int) = {
    esperaO.acquire()
    mutex.acquire()
    numO += 1
    log(s"Oxígeno $id quiere formar una molécula")
    if (numH + numO < 3) {
      mutex.release()
      molecula.acquire()
      mutex.acquire()
    } else {
      log(s"      Molecula formada!!! ")
    }
    numO -= 1
    if (numH + numO > 0) {
      molecula.release()
    } else {
      esperaO.release()
      esperaH.release()
    }
    mutex.release()
  }

  def hidrogeno(id: Int) = {
    esperaH.acquire()
    mutex.acquire()
    numH += 1
    log(s"Hidrógeno $id quiere formar una molécula")
    if (numH < 2) {
      esperaH.release()
    }
    if (numH + numO < 3) {
      mutex.release()
      molecula.acquire()
      mutex.acquire()
    } else {
      log(s"      Molecula formada!!!")
    }
    numH -= 1
    if (numH + numO > 0){
      molecula.release()
    } else {
      esperaO.release()
      esperaH.release()
    }
    mutex.release()
  }
}
object Ejercicio5 {
  def main(args:Array[String]) =
    val N = 5
    val hidrogeno = new Array[Thread](2*N)
    for (i<-0 until hidrogeno.length)
      hidrogeno(i) = thread{
        Thread.sleep(Random.nextInt(500))
        gestorAgua.hidrogeno(i)
      }
    val oxigeno = new Array[Thread](N)
    for(i <- 0 until oxigeno.length)
      oxigeno(i) = thread {
        Thread.sleep(Random.nextInt(500))
        gestorAgua.oxigeno(i)
      }
    hidrogeno.foreach(_.join())
    oxigeno.foreach(_.join())
    log("Fin del programa")
}
