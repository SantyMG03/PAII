package EjerciciosBasicos

def removeDup(matrix: Array[Array[Int]]): Array[Array[Int]] =
  var seen = Set[Int]()
  for (i <- matrix.indices) {
    for (j <- matrix(i).indices) {
      if seen.contains(matrix(i)(j)) then matrix(i)(j) = -1
      else seen += matrix(i)(j)
    }
  }
  matrix.map(row => row.filter(_ != -1))

@main
def checkMatrix(): Unit = {
  val matrix = Array(
    Array(1, 2, 3, 4),
    Array(3, 4, 5, 6),
    Array(6, 7, 8, 1)
  )

  val result = removeDup(matrix)
  result.foreach(row => println(row.mkString(" ")))
}