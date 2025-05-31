package Practica6

import java.util.concurrent.locks.ReentrantLock
import scala.util.Random

object Barca{

  private var nIphone = 0
  private var nAndroid = 0
  private val lock = new ReentrantLock(true)
  private val cIphone = lock.newCondition()
  private val cAndroid = lock.newCondition()
  private var puertaA = true
  private var puertaI = true
  private var bajan = false
  private var viaje = false

  def paseoIphone(id:Int) =  {
    lock.lock()
    try {
      while (viaje || bajan || !puertaI) cIphone.await()
      // Si estan viajando o bajando o la puertaI cerrada entonces esperan
      nIphone += 1
      if (nAndroid + nIphone == 3){ // Cuando la suma total de 3 -> CUIDADO
        if (nIphone == 3 || nAndroid == 2) puertaA = false // Si hay 2 Andr o 3 Ipho cierro la entrada de Andr
        if (nIphone == 2) puertaI = false // Si el numero de Ipho es 2 cierro la puerta
      }
      log(s"Estudiante IPhone $id se sube a la barca. Hay: iphone=$nIphone,android=$nAndroid ")
      if (nAndroid + nIphone == 4) { // Si hay 4 entonces viajo
        viaje = true
      }
      if (viaje) {
        log(s"Empieza el viaje....")
        Thread.sleep(Random.nextInt(200))
        log(s"fin del viaje....")
        bajan = true
        viaje = false
        cIphone.signalAll()
        cAndroid.signalAll() // Aviso a todos los Android y Iphone esperando a bajar
      }
      while (!bajan) cIphone.await() // Mientras no se puedan bajar esperan
      nIphone -= 1
      log(s"Estudiante IPhone $id se baja de la barca. Hay: iphone=$nIphone,android=$nAndroid ")
      if (nIphone + nAndroid == 0) {
        bajan = false
        puertaA = true
        puertaI = true
        cIphone.signalAll()
        cAndroid.signalAll() // Aviso a todos los Android y Iphone esperando a subir
      }
    } finally {
      lock.unlock()
    }
  }

  def paseoAndroid(id:Int) =  {
    lock.lock()
    try {
      // Mismo planteamiento pero cambiando los await por el contrario que en Iphone
      while (viaje || bajan || !puertaA) cAndroid.await()
      nAndroid += 1
      if (nAndroid + nIphone == 3){
        if (nAndroid == 3 || nIphone == 2) puertaI = false
        if (nAndroid == 2) puertaA = false
      }
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
        puertaA = true
        puertaI = true
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
