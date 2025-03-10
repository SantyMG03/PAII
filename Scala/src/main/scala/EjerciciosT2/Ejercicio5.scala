package EjerciciosT2

class Triangle(val a1: Int, val a2: Int, val a3: Int) {
  def check(): Unit =
    if a1 == a2 && a2 == a3 then println("Equilatero")
    else if a1 == a2 && a2 != a3 then println("Isosceles")
    else if a1 != a2 && a2 != a3 && a1 != a3 then println("Escaleno")
}

@main
def testTriangle(): Unit =
  val t1 = new Triangle(10, 10, 10)
  val t2 = new Triangle(10, 10, 5)
  val t3 = new Triangle(15, 10, 5)
  t1.check()
  t2.check()
  t3.check()