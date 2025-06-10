package Practica7

import javax.swing.SwingWorker
import scala.collection.mutable.ListBuffer
import java.math.BigInteger
import java.util
import scala.jdk.CollectionConverters.*

class Worker(numPairs: Int, tipo: String, panel: IPanel) extends SwingWorker[List[PrimesPair], PrimesPair] {
  override def doInBackground(): List[PrimesPair] = {
    val result = ListBuffer[PrimesPair]()
    var count = 0
    var current = 2L
    var lastPrimeFound: Option[Long] = None

    def isPrime(n: Long): Boolean = {
      if (n < 2) false
      else if (n == 2) true
      else if (n % 2 == 0) false
      else !(3L to math.sqrt(n).toLong by 2).exists(n % _ == 0)
    }

    def distance: Int = tipo match {
      case "Gemelos" => 2
      case "Primos"  => 4
      case "Sexies"  => 6
      case _ => 2
    }

    while (!isCancelled && count < numPairs) {
      if (isPrime(current)) {
        lastPrimeFound match {
          case Some(a) => // Si ya tenemos un primo anterior 'a'
            val b = current
            if (b - a == distance) { // Comprueba si la diferencia es la esperada
              val pair = PrimesPair(a, b, count + 1) // Crea el objeto PrimesPair
              publish(pair) // Publica el par para que la UI lo muestre progresivamente
              result += pair // Añade el par a la lista de resultados final
              count += 1
              setProgress((count * 100) / numPairs) // Actualiza el progreso
            }
            lastPrimeFound = Some(current) // Actualiza el último primo encontrado
          case None => // Si es el primer primo que encontramos, solo lo almacenamos
            lastPrimeFound = Some(current)
        }
      }
      current += 1 // Pasa al siguiente número para comprobar
    }

    result.toList
  }

  override def process(chunks: java.util.List[PrimesPair]): Unit = {
    panel.writePrimes(chunks.asScala.toList) // Use asScala para convertir la lista java en lista scala
  }

  override def done(): Unit = {
    if (isCancelled) {
      panel.setStatusMessage("Tarea interrumpida")
    } else {
      try {
        val result = get()
        val unique = result.flatMap(pair => List(pair.prime1, pair.prime2)).toSet
        val totalSum = unique.foldLeft(BigInt(0))((acc, elem) => acc + BigInt(elem))
        panel.setStatusMessage(s"Suma de todos los primos únicos: $totalSum")
      } catch {
        case e: InterruptedException => panel.setStatusMessage("Tarea interrumpida")
        case e: Exception => panel.setStatusMessage(s"Error al obtener resultados: ${e.getMessage}")
      }
    }
  }
}
