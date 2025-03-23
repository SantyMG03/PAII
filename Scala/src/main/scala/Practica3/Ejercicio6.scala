package Practica3

def compose[A](lf: List[A=>A], v: A): A = {
  lf.foldRight(v)((f, acc) => f(acc))
}

@main
def testCompose(): Unit = {
  val funcs: List[Int => Int] = List(_ + 1, _ * 2, _ - 3)
  val result = compose(funcs, 4) // (4 - 3) * 2 + 1 = 3
  println(result)
}