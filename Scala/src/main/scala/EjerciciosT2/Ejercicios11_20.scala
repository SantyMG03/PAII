package EjerciciosT2

import java.io.{File, PrintWriter}
import scala.annotation.tailrec
import scala.collection.mutable.ArrayBuffer
import scala.io.Source

/**
 * Ejercicio 11
 * Implementa una función recursiva para calcular el enésimo número de Fibonacci.
 * Conviértela en recursiva de cola.
 */
def fibonacci(n: Int): Int = {
  @tailrec
  def recfib(n: Int, a: Int, b: Int): Int = {
    if n == 0 then a
    else recfib(n - 1, b, a + b)
  }

  recfib(n, 0, 1) // Salida -> 8
}

@main
def fibTest(): Unit =
  println(fibonacci(6))

/**
 * Ejercicio 12
 * Implementa una función recursiva que devuelva la suma de los dígitos de un entero
 * dado. Conviértela en recursiva de cola.
 */
def sumRec(a: Int): Int = {
  @tailrec
  def rec(acc: Int, n: Int): Int = {
    if n == 0 then acc
    else rec(acc + (n % 10), n / 10)
  }
  rec(0, a)
}

@main
def sumRecTest(): Unit =
  println(sumRec(1234)) // Salida -> 10

/**
 * Ejercicio 13
 * Implementa una función recursiva para generar todos los subconjuntos de un
 * conjunto determinado. Conviértela en recursiva de cola.
 */
def kleene[A](set: Set[A]): Set[Set[A]] = {
  @tailrec
  def func(rest: List[A], acc: Set[Set[A]]): Set[Set[A]] = {
    if rest.isEmpty then acc
    else func(rest.tail, acc ++ acc.map(_ + rest.head))
  }
  func(set.toList, Set(Set()))
}

@main
def kleeneTest(): Unit =
  println(kleene(Set(1,2,3))) // Salida -> Set(), Set(1, 3), Set(2), Set(2, 3), Set(3), Set(1, 2), Set(1), Set(1, 2, 3)

/**
 * Ejercicio 14
 * Escribe una función que encuentre la subsecuencia creciente más larga de una lista
 * de números enteros
 */
def biggestSubSeq(arr: Array[Int]): List[Int] = {
  if (arr.isEmpty) return List()

  val n = arr.length
  val subseq = ArrayBuffer[Int]() // Almacena los elementos finales de subsecuencias óptimas
  val parent = Array.fill(n)(-1) // Para reconstruir la subsecuencia
  val idx = Array.fill(n)(-1) // Índices de los elementos en subseq

  for (i <- arr.indices) {
    val pos = subseq.search(arr(i)).insertionPoint
    if (pos == subseq.length) subseq.append(arr(i))
    else subseq(pos) = arr(i)

    idx(pos) = i
    parent(i) = if (pos > 0) idx(pos - 1) else -1
  }

  // Reconstrucción de la subsecuencia
  var lis = List[Int]()
  var k = idx(subseq.length - 1)
  while (k >= 0) {
    lis = arr(k) :: lis
    k = parent(k)
  }

  lis
}

@main
def biggestSubseqTest(): Unit =
  val arr = Array(10, 9, 2, 5, 3, 7, 101, 18)
  println(biggestSubSeq(arr))

/**
 * Ejercicio 16
 * Implementa un programa que encuentre la palabra más larga de un archivo
 */
def longestWord(filename: String): Option[String] = {
  try {
    val source = Source.fromFile(filename)
    val words = source.getLines().flatMap(_.split("\\s+")).toList
    source.close()
    words.maxByOption(_.length)
  } catch
    case e: Exception => println(s"Error al leer el archivo: ${e.getMessage}")
    None
}

def copyContent(f1: String, f2: String): Unit = {
  try {
    val source = Source.fromFile(f1)
    val lines = source.getLines().mkString("\n")
    source.close()

    val writer = PrintWriter(new File(f2))
    writer.write(lines)
    writer.close()
  } catch
    case e: Exception => println(s"Error al copiar archivo ${e.getMessage}")
}

/**
 * Ejercicio 18
 * Define una función last que devuelva un Option con el último elemento de la lista que
 * recibe como argumento, si dicho elemento existe, o None en otro caso.
 */
def last[A](l: List[A]): Option[A] = {
  if l.isEmpty then None
  else Some(l.reverse.head)
}

@main
def testEjercicio18(): Unit = {
  val l = List("a","b","c","d")
  val l2 = List()
  println(last(l))
  println(last(l2))
}

/**
 * Ejercicio 19
 * Define una función nth que devuelva un Option con el elemento de la lista que recibe
 * como argumento en la posición i, si dicho elemento existe, o None en otro caso.
 */
def nth[A](l: List[A], v: A): Option[A] = {
  if l.contains(v) then Some(v)
  else None
  // Some(v).filter(l.contains) tambien sirve
}

@main
def testEjercicio19(): Unit = {
  val l = List("a","b","c","d")
  println(nth(l, "c"))
  println(nth(l, "h"))
}

/**
 * Ejercicio 20
 * Define una función recursiva de cola sumaCuadrados(List[Int]) que calcule la suma
 * de los elementos de la lista de enteros que recibe como argumento
 */
def sumaCuadrados(l: List[Int]): Int = {
  @tailrec
  def rec(list: List[Int], acc: Int): Int = {
    list match
      case Nil => acc
      case x :: xs => rec(xs, x * x + acc)
  }
  rec(l, 0)
}

/**
 * Version con foldRight del ejercicio 20
 */
def sumaCuadradosf(l: List[Int]): Int = {
  l.foldRight(0)((elem, acc) => acc + elem * elem)
}


@main
def testEjercicio20(): Unit = {
  val l = List(1,2,3)
  println(sumaCuadradosf(l))
  println(sumaCuadrados(l))
}