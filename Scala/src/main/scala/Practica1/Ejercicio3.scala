package Practica1

def unzip[A,B](list: List[(A,B)]): (List[A], List[B]) = {
  def func(remain: List[(A, B)], accA: List[A], accB: List[B]): (List[A], List[B]) = {
    remain match
      case Nil => (accA.reverse, accB.reverse)
      case (a, b) :: tail => func(tail, a :: accA, b :: accB)
  }
  func(list, Nil, Nil)
}

@main def checkUnzip(): Unit = {
  val input = List((1, "a"), (2, "b"), (3, "c"))
  val (f, s) = unzip(input)
  println(s"First comp: $f")
  println(s"Second comp: $s")
}
