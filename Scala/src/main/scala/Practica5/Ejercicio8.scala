package Practica5

import java.util.concurrent.*
import scala.util.Random

class Olla(R: Int) {
  // CS-caníbal i: no coge una ración de la olla si está vacía
  val comen = new Semaphore(1)
  // CS-cocinero: no cocina un nuevo explorador hasta que la olla está vacía
  val cocina = new Semaphore(0)
  private var olla = R // inicialmente llena
  // ...

  def racion(i: Int) = {
    // caníbal i coge una ración de la olla
    // ...
    comen.acquire()
    olla -= 1
    log(s"Caníbal $i coge una ración de la olla. Quedan $olla raciones.")
    if (olla > 0) comen.release()
    else cocina.release()
    // ...
  }

  def dormir = {
    // cocinero espera a que la olla esté vacía
    cocina.acquire()
    // ...
  }

  def llenarOlla = {
    // ...
    olla = R
    log(s"El cocinero llena la olla. Quedan $olla raciones.")
    comen.release()
    // ...
  }
}

object Ejercicio8 {
  def main(args: Array[String]): Unit = {
    val NCan = 20
    val olla = new Olla(5)
    val canibal = new Array[Thread](NCan)
    for (i <- canibal.indices)
      canibal(i) = thread {
        while (true)
          Thread.sleep(Random.nextInt(500))
          olla.racion(i)
      }
    val cocinero = thread {
      while (true)
        olla.dormir
        Thread.sleep(500) // cocinando
        olla.llenarOlla
    }
  }
}
