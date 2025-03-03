package Practica1

import scala.annotation.tailrec

def primeFactors(n: Int): List[Int] = {
  @tailrec
  def help(n: Int, div: Int, acc: List[Int]): List[Int] = {
    if n < 2 then acc
    else if n % div == 0 then help(n / div, div, acc :+ div)
    else help(n, div + 1, acc)
  }

  help(n, 2, List())
}