package Practica2

class EfficientQueue[T]private (private val front: List[T], private val rear: List[T]) extends ImmutableQueue[T] {
  def this(elems: T*) = this(elems.toList, Nil)

  override def enqueue(elem: T): EfficientQueue[T] = new EfficientQueue[T](front, elem :: rear)

  override def dequeue(): (T, EfficientQueue[T]) = front match {
    case Nil if rear.isEmpty => throw new NoSuchElementException("dequeue on empty queue")
    case Nil =>
      val newFront = rear.reverse
      (newFront.head, new EfficientQueue[T](newFront.tail, Nil))
    case h :: t => (h, new EfficientQueue[T](t, rear))
  }

  override def isEmpty: Boolean = front.isEmpty && rear.isEmpty

  override def toString: String = s"(${(front ++ rear.reverse).mkString(", ")})"

  override def equals(obj: Any): Boolean = obj match {
    case other: EfficientQueue[T] => (front ++ rear.reverse) == (other.front ++ other.rear.reverse)
    case _ => false
  }

  // Sobrecarga de hashCode para que coincida con equals
  override def hashCode(): Int = (front ++ rear.reverse).hashCode()
}

@main def testImmutableQueue(): Unit = {
  val squeue = new EfficientQueue[Int]()
  val q = squeue.enqueue(1).enqueue(2).enqueue(3).enqueue(4)
  assert(q.dequeue() == (1, EfficientQueue(2, 3, 4)), s"${q.dequeue()} should be equal to (1, SimpleQueue(List(2, 3, 4)))")
  assert(squeue.isEmpty, s"{q} should be empty")
  assert(!q.isEmpty, s"{q should not be empty")
  val q2 = EfficientQueue(1, 2, 3, 4)
  assert(q == q2, s"${q} and ${q2} should be equal")
  assert(q.hashCode() == q2.hashCode(), s"The hash codes of ${q} and ${q2} should be equal")
}
