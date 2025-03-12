package Practica1

def reduce[A](l: List[A], f: (A, A) => A): A =
  l match
    case Nil => throw new IllegalArgumentException("Lista vacia")
    case head :: tail => tail.foldLeft(head)(f)

@main def testReduce(): Unit =
  println(reduce(List(1, 2, 3, 4, 5, 6), _ + _))
  println(reduce(List(1, 2, 3, 4, 5, 6), _ * _))