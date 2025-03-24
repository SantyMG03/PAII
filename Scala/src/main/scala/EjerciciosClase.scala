import scala.annotation.tailrec

def existeF[A](l: List[A], f: A => Boolean): Boolean = {
  l.foldLeft(false)((acc, elem) => f(elem) | acc)
}

def existe[A](l: List[A], f: A => Boolean): Boolean = {
  @tailrec
  def rec(lb: List[A], acc: Boolean): Boolean = {
    lb match
      case x :: xs => rec(xs, f(x) || acc)
      case Nil => acc // Condicion de parada
  }
  rec(l, false)
}

@main
def testExiste(): Unit = {
  val l = List(1,3,5)
  println(existeF(l, _ % 2 == 0))
  println(existe(l, _ % 2 == 0))
}

def f(l: List[Int]): List[Int] = {
  l.filter(_ < 0).map(math.abs).map(_ * 2)
}
def ff(l: List[Int]): List[Int] = {
  l.foldRight(List.empty)((elem, acc) => if elem < 0 then elem * -2 :: acc else acc)
}

@main
def testF():Unit = {
  val l = List(-1,1,-3,3,4)
  println(f(l))
  println(ff(l))
}