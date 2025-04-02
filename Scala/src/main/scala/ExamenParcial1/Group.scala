package ExamenParcial1

import scala.annotation.tailrec

def group[T](l: List[T]): List[List[T]] = {
  @tailrec
  def rec(list: List[T], acc: List[List[T]]): List[List[T]] = {
    list match
      case Nil => acc.reverse
      case x :: xs => acc match
        case(h :: t) if h.head == x => rec(xs, (x :: h) :: t)
        case _ => rec(xs, List(x) :: acc)
  }
  rec(l, Nil)
}

@main def testGroup(): Unit =
  assert(group(List()) == List())
  assert(group(List(1, 1, 2, 3, 3, 3, 4, 4, 5, 1, 1)) == List(List(1, 1), List(2), List(3, 3, 3), List(4, 4), List(5), List(1, 1)))
  assert(group(List(1, 2, 3, 4, 1, 1, 3, 3)) == List(List(1), List(2), List(3), List(4), List(1, 1), List(3, 3)))

