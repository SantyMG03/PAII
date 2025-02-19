package EjerciciosBasicos

def rotate(lst: List[Int], k: Int): List[Int] =
  val n = lst.length
  if (n == 0) lst
  else
    val shift = k % n
    lst.takeRight(shift) ++ lst.dropRight(shift)

@main
def checkRotate(): Unit =
  val list = List(1, 2, 3, 4, 5)
  println(rotate(list, 2))