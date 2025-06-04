package control_3

/*
Vamos a organizar un pequeño torneo de voley playa. Nos da igual las reglas del juego, simplemente necesitamos saber
que cada equipo tiene dos jugadores y que el equipo que gana un partido continúa jugando, mientras que el equipo que
pierde se renueva con nuevos jugadores.
Si tenemos equipos 0 y 1, el equipo ganador de un partido se decidirá aleatoriamente: i = Random.nextBoolean(2).
Los equipos no están previamente formados, todos los participantes que desean jugar esperan a que les llegue el turno.
La espera de los participantes será justa, de forma que los jugadores que entran a formar parte de un equipo son los
que llevan más tiempo esperando.
Al iniciar el torneo, se completarán los dos equipos. A partir de ese momento, cuando un partido termina los componentes
del equipo perdedor abandonan la pista y entran nuevos jugadores para formar un nuevo equipo, mientras que los del
equipo ganador permanecen en la pista para jugar el siguiente partido. Cuando los dos equipos están completos se inicia
un nuevo partido. El torneo continúa indefinidamente.
Para implementarlo, utilizaremos locks y condiciones, de forma que se minimicen las esperas y se evite despertar
hilos innecesariamente.
Por ejemplo, para equipos de capacidad 2, la siguiente sería una traza válida.
Thread-1: El jugador 0 se une al equipo 0, que tiene 1 jugadores.
Thread-2: El jugador 1 se une al equipo 0, que tiene 2 jugadores.
Thread-3: El jugador 2 se une al equipo 1, que tiene 1 jugadores.
Thread-4: El jugador 3 se une al equipo 1, que tiene 2 jugadores.
Voley: Equipos preparados, comienza un nuevo partido....
Voley: Ha ganado el equipo 1, se renueva el equipo 0.
Thread-1: El jugador 0 deja el equipo 0, que tiene 1 jugadores.
Thread-2: El jugador 1 deja el equipo 0, que tiene 0 jugadores.
Thread-5: El jugador 4 se une al equipo 0, que tiene 1 jugadores.
Thread-6: El jugador 5 se une al equipo 0, que tiene 2 jugadores.
Voley: Equipos preparados, comienza un nuevo partido....
Voley: Ha ganado el equipo 0, se renueva el equipo 1.
Thread-3: El jugador 2 deja el equipo 1, que tiene 1 jugadores.
Thread-4: El jugador 3 deja el equipo 1, que tiene 0 jugadores.
Thread-7: El jugador 6 se une al equipo 1, que tiene 1 jugadores.
Thread-8: El jugador 7 se une al equipo 1, que tiene 2 jugadores.
Voley: Equipos preparados, comienza un nuevo partido....
*/

import java.util.concurrent.locks.ReentrantLock
import scala.util.Random

class Torneo(C: Int) extends Thread {
  private val numJugadores = Array.fill(2)(0) // núm. de jugadores en cada equipo
  private var equipoPerdedor = 0 // equipo a renovar
  
  private val lock = new ReentrantLock()
  private val cEntra = lock.newCondition()
  private val cSale = lock.newCondition()
  private val cPartido = lock.newCondition()
  private var comienzo = false
  private var fin = false

  def juega(id: Int) = {
    lock.lock()
    try {
      while (numJugadores.sum == 4 || fin) cEntra.await()
      numJugadores(equipoPerdedor) += 1 // Aumento el numero de jugadores de dicho equipo
      log(s"El jugador $id se une al equipo $equipoPerdedor, que tiene ${numJugadores(equipoPerdedor)} jugadores.")
      if (numJugadores(equipoPerdedor) == 2){ // Si ya hay 2 jugadores entonces cambio el indice del equipo
        equipoPerdedor = (equipoPerdedor + 1) % 2
      }
      if (numJugadores.sum == 4) { // Si los equipos estan completos entonces el ultimo avisa de que se puede empezar
        comienzo = true
        cPartido.signal()
      }

      while (!fin) cSale.await() // Si el partido no ha acabado hay que esperar
      numJugadores(equipoPerdedor) -= 1 // Reduzco el numero de jugadores del equipo perderdor
      log(s"El jugador $id deja el equipo $equipoPerdedor, que tiene ${numJugadores(equipoPerdedor)} jugadores.")
      if (numJugadores(equipoPerdedor) == 0) { // Si esta vacio entonces se puede volver a formar equipo
        fin = false
        cEntra.signalAll()
      }
    } finally {
      lock.unlock()
    }
  }

  def jugarPartido = {
    lock.lock()
    try {
      while (!comienzo) cPartido.await()
      comienzo = false // Para siguientes iteraciones
      log(s"Equipos preparados, comienza un nuevo partido....")
      Thread.sleep(Random.nextInt(500)) // juegan el partido
      equipoPerdedor = Random.nextInt(2) // gana uno de los dos equipos (Asignado el perderdor)
      log(s"Ha ganado el equipo ${(equipoPerdedor + 1) % 2}, se renueva el equipo $equipoPerdedor.")
      fin = true
      cSale.signalAll() // Aviso de que el partido ha terminado
    } finally {
      lock.unlock()
    }
  }

  override def run = {
    setName("Voley")
    while (true)
      jugarPartido // cuando los dos equipos están completos (sale el equipo perdedor y entra un nuevo equipo) se juega un nuevo partido
  }
}

@main def mainVoley =
  val torneo = new Torneo(2)
  val jugador = new Array[Thread](50)
  torneo.start()
  for (i <- jugador.indices)
    jugador(i) = thread {
      while (true)
        torneo.juega(i) // el jugador i participa en el torneo de voley
        Thread.sleep(Random.nextInt(500)) // se da un bañito en la playa
    }
