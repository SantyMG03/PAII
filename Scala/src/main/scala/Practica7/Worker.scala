package Practica7

import javax.swing.SwingWorker
import scala.collection.mutable.ListBuffer
import java.math.BigInteger
import java.util
import scala.jdk.CollectionConverters.*

class Worker(numPairs: Int, tipo: String, panel: IPanel)
  extends SwingWorker[java.math.BigInteger, PrimesPair] {

  private def isPrime(num: Long): Boolean = {
    if (num < 2) false
    else !(2L to math.sqrt(num).toLong).exists(num % _ == 0)
  }

  override def doInBackground(): BigInteger = {
    var count = 0
    var current = 2L
    var lastPrimes = ListBuffer[Long]() // Lista de los ultimos primos (Manejo hasta 10)
    var suma = BigInteger.ZERO

    while (count < numPairs && !isCancelled) {
      if (isPrime(current)) {
        for (prev <- lastPrimes) {
          val diff = (current - prev).abs // Diferencia entre el ultimo primo y el actual
          val isMatch = tipo match {
            case "Gemelos" => diff == 2
            case "Primos"  => diff == 4
            case "Sexies"  => diff == 6
            case _ => false
          }

          if (isMatch) { // Si la diferencia coincide con el tipo seleccionado
            val pair = PrimesPair(prev, current, count + 1)
            publish(pair) // Publico el par
            suma = suma.add(BigInteger.valueOf(prev + current)) // Aumenta la suma
            count += 1 // Aumenta el contado
          }
        }
        lastPrimes += current // Agrego el primo a la lista
        if (lastPrimes.size > 10) lastPrimes.remove(0) // Si ya hay 10 guardados, borro el primero
      }

      if (numPairs > 0) setProgress((count * 100) / numPairs) // Aumento en la barra de progreso
      current += 1 // Aumento al siguiente val
    }
    suma
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
        panel.setStatusMessage(s"Suma de todos los primos únicos: $result")
      } catch {
        case e: InterruptedException => panel.setStatusMessage("Tarea interrumpida")
        case e: Exception => panel.setStatusMessage(s"Error al obtener resultados: ${e.getMessage}")
      }
    }
  }
}
