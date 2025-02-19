package EjerciciosBasicos

def mergeSortedArrays(arr1: Array[Int], arr2: Array[Int]): Array[Int] = {
  val merged = new Array[Int](arr1.length + arr2.length)
  var i, j, k = 0

  while (i < arr1.length && j < arr2.length) {
    if (arr1(i) <= arr2(j)) {
      merged(k) = arr1(i)
      i += 1
    } else {
      merged(k) = arr2(j)
      j += 1
    }
    k += 1
  }

  while (i < arr1.length) {
    merged(k) = arr1(i)
    i += 1
    k += 1
  }

  while (j < arr2.length) {
    merged(k) = arr2(j)
    j += 1
    k += 1
  }

  merged
}

@main
def checkMerge(): Unit = {
  val arr1 = Array(1, 3, 5, 7)
  val arr2 = Array(2, 4, 6, 8)

  val result = mergeSortedArrays(arr1, arr2)
  println(result.mkString(" "))
}


