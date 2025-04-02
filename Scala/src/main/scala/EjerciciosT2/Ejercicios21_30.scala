package EjerciciosT2

import scala.annotation.tailrec

/**
 * Ejercicio 21
 * Define una función recursiva de cola genérica (una cola de elementos de cualquier
 * tipo) que tome una lista y devuelva otra con los mismos elementos pero en orden
 * inverso.
 */
def reverse[A](l: List[A]): List[A] = {
  @tailrec
  def rec(list: List[A], acc: List[A]): List[A] = {
    list match
      case Nil => acc
      case x :: xs => rec(xs, x :: acc)
  }
  rec(l, Nil)
}

/**
 * Version con FoldLeft del ejercicio 21
 */
def reversef[A](l: List[A]): List[A] = {
  l.foldLeft(List.empty)((acc, elem) => elem :: acc)
}

@main
def testEjercicio21(): Unit = {
  val l = List(1,2,3,4)
  println(reverse(l))
  println(reversef(l))
}

/**
 * Ejercicio 22
 * Define una función recursiva de cola que tome como argumentos una función f de
 * enteros en enteros (Int => Int) y dos enteros, x e y, y calcule .
 */
def ej22(f: Int => Int, x: Int, y: Int): Int = {
  @tailrec
  def helper(current: Int, acc: Int): Int = {
    if (current > y) acc
    else helper(current + 1, acc + f(current))
  }

  helper(x, 0)
}

@main
def testEjercicio22(): Unit = {
  println(ej22(x => x * x, 1, 5)) // Ejemplo: suma de cuadrados de 1 a 5

}

/**
 * Ejercicio 23
 * Define una función recursiva de cola que tome un entero n mayor o igual que 0 y
 * devuelva una lista con los números naturales desde 0 a n.
 */
def listN(n: Int): List[Int] = {
  @tailrec
  def rec(v: Int, acc: List[Int]): List[Int] = {
    if (v < 0) acc
    else rec(v - 1, v :: acc)
  }
  rec(n, Nil)
}

def listN2(n: Int): List[Int] = {
  (0 to n).toList
}

@main
def testEjercicio23(): Unit = {
  println(listN(5))
  println(listN2(5))
  println(listN(-7))
}

/**
 * Ejercicio 24
 * Define una función recursiva genérica unzip que tome una lista de tuplas con dos
 * componentes y que devuelva una tupla con dos listas: una con las primeras
 * componentes y otra con las segundas.
 */
def unzip[A,B](list: List[(A,B)]): (List[A], List[B]) = {
  @tailrec
  def func(remain: List[(A, B)], accA: List[A], accB: List[B]): (List[A], List[B]) = {
    remain match
      case Nil => (accA.reverse, accB.reverse)
      case (a, b) :: tail => func(tail, a :: accA, b :: accB)
  }
  func(list, Nil, Nil)
}

@main
def testEjercicio24(): Unit = {
  val input = List((1, "a"), (2, "b"), (3, "c"))
  val (f, s) = unzip(input)
  println(s"First comp: $f")
  println(s"Second comp: $s")
}

/**
 * Ejercicio 25
 * Define una función recursiva genérica zip que tome dos listas y devuelva una lista de
 * tuplas, donde las primeras componentes se tomen de la primera lista y las segundas
 * componentes de la segunda lista.
 */
def zip[A, B](l1: List[A], l2: List[B]): List[(A, B)] = {
  @tailrec
  def rec(remainA: List[A], remainB: List[B], acc: List[(A,B)]): List[(A,B)] = {
    (remainA, remainB) match
      case (Nil, _) | (_, Nil) => acc.reverse
      case (a :: as, b :: bs) => rec(as, bs, (a, b) :: acc)
  }
  rec(l1, l2, Nil)
}

@main
def testEjercicio25(): Unit = {
  val f = List(1, 2, 3)
  val s = List("a", "b", "c")
  val zipped = zip(f, s)
  println(s"Zipped: $zipped")
}

/**
 * Ejercicio 26
 * Escribe una función flatten que transforme una estructura de listas anidadas en una
 * lista aplanada.
 */
def flatten(l: List[Any]): List[Any] = {
  l.foldLeft(List.empty) {
    case (acc, inner: List[_]) => acc ++ flatten(inner)
    case (acc, elem) => acc :+ elem
  }
}

@main
def testEjercicio26(): Unit = {
  println(flatten(List("a", List("b","c"), List("d", "e"))) )
}

/**
 * Ejercicio 27
 * Escribe una función compress que elimine duplicados consecutivos de una lista.
 */
def compress[A](l: List[A]): List[A] = {
  l.foldRight(List.empty)((elem, acc) => if !acc.contains(elem) then elem :: acc else acc)
}

@main
def testEjercicio27(): Unit = {
  val l = List("a","a","a","b","c","c","d","e","e","e")
  println(compress(l))
}

/**
 * Ejercicio 28
 * Escribe una función pack que agrupe elementos repetidos consecutivos en sublistas.
 */
def pack[A](l: List[A]): List[List[A]] = {
  l.foldRight(List[List[A]]()) {
    case (elem, Nil) => List(List(elem))
    case (elem, (x :: xs)) if x.head == elem => (elem :: x) :: xs
    case (elem, acc) => List(elem) :: acc
   }
}

@main
def testEjercicio28(): Unit = {
  val result = pack(List('a', 'a', 'b', 'b', 'b', 'c', 'a', 'a'))
  println(result)
}

/**
 * Ejercicio 29
 * Escribe una función replicate que construya una lista en la que los elementos de la
 * lista que recibe se replican tantas veces como indica su segundo argumento.
 */
def replicate[A](l: List[A], v: Int): List[A] = {
  l.foldRight(List.empty)((elem, acc) => List.fill(v)(elem) ::: acc)
}

@main
def testEjercicio29(): Unit = {
  val result = replicate(List(1,2,3,4), 3)
  println(result)
}

/**
 * Ejercicio 30
 * Escribe una función range que devuelva una lista con los enteros entre sus
 * argumentos (en orden creciente o decreciente según corresponda).
 */
def range(x: Int, y: Int): List[Int] = {
  if x < y then (x to y).toList
  else if x == y then List()
  else (y to x).reverse.toList
}

@main
def testEjercicio30(): Unit = {
  println(range(10,5))
  println(range(5,10))
  println(range(5,5))
}