package Practica6

import java.util.concurrent.locks.ReentrantLock
import scala.util.Random

object Parejas{

  private val lock = new ReentrantLock(true)
  private val chombre = lock.newCondition()
  private val cmujer = lock.newCondition()
  private var hombreEspera = false
  private var mujerEspera = false

  def llegaHombre(id:Int) =  {
    lock.lock()
    try {
      while(hombreEspera) chombre.await() // Si ya ha llegado un hombre los siguientes esperar
      log(s"Hombre $id quiere formar pareja")
      hombreEspera = true
      if (mujerEspera) { // Si el hombre es el segundo de la pareja en llegar
        log(s"Se forma una pareja")
        hombreEspera = false
        mujerEspera = false
        cmujer.signal()
        chombre.signal() // Aviso a todos los hombres y mujeres que estan esperando
      }
    } finally {
      lock.unlock()
    }
  }

  def llegaMujer(id: Int) =  {
    lock.lock()
    try {
      while(mujerEspera) cmujer.await() // Igual que el hombre pero al reves que con hombres
      log(s"Mujer $id quiere formar pareja")
      mujerEspera = true
      if (hombreEspera) {
        log(s"Se forma una pareja")
        hombreEspera = false
        mujerEspera = false
        cmujer.signal()
        chombre.signal()
      }
    } finally {
      lock.unlock()
    }
  }
}
object Ejercicio3 {

  def main(args:Array[String]):Unit = {
    val NP = 10
    val mujer = new Array[Thread](NP)
    val hombre = new Array[Thread](NP)
    for (i<-mujer.indices)
      mujer(i) = thread{
        Parejas.llegaMujer(i)
      }
    for (i <- hombre.indices)
      hombre(i) = thread {
        Parejas.llegaHombre(i)
      }
  }

}
