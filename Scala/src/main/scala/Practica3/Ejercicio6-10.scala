package Practica3

import scala.annotation.tailrec

// ------ Ejercicio 6 ------

def compose[A](lf: List[A=>A], v: A): A = {
  lf.foldRight(v)((f, acc) => f(acc))
}

@main
def testCompose(): Unit = {
  val funcs: List[Int => Int] = List(_ + 1, _ * 2, _ - 3)
  val result = compose(funcs, 4) // (4 - 3) * 2 + 1 = 3
  println(result)
}

// ------ Ejercicio 7 ------

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

// ------ Ejercicio 8 ------

def fibonacci(n: Int):Int = {
  (0 until n).toList.foldRight((1,0)) {
    case (_,(a, b)) => (a + b, a)
  }._2
}

@main
def testFib(): Unit =
  println(fibonacci(5))

// ------ Ejercicio 9 ------

def inits[A](list: List[A]): List[List[A]] = {
  list.foldRight(List(List.empty[A])) { (elem, acc) =>
    (elem :: acc.head) :: acc
  }
}

@main
def testInits(): Unit = {
  val l = List(1,2)
  println(inits(l))
}

// ------ Ejercicio 10 ------

def halfEvenRec(l1: List[Int], l2: List[Int]): List[Int] = {
  @tailrec
  def rec(acc: List[Int], list1: List[Int], list2: List[Int]): List[Int] = {
    (list1, list2) match
      case (Nil, Nil) => acc.reverse
      case (x :: xs, Nil) => rec(acc, xs, Nil)
      case (Nil, y :: ys) => rec(acc, Nil, ys)
      case (x :: xs, y :: ys) => {
        val sum = x + y
        if sum % 2 == 0 then rec((sum / 2) :: acc, xs, ys)
        else rec(acc, xs, ys)
      }
  }
  rec(Nil, l1, l2)
}

def halfEven(l1: List[Int], l2: List[Int]): List[Int] = {
  l1.zip(l2).map((a, b) => a + b).filter(_ % 2 == 0).map(_ / 2)
}

@main
def testHalfEven(): Unit = {
  val l1 = List(1,2,3,4)
  val l2 = List(3,2,4)
  println(halfEvenRec(l1,l2))
  println(halfEven(l1,l2))
}
