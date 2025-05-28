package Practica6

import java.util.concurrent.locks.ReentrantLock
import scala.util.Random

object Barca{

  private var nIphone = 0
  private var nAndroid = 0
  private val lock = new ReentrantLock(true)
  private val cIphone = lock.newCondition()
  private val cAndroid = lock.newCondition()
  private var bajan = false
  private var viaje = false

  def paseoIphone(id:Int) =  {
    lock.lock()
    try {
      while (nIphone + 1 == 3 || viaje || bajan) cIphone.await()
      nIphone += 1
      log(s"Estudiante IPhone $id se sube a la barca. Hay: iphone=$nIphone,android=$nAndroid ")
      if (nAndroid + nIphone == 4) {
        viaje = true
      }
      if (viaje) {
        log(s"Empieza el viaje....")
        Thread.sleep(Random.nextInt(200))
        log(s"fin del viaje....")
        bajan = true
        viaje = false
        cIphone.signalAll()
        cAndroid.signalAll()
      }
      while (!bajan) cIphone.await()
      nIphone -= 1
      log(s"Estudiante IPhone $id se baja de la barca. Hay: iphone=$nIphone,android=$nAndroid ")
      if (nIphone + nAndroid == 0) {
        cIphone.signalAll()
        cAndroid.signalAll()
      }
    } finally {
      lock.unlock()
    }
  }

  def paseoAndroid(id:Int) =  {
    lock.lock()
    try {
      while (nAndroid + 1 == 3 || viaje || bajan) cAndroid.await()
      nAndroid += 1
      log(s"Estudiante Android $id se sube a la barca. Hay: iphone=$nIphone,android=$nAndroid ")
      if (nAndroid + nIphone == 4) {
        viaje = true
      }
      if (viaje == true) {
        log(s"Empieza el viaje....")
        Thread.sleep(Random.nextInt(200))
        log(s"fin del viaje....")
        bajan = true
        viaje = false
        cIphone.signalAll()
        cAndroid.signalAll()
      }
      while (!bajan) cAndroid.await()
      nAndroid -= 1
      log(s"Estudiante Android $id se baja de la barca. Hay: iphone=$nIphone,android=$nAndroid ")
      if (nIphone + nAndroid == 0) {
        bajan = false
        cIphone.signalAll()
        cAndroid.signalAll()
      }
    } finally {
      lock.unlock()
    }
  }
}
object Ejercicio5 {

  def main(args:Array[String]) = {
    val NPhones = 10
    val NAndroid = 10
    val iphone = new Array[Thread](NPhones)
    val android = new Array[Thread](NAndroid)
    for (i<-iphone.indices)
      iphone(i) = thread{
     //   while (true){
          Thread.sleep(Random.nextInt(400))
          Barca.paseoIphone(i)
        //    }
      }
    for (i <- android.indices)
      android(i) = thread {
     //   while (true) {
          Thread.sleep(Random.nextInt(400))
          Barca.paseoAndroid(i)
     //   }
      }
  }
}
