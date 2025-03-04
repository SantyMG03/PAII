package Practica1

def subsets[A](set: List[A]): List[List[A]] =
  set match
    case Nil => List(Nil)
    case head :: tail =>
      val tailSubset = subsets(tail)
      tailSubset ++ tailSubset.map(head :: _)

@main def testSubset(): Unit = {
  val set = List(1, 2, 3)
  println(subsets(set))
}