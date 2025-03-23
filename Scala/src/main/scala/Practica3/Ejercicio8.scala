package Practica3

def fibonacci(n: Int):Int = {
  (0 until n).toList.foldRight((1,0)) {
    case (_,(a, b)) => (a + b, a)
  }._2
}

@main
def testFib(): Unit =
  println(fibonacci(5))
