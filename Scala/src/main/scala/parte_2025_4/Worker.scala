package parte_2025_4

import javax.swing.SwingWorker
import scala.collection.mutable.ListBuffer
import java.math.BigInteger

class Worker(numPairs: Int, tipo: String, panel: IPanel) extends SwingWorker[List[PrimesPair], PrimesPair] {
  override def doInBackground(): List[PrimesPair] = {
    val result = ListBuffer[PrimesPair]()
    var count = 0
    var current = 2L
    var lastPrimes = ListBuffer[Long]()
    var totalSum = BigInteger.ZERO

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
        lastPrimes += current
        if (lastPrimes.length >= 2) {
          val a = lastPrimes(lastPrimes.length - 2)
          val b = lastPrimes.last
          if (b - a == distance) {
            val pair = PrimesPair(a, b, count + 1)
            publish(pair)
            result += pair
            totalSum = totalSum.add(BigInteger.valueOf(a)).add(BigInteger.valueOf(b))
            count += 1
            setProgress((count * 100) / numPairs)
          }
        }
      }
      current += 1
    }

    if (isCancelled) {
      throw new InterruptedException("Tarea interrumpida")
    }

    panel.setStatusMessage(s"Suma de todos los primos: $totalSum")
    result.toList
  }


  override def done(): Unit = {
    if (isCancelled) {
      panel.setStatusMessage("Tarea interrumpida")
    } else {
      val result = get()
      val totalSum = result.foldLeft(BigInt(0))((acc, pair) =>
        acc + BigInt(pair.toString.split("\\(")(1).dropRight(1).split(",").map(_.toLong).sum)
      )
      panel.setStatusMessage(s"Suma de todos los primos: $totalSum")
    }
  }
}
