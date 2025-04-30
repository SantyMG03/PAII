package EjercicioT6

import java.util.concurrent.Semaphore
import Practica4.*
import scala.util.Random

@main
def Impresoras(): Unit = {
  val libres = 2 // Número de impresoras
  val workers = 10 // Número de trabajadores
  val sem = Array.fill(libres)(new Semaphore(1)) // Inicializar semáforos para las impresoras

  for (i <- 0 until workers) {
    thread {
      while (true) {
        var id = Random.nextInt(libres) // Seleccionar una impresora aleatoria
        if(!sem(id).tryAcquire()){
          id = id + 1 % libres
        }
        sem(id).acquire() // Adquirir el semáforo de la impresora seleccionada
        log(s"Trabajador ${Thread.currentThread().getName} está imprimiendo en la impresora $id")
        Thread.sleep(Random.nextInt(1000)) // Simular tiempo de impresión
        log(s"Trabajador ${Thread.currentThread().getName} ha terminado de imprimir en la impresora $id")
        sem(id).release() // Liberar el semáforo de la impresora seleccionada
      }
    }
  }
}