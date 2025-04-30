package Practica5

import java.util.concurrent.*
import scala.util.Random

class Nido(B: Int) {
  private var plato = 0
  private val mutex = new Semaphore(1)
  private val suelta = new Semaphore(1) // Controla el límite de bichitos en el plato
  private val comebb = new Semaphore(0) // Controla si hay bichitos disponibles

  def cojoBichito(i: Int): Unit = {
    comebb.acquire() // Espera a que haya bichitos disponibles
    mutex.acquire() // Protege el acceso a `plato`
    plato -= 1
    log(s"Bebé $i coge un bichito. Quedan $plato bichitos")
    if (plato == B - 1) suelta.release() // Permite que se añadan más bichitos si el plato no está lleno
    if (plato > 0) comebb.release()
    mutex.release()
  }

  def pongoBichito(i: Int): Unit = {
    suelta.acquire() // Espera a que haya espacio en el plato
    mutex.acquire() // Protege el acceso a `plato`
    plato += 1
    log(s"Papá $i pone un bichito. Quedan $plato bichitos")
    if (plato == 1) comebb.release() // Notifica que hay bichitos disponibles
    if (plato < B) suelta.release()
    mutex.release()
  }
}

object Ejercicio7 {
  def main(args: Array[String]): Unit = {
    val N = 10
    val nido = new Nido(5)
    val bebe = new Array[Thread](N)
    for (i <- bebe.indices) {
      bebe(i) = thread {
        while (true) {
          nido.cojoBichito(i)
          Thread.sleep(Random.nextInt(600)) // Simula el tiempo de consumo
        }
      }
    }
    val papa = new Array[Thread](2)
    for (i <- papa.indices) {
      papa(i) = thread {
        while (true) {
          Thread.sleep(Random.nextInt(100)) // Simula el tiempo de recolección
          nido.pongoBichito(i)
        }
      }
    }
  }
}