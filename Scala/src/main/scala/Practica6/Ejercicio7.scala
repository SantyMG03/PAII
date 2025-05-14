package Practica6

import scala.util.Random

object Guarderia{
  private var nBebe  = 0
  private var nAdulto = 0
  //nBebe<=3*Adulto (3bebes,1adulto),(4bebes,2adulto)
  

  def entraBebe(id:Int) = synchronized {
    while (nBebe + 1 > 3 * nAdulto || nAdulto == 0) wait()
    nBebe += 1
    log(s"Ha llegado un bebé. Bebés=$nBebe, Adultos=$nAdulto")
  }
  def saleBebe(id:Int) = synchronized {
    nBebe -= 1
    log(s"Ha salido un bebé. Bebés=$nBebe, Adultos=$nAdulto")
    notifyAll()
  }
  def entraAdulto(id:Int) = synchronized {
    nAdulto += 1
    log(s"Ha llegado un adulto. Bebés=$nBebe, Adultos=$nAdulto")
    notifyAll()
    
  }
  def saleAdulto(id:Int) = synchronized {
    while (nBebe > 3 * (nAdulto - 1)) wait()
    nAdulto -= 1
    log(s"Ha salido un adulto. Bebés=$nBebe, Adultos=$nAdulto")
  }
}
object Ejercicio7 {
  def main(args:Array[String]):Unit={
    val NB = 15
    val NA = 5
    val bebe = new Array[Thread](NB)
    val adulto = new Array[Thread](NA)
    for (i <- bebe.indices)
      bebe(i) = thread {
        while (true) {
          Thread.sleep(Random.nextInt(700))
          Guarderia.entraBebe(i)
          Thread.sleep(Random.nextInt(500))
          Guarderia.saleBebe(i)
        }
      }
    for (i <- adulto.indices)
      adulto(i) = thread {
        while (true) {
          Thread.sleep(Random.nextInt(700))
          Guarderia.entraAdulto(i)
          Thread.sleep(Random.nextInt(500))
          Guarderia.saleAdulto(i)
        }
      }
  }

}
