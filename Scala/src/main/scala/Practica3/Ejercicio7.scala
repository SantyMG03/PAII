package Practica3

def remdups[A](lista: List[A]) : List[A] = {
  lista.foldRight(List.empty[A]) { (elem, acc) =>
    if acc.contains(elem) then acc else elem :: acc
  }
}

@main
def testRemDups(): Unit = {
  val nums = List(1, 2, 3, 2, 4, 1, 5)
  val result = remdups(nums)
  println(result)
}