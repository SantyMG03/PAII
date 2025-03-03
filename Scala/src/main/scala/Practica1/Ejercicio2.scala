package Practica1

import scala.annotation.tailrec

def binarySearch(arr: List[Int], elt: Int): Option[Int] = {
  @tailrec
  def rec(elt: Int, n: Int, arr: List[Int]): Option[Int] = {
    if arr(n) == elt then Some(n)
    else if arr(n) < elt then rec(elt, n - (arr.size/2), arr.slice(0, n - 1))
    else rec(elt, n + (arr.size/2), arr.slice(n + 1, arr.size - 1))
  }
  rec(elt, arr.size/2, arr)
}