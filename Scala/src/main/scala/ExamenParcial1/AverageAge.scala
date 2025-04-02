package ExamenParcial1

@main
def testAveragaAgeByCity = {
  val people = List(
    ("Alice", 30, "Nueva York"),
    ("Bob", 25, "San Francisco"),
    ("Charlie", 35, "Nueva York"),
    ("David", 40, "San Francisco"),
    ("Eve", 28, "Nueva York")
  )

  val average = people.groupBy(_._3).map { case (city, group) =>
    val avg = group.map(_._2).sum.toDouble / group.size
    city -> avg
  }

  println(average)
}


