package Practica1

import scala.annotation.tailrec

def filter[A](l: List[A], f: A => Boolean): List[A] = {
  @tailrec
  def func(remain: List[A], acc: List[A]): List[A] = {
    remain match
      case Nil => acc.reverse
      case head :: tail if f(head) => func(tail, head :: acc)
      case _ :: tail => func(tail, acc)
  }
  func(l, Nil)
}

@main def testFilter(): Unit = {
  val l = List(1, 2, 3)
  val fil = filter(l, _ % 2 != 0)
  println(s"Filtered (odd numbers): $fil")
}