import scala.annotation.tailrec

trait ImmutableBag[T] {
  def add(elem: T): ImmutableBag[T]
  def remove(elem: T): ImmutableBag[T]
  def count(elem: T): Int
  def size: Int
  def isEmpty: Boolean
}

class Bag[T] private(private val elems: Map[T, Int]) extends ImmutableBag[T] {
  def this(el: T*) = this(el.foldLeft(Map.empty[T,Int])((acc, elem) => acc.updated(elem, acc.getOrElse(elem, 0) + 1)))
  // Sirve tambien this(el.foldLeft(Map.empty[T,Int])((acc, elem) => acc + (elem -> acc.getOrElse(elem, 0) + 1)))

  override def add(elem: T): ImmutableBag[T] = Bag(elems.updated(elem, elems.getOrElse(elem,0) + 1))

  override def remove(elem: T): ImmutableBag[T] ={
    Bag(elems.get(elem)) match
      case None => elems
      case Some(1) => elems - elem
      case Some(_) => elems.updated(elem, elems.getOrElse(elem, 0) - 1)
  }

  override def count(elem: T): Int = elems.getOrElse(elem,0)

  override def size: Int = elems.foldLeft(0)((acc, elem) => acc + elem._2)

  override def isEmpty: Boolean = elems.isEmpty
  
  def union(other: Bag[T]): Bag[T] = {
    Bag(elems.foldLeft(other.elems){ case (acc, (k,v)) => acc.updated(k, acc.getOrElse(k, 0) + value)})
  }

  override def equals(other: Any): Boolean = {
    other match
      case b: Bag[T] => elems == b.elems
      case _ => false
  }

  override def hashCode(): Int = {
    elems.hashCode()
  }

  override def toString: String = {
    elems.map{case (k,v) => List.fill(v)(k).mkString(",").mkString("Bag(", ",", ")")}
  }
}

@main def testBag(): Unit = {
  val emptyBag: Bag[Int] = Bag()
  val bag = Bag(1,2,2,3,3,3)
  val otherBag = Bag(3, 4, 4, 5, 5, 5)
  println(emptyBag)
  println(bag)
  println(otherBag)

  assert(bag.add(4).count(4) == 1, "Error: Adding new element failed")
  assert(bag.remove(4).count(4) == 0, "Error: Removing non-existing element failed")
  assert(bag.count(2) == 2, "Error: Counting existing element failed")
  assert(bag.size == 6, "Error: Size of bag with elements failed")
  assert(emptyBag.isEmpty, "Error: isEmpty on empty bag failed")
  assert(bag.union(otherBag).count(3) == 4, "Error: Union with another bag failed")
}

/*
Escribe una función recursiva de cola 
  def group[T](l: List[T]): List[List[T]]
que toma una lista de elementos y agrupa los elementos consecutivos iguales en sublistas.
Por ejemplo:
  group(List(1, 1, 2, 3, 3, 3, 4, 4, 5, 1, 1))
da como resultado:
  List(List(1, 1), List(2), List(3, 3, 3), List(4, 4), List(5), List(1, 1))
*/
def group[T](l: List[T]): List[List[T]] = {
  @tailrec
  def rec(list: List[T], act: List[T], acc: List[List[T]]): List[List[T]] = {
    list match
      case Nil => act :: acc
      case x :: xs => if act.isEmpty || x == act.head then rec(xs, x :: act, acc) else rec(xs, List(x), act :: acc)
  }
  if l.isEmpty then List()
  else rec(l, List.empty, List.empty)
}

@main def testGroup(): Unit =
  assert(group(List()) == List())
  assert(group(List(1, 1, 2, 3, 3, 3, 4, 4, 5, 1, 1)) == List(List(1, 1), List(2), List(3, 3, 3), List(4, 4), List(5), List(1, 1)))
  assert(group(List(1, 2, 3, 4, 1, 1, 3, 3)) == List(List(1), List(2), List(3), List(4), List(1, 1), List(3, 3)))

/*
Usando funciones de orden superior encadenadas, escribe una función
def averageAgeByCity(people: List[(String, Int, String)]): List[(String, Double)] =
que toma una lista de tuplas, donde cada tupla contiene el nombre de una persona, su edad y su ciudad
y devuelve una lista de tuplas, donde cada tupla contiene una ciudad y la edad promedio de
las personas en esa ciudad.
Por ejemplo, dado
val people = List(
  ("Alice", 30, "Nueva York"),
  ("Bob", 25, "San Francisco"),
  ("Charlie", 35, "Nueva York"),
  ("David", 40, "San Francisco"),
  ("Eve", 28, "Nueva York")
)
val result = averageAgeByCity(people)
// Salida esperada: List(("Nueva York", 31.0), ("San Francisco", 32.5))
*/

def averageAgeByCity(people: List[(String, Int, String)]): List[(String, Double)] = {
  people.groupBy(_._3).map {case (city, group) => 
    val avg = group.map(_._2).sum.toDouble / group.size
    city-> avg
  }.toList
}

@main def testAveragaAgeByCity =
  val people = List(
    ("Alice", 30, "Nueva York"),
    ("Bob", 25, "San Francisco"),
    ("Charlie", 35, "Nueva York"),
    ("David", 40, "San Francisco"),
    ("Eve", 28, "Nueva York")
  )
  // assert(averageAgeByCity(people)) == List(("Nueva York", 31.0), ("San Francisco", 32.5)))
  print(averageAgeByCity(people)) // List(("Nueva York", 31.0), ("San Francisco", 32.5))
