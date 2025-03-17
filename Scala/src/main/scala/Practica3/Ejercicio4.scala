package Practica3

import scala.annotation.tailrec

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

