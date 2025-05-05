package Practica5

import java.util.concurrent.*
import scala.util.Random

object gestorAgua {
  // CS-Hid1: El hidrógeno que quiere formar una molécula espera si ya hay dos hidrógenos
  // CS-Hid2: Un hidrógeno debe esperar a los otros dos átomos para formar la molécula
  // CS-Ox1: El oxígeno que quiere formar una molécula espera si ya hay un oxígeno
  // CS-Ox2: El oxígeno debe esperar a los otros dos átomos para formar la molécula

  private val mutex = new Semaphore(1) // protege las variables compartidas

  // Binarios para bloquear hidrógenos y oxígenos
  private val hReady = new Semaphore(0) // para despertar hidrógenos
  private val oReady = new Semaphore(0) // para despertar oxígeno

  // Semáforo binario para sincronizar la salida de los 3 participantes
  private val barrier = new Semaphore(0)

  // Variables de control
  private var waitingH = 0
  private var waitingO = 0


  def oxigeno(id: Int) = {
    log(s"Oxígeno $id quiere formar una molécula")

    mutex.acquire()
    waitingO += 1
    if (waitingH >= 2 && waitingO >= 1) {
      // Hay suficientes para formar una molécula
      waitingH -= 2
      waitingO -= 1
      hReady.release()
      hReady.release()
      oReady.release()
    }
    mutex.release()

    oReady.acquire()
    log(s"Oxígeno $id participa en la molécula")

    // Esperar a que todos lleguen
    barrier.release()
    barrier.release()
    barrier.release()

    // Esperar a que todos se liberen (solo uno lo hace: oxígeno)
    barrier.acquire()
    barrier.acquire()
    barrier.acquire()

    log(s"      Molécula formada!!!")
    log(s"Sale oxígeno $id")
  }

  def hidrogeno(id: Int) = {
    log(s"Hidrógeno $id quiere formar una molécula")

    mutex.acquire()
    waitingH += 1
    if (waitingH >= 2 && waitingO >= 1) {
      // Hay suficientes para formar una molécula
      waitingH -= 2
      waitingO -= 1
      hReady.release() // liberar un H
      hReady.release() // liberar otro H
      oReady.release() // liberar O
    }
    mutex.release()

    hReady.acquire()
    log(s"Hidrógeno $id participa en la molécula")

    // Esperar a que todos lleguen
    barrier.release() // este H está listo
    barrier.acquire() // espera a los otros dos
    log(s"Sale hidrógeno $id")
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
