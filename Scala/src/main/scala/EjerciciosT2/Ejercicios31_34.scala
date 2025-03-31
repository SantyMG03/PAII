package EjerciciosT2

import scala.annotation.tailrec

/**
 * Ejercicio 31
 * Implementa una operación filter(l, f) que tome una lista l de elementos de tipo A y
 * una función f: A => Boolean y que devuelva una lista con los elementos e de l que
 * satisfacen f(e)
 */
def filter[A](l: List[A], f: A => Boolean): List[A] = {
  l.foldRight(List.empty)((elem, acc) => if f(elem) then elem :: acc else acc)
}

@main
def testEjercicio31(): Unit = {
  val l = List(1,2,3,4,5,6)
  println(filter(l, _ % 2 == 0))
}

/**
 * Ejercicio 32
 * Implementa una operación map(l, f) que tome como argumentos una lista l de
 * elementos de tipo A y una función f: A => B y que devuelva una lista de elementos
 * de tipo B con los elementos resultantes de aplicar f a cada uno de los elementos de
 * l.
 */
def map[A,B](l: List[A], f: A=>B): List[B] = {
  l.foldRight(List.empty)((elem, acc) => f(elem) :: acc)
}

@main
def testEjercicio32(): Unit = {
  val l = List(1,2,3,4,5,6)
  println(map(l, _ * 2))
}

/**
 * Ejercicio 33
 * Implementa una operación groupBy(l, f) que tome como argumentos una lista l de
 * elementos de tipo A y una función f: A => B y que devuelva un objeto de
 * tipo Map[B,List[A]] que asocie una lista con los elementos e de l con el mismo f(e)
 */
def groupBy[A,B](l: List[A], f: A => B): Map[B, List[A]] = {
  l.foldLeft(Map.empty[B, List[A]]) { (acc, elem) =>
    val k = f(elem)
    acc.updated(k, acc.getOrElse(k, List()) :+ elem)
  }
}

@main
def testEjercicio33(): Unit = {
  val result = groupBy(List(1, 2, 3, 4, 5, 6), _ % 2)
  println(result)
}


def reduce[A](l: List[A], f: (A,A)=>A): A = {
  l match
    case Nil => throw new UnsupportedOperationException()
    case x :: xs => xs .foldLeft(x)(f)
}

@main
def testEjercicio34(): Unit = {
  val l = List(1,2,3)
  println(reduce(l, _ + _))
}
