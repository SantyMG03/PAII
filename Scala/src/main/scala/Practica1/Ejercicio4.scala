package Practica1

def zip[A, B](a: List[A], b: List[B]): List[(A,B)] = {
  def func(la: List[A], lb: List[B], acc: List[(A,B)]): List[(A,B)] = {
    (la, lb) match
      case (Nil, _) | (_, Nil) => acc.reverse
      case (a :: tailA, b :: tailB) => func(tailA, tailB, (a, b) :: acc)
  }
  func(a, b, Nil)
}

@main def checkZip(): Unit = {
  val f = List(1, 2, 3)
  val s = List("a", "b", "c")
  val zipped = zip(f,s)
  println(s"Zipped: $zipped")
}