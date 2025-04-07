package ExamenParcial1

import scala.annotation.tailrec

trait ImmutableSet[T] {
  def add(elem: T): ImmutableSet[T] // añade el elemento elem al conjunto si no está presente
  def remove(elem: T): ImmutableSet[T] // elimina elem del conjunto; no modifica el conjunto si no está
  def contains(elem: T): Boolean // comprueba si un elemento está en el conjunto
  def size: Int // devuelve el número de elementos en el conjunto
  def isEmpty: Boolean // comprueba si el conjunto está vacío
}

class SimpleSet[T] private(private val elems: List[T]) extends ImmutableSet[T] {
  def this(elem: T*) = this(elem.foldRight(List.empty[T])((elem, acc) => if acc.contains(elem) then acc else elem :: acc))

  override def add(elem: T): ImmutableSet[T] = {
    if elems.contains(elem) then SimpleSet(elems) else SimpleSet(elem :: elems)
  }

  override def remove(elem: T): ImmutableSet[T] = {
    SimpleSet(elems.foldRight(List.empty[T])((e, acc) => if e == elem then acc else e :: acc))
  }

  override def contains(elem: T): Boolean = elems.contains(elem)

  override def size: Int = elems.size

  override def isEmpty: Boolean = elems.isEmpty

  override def toString: String = elems.mkString("Set(", ", ", ")")

  override def hashCode(): Int = elems.foldRight(0)((elem, acc) => elem.hashCode() + acc)

  override def equals(obj: Any): Boolean = {
    obj match
      case b: SimpleSet[T] => elems == b.elems
      case _ => false
  }

  def toList: List[T] = elems.toList

  def union(other: SimpleSet[T]): ImmutableSet[T] = {
    @tailrec
    def rec(remaining: List[T], acc: List[T]): ImmutableSet[T] = {
      remaining match
        case Nil => SimpleSet(acc)
        case x :: xs => if !acc.contains(x) then rec(xs, x :: acc) else rec(xs, acc)
    }
    rec(this.elems, other.elems)
  }

  def intersection(other: SimpleSet[T]): ImmutableSet[T] = {
    SimpleSet(this.elems.intersect(other.elems))
  }

  def difference(other: SimpleSet[T]): ImmutableSet[T] = {
    SimpleSet(this.elems.foldLeft(List.empty[T])((acc, elem) => if other.elems.contains(elem) then acc else elem :: acc))
  }
}

@main
def testSimpleSet(): Unit = {
  val set1 = SimpleSet(1,2,3)
  val set2 = SimpleSet(1,1,1,2,3,4,5,6,7,7)
  val set3 = SimpleSet()
  val set4 = SimpleSet(1,2,3)

  println(set1.union(set2))
  println(set3.isEmpty)
  println(set1.intersection(set2))
  println(set2.difference(set1))
  println(set1.remove(3))
  println(set1 == set4)
}

@main
def ejercicio2(): Unit = {
  val sentences = List(
    "FINAL: Scala is a functional language",
    "DRAFT: The power of functional programming is great",
    "DRAFT: Programming is elegant",
    "FINAL: Functional programming is elegant",
    "FINAL: Object-oriented programming is great")

  val stopWords = Set("a", "the", "is", "of")

  val sol = sentences.filter(_.startsWith("FINAL")).flatMap(_.split(":|\\s+")).map(_.toLowerCase).
    filterNot(_.isBlank).filterNot(stopWords.contains).filterNot(_ == "final").
    groupBy(identity).view.mapValues(_.size).toMap

  println(sol)
}