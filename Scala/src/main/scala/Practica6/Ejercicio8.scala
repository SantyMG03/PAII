package Practica6

import scala.util.Random

object gestorAgua {
  //CS-Hid1: El hidrógeno que quiere formar una molécula espera si ya hay dos hidrógenos
  //CS-Hid2: Un hidrógeno debe esperar a los otros dos átomos para formar la molécula
  //CS-Ox1: El oxígeno que quiere formar una molécula espera si ya hay un oxígeno
  //CS-Ox2: El oxígeno debe esperar a los otros dos átomos para formar la molécula

  private var puertaH = true
  private var puertaO = true
  private var molecula = false
  private var hEsperando = 0;
  private var oEsperando = 0;

  def hidrogeno(id: Int) = synchronized {
    while (!puertaH) wait()

    log(s"Hidrógeno $id quiere formar una molécula")
    hEsperando += 1

    if (hEsperando == 2) puertaH = false

    if (hEsperando + oEsperando < 3) {
      while (!molecula) wait()
    } else {
      molecula = true
      notifyAll()
    }

    log(s"Hidrógeno $id participa en la formación de la molécula")
    hEsperando -= 1

    if (oEsperando + hEsperando == 0) {
      log("      ¡Molécula formada!")
      molecula = false
      puertaO = true
      puertaH = true
    }
    notifyAll()
  }


  def oxigeno(id: Int) = synchronized {
    while (!puertaO) wait()

    log(s"Oxígeno $id quiere formar una molécula")
    oEsperando += 1
    puertaO = false

    if (hEsperando + oEsperando < 3) {
      while (!molecula) wait()
    } else {
      molecula = true
      notifyAll()
    }

    log(s"Oxígeno $id participa en la formación de la molécula")
    oEsperando -= 1

    if (oEsperando + hEsperando == 0) {
      log("      ¡Molécula formada!")
      molecula = false
      puertaO = true
      puertaH = true
    }
    notifyAll()
  }

}
object Ejercicio8 {

  def main(args:Array[String]) =
    val N = 1
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
