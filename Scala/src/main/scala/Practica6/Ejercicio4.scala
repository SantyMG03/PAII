package Practica6

import scala.util.Random

class Coche(C:Int) extends Thread{
  //CS-pasajero1: si el coche está lleno, un pasajero no puede subir al coche hasta que haya terminado
  //el viaje y se hayan bajado los pasajeros de la vuelta actual
  //CS-pasajero2: un pasajero que está en el coche no se puede bajarse hasta que haya terminado el viaje
  //CS-coche: el coche espera a que se hayan subido C pasajeros para dar una vuelta
  private var numPas = 0
  private var entrada = true
  private var fin = false
  private var lleno = false

  def nuevoPaseo(id:Int)= synchronized {
    while (!entrada) wait()
    numPas += 1
    log(s"El pasajero $id sube al coche. Hay $numPas pasajeros")
    if (numPas == C) {
      entrada = false
      lleno = true
      notify()
    }

    while(!fin) wait()
    numPas -= 1
    log(s"El pasajero $id baja del coche. Hay $numPas pasajeros")
    if (numPas == 0) {
      fin = false
      entrada = true
      notifyAll()
    }
  }

  def esperaLleno = synchronized {
    //el coche espera a que se llene para dar un paseo
    while (!lleno) wait()
    log(s"        Coche lleno!!! empieza el viaje....")
    lleno = false
  }

  def finViaje = synchronized {
    //el coche indica que se ha terminado el viaje
    log(s"        Fin del viaje... :-(")
    fin = true
    notifyAll()
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
