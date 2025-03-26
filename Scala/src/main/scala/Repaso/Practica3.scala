package Repaso

import scala.annotation.tailrec

// ------ Ejercicio 1 ------

def suma(l: List[Int]): Int = {
  l.foldRight(0)((elem, acc) => acc + elem)
}

def product(l: List[Int]): Int = {
  l.foldRight(1)((elem, acc) => acc * elem)
}

def len[A](l: List[A]): Int = {
  l.foldRight(0)((elem, acc) => acc + 1)
}

@main
def testEx1(): Unit = {
  val l = List(1,2,3,4)
  println(suma(l))
  println(product(l))
  println(len(l))
}

// ------ Ejercicio 2 ------

def reverse[A](l: List[A]): List[A] = {
  l.foldLeft(List.empty)((acc, elem) => elem :: acc)
}

def append[A](l1: List[A], l2: List[A]): List[A] = {
  l1.foldRight(l2)((elem, acc) => elem :: acc)
}

@main
def testEx2(): Unit = {
  val l = List(1,2,3,4)
  val l2 = List(2,3)
  println(reverse(l))
  println(append(l, l2))
}

// ------ Ejercicio 3 ------

def exist[A](l: List[A], f: A=> Boolean): Boolean = {
  l.foldRight(false)((elem, acc) => f(elem) | acc)
}

@main
def testEx3(): Unit = {
  val l = List(1,2,3,4)
  println(exist(l, _ % 2 == 0))
}

// ------ Ejercicio 4 ------

def fTailrec(l: List[Int]): List[Int] = {
  @tailrec
  def rec(list: List[Int], acc: List[Int]): List[Int] = {
    list match
      case head :: tail =>
        if head < 0 then
          val h = head * -1
          rec(tail, acc :+ h)
        else
          rec(tail, acc)
      case Nil => acc
  }
  rec(l, List.empty)
}

def f(l: List[Int]): List[Int] = {
  l.filter(_ < 0).map(math.abs)
}

@main
def testEx4(): Unit = {
  val l = List(1,-2,3,-4)
  println(fTailrec(l))
  println(f(l))
}

// ------ Ejercicio 5 ------

def unzip[A,B](l:List[(A,B)]):(List[A],List[B]) = {
  l.foldRight((List.empty[A], List.empty[B]))((elem, acc) => (elem._1 :: acc._1, elem._2 :: acc._2))
}

@main
def testEx5(): Unit = {
  val lista = List((1, "a"), (2, "b"), (3, "c"))
  println(unzip(lista))
}

// ------ Ejercicio 6 ------

def compose[A](l: List[A=>A], v: A): A = {
  l.foldRight(v)((elem, acc) => elem(acc))
}

@main
def testEx6(): Unit = {
  val funcs: List[Int => Int] = List(_ + 1, _ * 2, _ - 3, Math.pow(_, 2).toInt)
  val result = compose(funcs, 4)
  println(result)
}

// ------ Ejercicio 7 ------

def remDups[A](l: List[A]): List[A] = {
  l.foldRight(List.empty[A]){
    case(elem, acc) if acc.nonEmpty && acc.head == elem => acc
    case(elem, acc) => elem :: acc}
}

@main
def testEx7(): Unit = {
  val l = List(1,1,2,3,3,3,3,4,4,5,1)
  println(remDups(l))
}

// ------ Ejercicio 8 ------

def fib(n: Int): Int = {
  (0 to n).toList.foldRight((0,1)){
    case (_ , (a, b)) => (a + b, a)
  }._2
}

@main
def testEx8(): Unit = {
  println(fib(5))
}

// ------ Ejercicio 9 ------

def inits[A](l: List[A]): List[List[A]] = {
  l.foldLeft(List(List.empty[A]))((acc, elem) => (acc.head :+ elem) :: acc).reverse
}

@main
def testEx9(): Unit = {
  val l = List(1,2)
  println(inits(l))
}

// ------ Ejercicio 10 ------

def halfEven(l1: List[Int], l2: List[Int]): List[Int] = {
  l1.zip(l2).foldRight(List.empty[Int])((elem, acc) => elem._1 + elem._2 :: acc).filter(_ % 2 == 0).map(_ / 2)
}

@main
def testEx10(): Unit = {
  val l1 = List(1,2,3,4)
  val l2 = List(3,2,4)
  println(halfEven(l1,l2))
}

// ------ Ejercicio 11 ------

@main
def ex11(): Unit = {
  val logs = List(
    "ERROR: Null pointer exception",
    "INFO: User logged in",
    "ERROR: Out of memory",
    "WARNING: Disk space low",
    "INFO: File uploaded",
    "ERROR: Database connection failed"
  )
  val m = logs.groupBy(_.split(":")(0)).view.mapValues(_.size).toMap
  val e = logs.filter(_.startsWith("ERROR"))
  println(m)
  println(e)
}

// ------ Ejercicio 12 ------

@main
def ex12():Unit = {
  val sales = List(
    ("Laptop", 2, 1000.0),
    ("Mouse", 10, 15.0),
    ("Keyboard", 5, 50.0),
    ("Monitor", 3, 200.0),
    ("USB Drive", 20, 5.0)
  )

  val gaining = sales.foldRight(0.0)((elem, acc) => acc + elem._3 * elem._2)
  println(gaining)
  val rep = sales.foldRight(List.empty[(String, Double)])((elem, acc) => (elem._1, elem._3 * elem._2) :: acc).sortBy(_._2).reverse
  println(rep)
}

// ------ Ejercicio 13 ------

@main
def ex13(): Unit = {
  val sentences = Set(
    "Scala is a functional language",
    "The power of functional programming is great",
    "Functional programming is elegant"
  )
  val stopWords = Set("a", "the", "is", "of")

  val res = sentences.flatMap(_.toLowerCase.split("\\s+")).diff(stopWords)
  println(res)
}

// ------ Ejercicio 14 ------

@main
def ex14(): Unit = {
  val words = List("scala", "is", "awesome", "scala", "functional", "scala", "is", "great")

  val res = words.groupBy(_.trim).view.mapValues(_.size).toMap
  println(res)
}

// ------ Ejercicio 15 ------

@main
def ex15(): Unit = {
  val warehouse1 = Map("laptop" -> 5, "mouse" -> 20, "keyboard" -> 10)
  val warehouse2 = Map("laptop" -> 3, "mouse" -> 15, "monitor" -> 8)

  val res = (warehouse1.toSeq ++ warehouse2.toSeq).map((k, v) => k -> (v + warehouse1.getOrElse(k, 0)))
  println(res)
}