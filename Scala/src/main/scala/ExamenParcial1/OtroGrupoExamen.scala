package ExamenParcial1

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
}