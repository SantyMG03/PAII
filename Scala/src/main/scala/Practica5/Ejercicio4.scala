package Practica5

import java.util.concurrent.*
import scala.util.Random

class Coche(C: Int) extends Thread {
  
  private var numPas = 0
  private val viaje = Semaphore(0) // Permite el viaje del coche
  private val bajan = Semaphore(0) // Permite a los pasajeros bajarse del coche
  private val suben = Semaphore(1) // Permite a los pasajeros subir al coche
  // ...

  def nuevoPaseo(id: Int) = {
    // el pasajero id quiere dar un paseo en la montaña rusa
    suben.acquire()
    numPas += 1
    log(s"El pasajero $id se sube al coche. Hay $numPas pasajeros.")
    if (numPas == C) viaje.release()
    else suben.release()
    
    // Al coche no se puede subir nadie hasta que este vacio
    bajan.acquire()
    numPas -= 1
    log(s"El pasajero $id se baja del coche. Hay $numPas pasajeros.")
    if (numPas > 0) bajan.release()
    else suben.release()
  }

  def esperaLleno = {
    // el coche espera a que se llene para dar un paseo
    viaje.acquire()
    log(s"        Coche lleno!!! empieza el viaje....")
  }

  def finViaje = {
    // el coche indica que se ha terminado el viaje
    log(s"        Fin del viaje... :-(")
    bajan.release()
  }

  override def run = {
    while (true) {
      esperaLleno
      Thread.sleep(Random.nextInt(Random.nextInt(500))) // el coche da una vuelta
      finViaje
    }
  }
}

object Ejercicio4 {
  def main(args: Array[String]) = {
    val coche = new Coche(5)
    val pasajero = new Array[Thread](12)
    coche.start()
    for (i <- 0 until pasajero.length)
      pasajero(i) = thread {
        while (true)
          Thread.sleep(Random.nextInt(500)) // el pasajero se da una vuelta por el parque
          coche.nuevoPaseo(i)
      }
  }
}
