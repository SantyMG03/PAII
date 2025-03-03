package Practica1

import scala.annotation.tailrec

def binarySearch(arr: List[Int], elt: Int): Option[Int] = {
  @tailrec
  def rec(l: Int, r: Int): Option[Int] = {
    if l > r then None
    else {
      val mid = l + (r - l) / 2
      if arr(mid) == elt then Some(mid)
      else if arr(mid) < elt then rec (mid + 1, r)
      else rec(l, mid - 1)
    }
  }
  rec(0, arr.length - 1)
}

@main def check (): Unit = {
  val arr = List(1, 3, 5, 7, 9, 11)
  println(binarySearch(arr, 9))
}