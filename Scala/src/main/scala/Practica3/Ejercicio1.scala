package Practica3

def sum(list :List[Int]): Int = {
  list.foldRight(0)(_ + _)
}

def product(list :List[Int]): Int = {
  list.foldRight(1)(_ * _)
}

def length[A](list :List[A]): Int = {
  list.foldRight(0)((_, acc) => acc + 1)
}

@main
def testEj1(): Unit = {
  val l = List[Int](1, 2, 3, 4)
  println(sum(l))
  println(product(l))
  println(length(l))
}