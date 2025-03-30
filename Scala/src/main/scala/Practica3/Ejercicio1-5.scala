package Practica3

import scala.annotation.tailrec

// ------ Ejercicio 1 ------

def sum(list :List[Int]): Int = {
  list.foldRight(0)(_ + _)
}

def product(list :List[Int]): Int = {
  list.foldRight(1)((acc, elem) => elem * acc)
}

def length[A](list :List[A]): Int = {
  list.foldRight(0)((_, acc) => acc + 1)
}

@main
def testEj1(): Unit = {
  val l = List[Int](1, 2, 3, 4)
  println(sum(l))
  println(product(l))
  println(length(l))
}

// ------ Ejercicio 2 ------

def reverse[A](list: List[A]): List[A] = {
  list.foldLeft(List.empty[A])((acc, t) => t :: acc)
}

def append[A](l1: List[A], l2: List[A]): List[A] = {
  l1.foldRight(l2)((e, acc) => e :: acc)
}

@main
def testEjercicio2(): Unit = {
  val l1 = List(1, 2, 3)
  val l2 = List(4, 5)
  val l3 = List(1, 2, 3)
  println(reverse(l1))
  println(append(l3, l2))
}

// ------ Ejercicio 3 ------

def existe[A](l:List[A],f:A=>Boolean):Boolean = {
  l.foldRight(false)((e, acc) => acc || f(e))
}

@main
def testEjercicio3(): Unit = {
  val list = List(1,2,3,4,5,6)
  println(existe(List(1,2,3),_>2))
  println(existe(List("Hola","Mundo"),_.length >= 5))
  println(existe(List("Hola","Mundo"),_.length < 3))
}

// ------ Ejercicio 4 ------

def fTailrec(l: List[Int]): List[Int] = {
  @tailrec
  def help(list: List[Int], acc: List[Int]): List[Int] = {
    list match
      case head :: tail =>
        if head < 0 then
          val h = head * -1
          help(tail, acc :+ h)
        else
          help(tail, acc :+ head)
      case Nil => acc
  }
  help(l, List.empty)
}

def fSupFunc(l: List[Int]): List[Int] = {
  l.map(math.abs)
}

@main
def testEjercicio4(): Unit = {
  println(fTailrec(List(1,-2,3,-4,-5,6)))
  println(fSupFunc(List(1,-2,3,-4,-5,6)))
}

// ------ Ejercicio 5 ------

def unzip[A, B](lista: List[(A, B)]): (List[A], List[B]) =
  lista.foldRight((List.empty[A], List.empty[B])) {
    case ((a, b), (accA, accB)) => (a :: accA, b :: accB)
  }

@main
def testEjercicio5(): Unit =
  val lista = List((1, "a"), (2, "b"), (3, "c"))
  println(unzip(lista))