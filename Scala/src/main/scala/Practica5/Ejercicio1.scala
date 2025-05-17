package Practica5

import java.util.concurrent.*
import scala.util.Random

object mediciones {
  // CS-Sensor-i: sensor i no puede volver a medir hasta que el trabajador no ha
  // terminado de procesar las medidas anteriores
  // CS-Trabajador: no puede realizar su tarea hasta que no están las
  // tres mediciones

  private val mutex = new Semaphore(1)
  private val espera = new Semaphore(0)
  private val sensores = Array.fill(3)(new Semaphore(1))
  private var ndatos = 0

  def nuevaMedicion(id: Int) = {
    // ...
    sensores(id).acquire()
    mutex.acquire()
    log(s"Sensor $id almacena su medición" )
    ndatos += 1
    if (ndatos == 3) espera.release()
    mutex.release()
    // ...
  }

  def leerMediciones() = {
    // ...
    espera.acquire()
    mutex.acquire()
    log(s"El trabajador recoge las mediciones")
    ndatos = 0
    mutex.release()
    // ...
  }

  def finTarea() = {
    // ...
    log(s"El trabajador ha terminado sus tareas")
    for(i <- 0 until sensores.length)
      sensores(i).release()
    // ...
  }
}

object Ejercicio1 {
  def main(args: Array[String]) =
    val sensor = new Array[Thread](3)

    for (i <- 0 until sensor.length)
      sensor(i) = thread {
        while (true)
          Thread.sleep(Random.nextInt(100)) // midiendo
          mediciones.nuevaMedicion(i)
      }

    val trabajador = thread {
      while (true)
        mediciones.leerMediciones()
        Thread.sleep(Random.nextInt(100)) // realizando la tarea
        mediciones.finTarea()
    }
}
