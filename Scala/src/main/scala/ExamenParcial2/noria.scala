/*
Tenemos una nueva noria en el parque de atracciones. La noria tiene N coches y cada coche puede llevar C pasajeros
(N y C son pasados como argumentos del constructor de la clase Noria). Los pasajeros esperan para subir a la noria,
se dan una vuelta y bajan. Sin embargo, el movimiento no es continuo, la noria irá parando cada uno de sus coches
en la puerta de salida para renovar sus pasajeros. En concreto, cuando el coche en la puerta está lleno, la noria
se mueve hasta colocar el siguiente coche en la puerta de salida, bajándose entonces los pasajeros en el coche
situado en ese momento en la salida. Cuando terminan de bajar los pasajeros del coche en la puerta de salida, el
coche vuelve a llenarse hasta completar su capacidad. El proceso se repite indefinidamente. Por ejemplo, para 3
coches de capacidad 2 la siguiente sería una traza válida.
*/

import concurrencia.{log, thread}

import java.util.concurrent.*
import scala.util.Random

class Noria(N: Int, C: Int) extends Thread {
  private val numPas = Array.fill(N)(0) // núm. de pasajeros en cada coche
  private var i = 0 // coche en la puerta

  private val suben = new Semaphore(1)
  private val bajan = new Semaphore(0)
  private val desplaza = new Semaphore(0)

  def paseo(id: Int) = {
    suben.acquire()
    // Si la cabina esta llena se bloquea hasta que: o bien llegue la siguiente vacia o se bajen todos los que estaban dentro
    numPas(i) += 1
    log(s"Se sube pasajero $id. Hay ${numPas(i)} pasajeros en el coche $i.")
    if (numPas(i) < C) suben.release() // Si todavia no esta llena pueden seguir subiendo
    else desplaza.release() // Si no paso a la siguiente

    bajan.acquire() // No se pueden bajar hasta que su coche hayad dado la vuelta
    numPas(i) -= 1
    log(s"Se baja pasajero $id. Hay ${numPas(i)} pasajeros en el coche $i.")
    if (numPas(i) > 0) bajan.release() // Si todavia no esta vacio pueden seguir bajando
    else suben.release() // Si no dejo que suban nuevos pasajeros
  }

  def mueve = {
    desplaza.acquire() // No se puede mover hasta que el coche este lleno
    log(s"Coche $i lleno, la noria se mueve....")
    i = (i + 1) % N
    log(s"Coche $i en la puerta")
    if (numPas(i) == C) bajan.release() // Si el coche que llega esta lleno, entonces lo vacio
    if (numPas(i) == 0) suben.release() // Si el coche que llega esta vacio, entonces lo lleno
  }

  override def run = {
    setName("Noria")
    while (true)
      mueve // cuando la noria está lista (se vacía el coche en la salida y se vuelva a llenar) el noria avanza un paso
  }
}

@main def mainNoria =
  val noria = new Noria(3, 2)
  val pasajero = new Array[Thread](50)
  noria.start()
  for (i <- pasajero.indices)
    pasajero(i) = thread {
      while (true)
        noria.paseo(i) // el pasajero i se da un paseo en la noria
        Thread.sleep(Random.nextInt(500)) // el pasajero i se da una vuelta por el parque
    }
