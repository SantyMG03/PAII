package Practica3

def reverse[A](list: List[A]): List[A] = {
  list.foldLeft(List.empty[A])((acc, t) => t :: acc)
}

def append[A](l1: List[A], l2: List[A]): List[A] = {
  l1.foldRight(l2)((e, acc) => e :: acc)
}

@main
def testEjercicio2(): Unit = {
  val l1 = List(1, 2, 3)
  val l2 = List(4, 5)
  val l3 = List(1, 2, 3)
  println(reverse(l1))
  println(append(l3, l2))
}