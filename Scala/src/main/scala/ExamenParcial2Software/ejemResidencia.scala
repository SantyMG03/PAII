package ExamenParcial2Software

import java.util.concurrent.Semaphore
import scala.util.Random

class Salon(cap: Int) {

  /*
   * Condiciones sincronización del ejercicio 1
   * CS-Dec1: El decano no entra en la salon hasta que lo avisan de que se ha excedido el aforo
   * CS-Dec2: El decano espera a que se vacíe el salón para volver a dormir
   * CS-Est1: Un estudiante no puede entrar si el decano está en ella
   */

  private val decano = new Semaphore(0)
  private val ultimo = new Semaphore(0)
  private val entran = new Semaphore(1)
  private val salen = new Semaphore(1)

  private var despierto = false
  private var fiesteros = 0;
  /*
  * Condiciones sincronización del ejercicio 2
  * CS-Est2: Un estudiante que está en la fiesta no puede salir si el decano ha sido avisado
  */

  def llegoAFiesta(id: Int) = {
    //El estudiante llama a este método cuando quiere entrar en la fiesta
    if (!despierto){
      entran.acquire()
      fiesteros += 1
      log(s"Estudiante $id llega a la fiesta. Hay $fiesteros fiesteros")
      if (fiesteros == cap) { // Aviso al decano
        log(s"El estudiante $id avisa al decano")
        decano.release()
      }
      entran.release()
    }
  }

  def salgoFiesta(id: Int) = {
    //estudiante id llama a este método cuando quiere abandonar la fiesta
    if (fiesteros > 0) {
      salen.acquire()
      fiesteros -= 1
      log(s"Estudiante $id sale de la fiesta. Quedan $fiesteros fiesteros")
      if (fiesteros == 0 && despierto) ultimo.release()
      salen.release()
    }
  }

  def meDuermo() = {
    //El decano llama a este método cuando quiere dormir
    //se despierta cuando le avisan de que se ha superado el aforo
    decano.acquire() // Decano duerme hata que lo avisan
    log(s"Decano: me despierto")
    despierto = true
    entran.acquire() // Decano entra al salon y bloquea la entrada a los estudiantes
    log(s"Decano: entro en la sala")
  }

  def esperoTodosFuera() = {
    //El decano llama a este método para esperar a que salgan del solón todos los estudiantes
    log(s"Decano: espero que salgan todos")
    ultimo.acquire()
    log(s"Decano: me voy otra vez a dormir")
    despierto = false
    entran.release()
  }
}

object ejemResidencia {

  def main(args: Array[String]): Unit = {
    val R = 20
    val Cap = 5
    val F = 1
    val salon = new Salon(Cap)
    val estudiante = new Array[Thread](R)
    for (i <- estudiante.indices)
      estudiante(i) = thread {
        for (j <- 0 until F)
          Thread.sleep(Random.nextInt(700))
          salon.llegoAFiesta(i)
          Thread.sleep(100)
          salon.salgoFiesta(i)
          Thread.sleep(700)
      }
    val decano = thread {
      var fin = false
      while (!fin && !Thread.interrupted()) {
        try {
          Thread.sleep(Random.nextInt(200))
          salon.meDuermo()
          salon.esperoTodosFuera()
        } catch {
          case e: InterruptedException => fin = true
        }
      }
    }
    estudiante.foreach(_.join())
    decano.interrupt()
    decano.join()
    log(s"Todos los estudiantes y el decano se han ido a dormir")
  }
}
