package Practica6

import scala.util.Random

class Bandeja(R:Int){

  private var raciones = 0


  def quieroRacion(id:Int)= synchronized {
    while (raciones == 0) wait() // Si no hay raciones esperas
    raciones -= 1
    log(s"Niño $id ha cogido una ración. Quedan $raciones")
    if (raciones == 0) notify() // Aviso al unico dormido (pastelero)
  }
  
  def tarta()= synchronized {
    while(raciones != 0) wait() // Si hay raciones esperas
    raciones = R
    log("El pastelero pone una nueva tarta.")
    notifyAll() // Aviso a todos los comensales
  }
}
object Ejercicio6 {

  def main(args:Array[String]):Unit = {
    val R = 5
    val N = 10
    val bandeja = new Bandeja(R)
    var niño = new Array[Thread](N)
    for (i<-niño.indices)
      niño(i) = thread{
        while (true){
          Thread.sleep(Random.nextInt(500))
          bandeja.quieroRacion(i)
        }
      }
    val pastelero = thread{
      while (true){
        Thread.sleep(Random.nextInt(100))
        bandeja.tarta()
      }
    }
  }


}
