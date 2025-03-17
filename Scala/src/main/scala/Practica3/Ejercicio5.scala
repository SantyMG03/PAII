package Practica3

def unzip[A, B](lista: List[(A, B)]): (List[A], List[B]) =
  lista.foldRight((List.empty[A], List.empty[B])) {
    case ((a, b), (accA, accB)) => (a :: accA, b :: accB)
  }

@main
def testEjercicio5(): Unit =
  val lista = List((1, "a"), (2, "b"), (3, "c"))
  println(unzip(lista))