package EjerciciosBasicos

def search (list: List[Int]): Option[Int] =
  val sorted = list.sorted.distinct.reverse
  sorted.lift(1)

@main def checkList(): Unit =
  val lista = List(4, 1, 7, 3, 7, 2, 9, 9)
  println(search(lista))
