package Practica6

import scala.util.Random

class Coche(C:Int) extends Thread{
  //CS-pasajero1: si el coche está lleno, un pasajero no puede subir al coche hasta que haya terminado
  //el viaje y se hayan bajado los pasajeros de la vuelta actual
  //CS-pasajero2: un pasajero que está en el coche no se puede bajarse hasta que haya terminado el viaje
  //CS-coche: el coche espera a que se hayan subido C pasajeros para dar una vuelta
  private var numPas = 0
  private var entrada = true // Permite acceso al coche
  private var fin = false // Permite la salida del coche
  private var lleno = false // Permite el viaje

  def nuevoPaseo(id:Int)= synchronized {
    while (!entrada) wait() // Si la puerta de entrada no esta abierta entonces esperas
    numPas += 1
    log(s"El pasajero $id sube al coche. Hay $numPas pasajeros")
    if (numPas == C) { // Cuando se alcanza la capacidad maxima cierro la entrada y aviso que esta lleno
      entrada = false
      lleno = true
      notify() // Aviso al espera lleno
    }

    while(!fin) wait() // Mientras que no haya acabado el viaje esperas
    numPas -= 1
    log(s"El pasajero $id baja del coche. Hay $numPas pasajeros")
    if (numPas == 0) { // Cuando se vacia por completo abro la entrada y cierro la salida 
      fin = false 
      entrada = true
      notifyAll() // Aviso a todos los dormidos para que puedan volver a entrar
    }
  }

  def esperaLleno = synchronized {
    while (!lleno) wait() // Mientras el coche no este lleno no puedo iniciar el viaje
    log(s"        Coche lleno!!! empieza el viaje....")
    lleno = false
  }

  def finViaje = synchronized {
    log(s"        Fin del viaje... :-(")
    fin = true // Permite la bajada
    notifyAll() // Aviso a todos los dormidos esperando a bajar
  }

  override def run = {
    while (true){
      esperaLleno
      Thread.sleep(Random.nextInt(Random.nextInt(500))) //el coche da una vuelta
      finViaje
    }
  }
}
object Ejercicio4 {
  def main(args:Array[String])=
    val coche = new Coche(5)
    val pasajero = new Array[Thread](20)
    coche.start()
    for (i<-0 until pasajero.length)
      pasajero(i) = thread{
   //     while (true)
          Thread.sleep(Random.nextInt(500))
          coche.nuevoPaseo(i)
      }

}
