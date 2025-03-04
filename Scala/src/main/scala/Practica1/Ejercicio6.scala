package Practica1

import scala.annotation.tailrec

def map[A, B](l: List[A], f: A => B): List[B] = {
  @tailrec
  def func(remain: List[A], acc: List[B]): List[B] = {
    remain match
      case Nil => acc.reverse
      case head :: tail => func(tail, f(head) :: acc)
  }
  func(l, Nil)
}

@main def testMap(): Unit =
  val f = List(1, 2, 3)
  val list = map(f, _ * 2)
  println(s"$list")


