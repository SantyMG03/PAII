package EjerciciosT2

import scala.annotation.tailrec

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