package EjerciciosT2

import scala.collection.mutable

def permutations(m1: Array[Array[Int]], m2: Array[Array[Int]]): Boolean =
  if m1.length != m2.length || m1.head.length != m2.head.length then return false
  val map = mutable.Map[Int, Int]().withDefaultValue(0)
  for(row <- m1; num <- row) {
    map(num) += 1
  }

  for(row <- m2; num <- row) {
    if (map(num) == 0){
      return false
    }
    map(num) -= 1
  }
  true