package Practica1

import scala.annotation.tailrec

def groupBy[A, B](l: List[A], f: A => B): Map[B, List[A]] =
  @tailrec
  def func(remain: List[A], acc: Map[B, List[A]]): Map[B, List[A]] =
    remain match
      case Nil => acc
      case head :: tail =>
        val key = f(head)
        val update = acc + (key -> (head :: acc.getOrElse(key, Nil)))
        func(tail, update)
  func(l, Map.empty[B, List[A]])

@main def testGroupBy(): Unit =
  println(groupBy(List(1, 2, 3, 4, 5, 6), _ % 2))
