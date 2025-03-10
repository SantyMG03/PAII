package EjerciciosT2

class Student(var name: String, var age: Int, var grades: List[Double]) {
  def media(): Double =
    grades.sum / grades.length
}

@main
def testStudent(): Unit =
  val s = new Student("Santiago", 22, List(7,9,10,8,8,7,9,7,7,10))
  println(s.media())
