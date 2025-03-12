package EjerciciosT2

import scala.annotation.tailrec
import scala.collection.mutable.ArrayBuffer

/**
 * Ejercicio 11
 * Implementa una función recursiva para calcular el enésimo número de Fibonacci.
 * Conviértela en recursiva de cola.
 *
 * @param n es el enesimo numero de la secuencia de fibonacci
 * @return El enesimo numero de la secuencia de fibonacci
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
 *
 * @param a numero que sumaremos digito a digito 12 = 1 + 2
 * @return El resultado de la suma
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
 *
 * @param set Conjunto sobre el que haremos el cierre
 * @tparam A
 * @return Conjunto de conjuntos
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
 *
 * @param arr sobre el que buscar la subsecuencia
 * @return Una lista con la subsecuencia mas larga encontrada
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