package Practica7

// Modificada esta clase para poder hacer la suma en worker
case class PrimesPair(prime1: Long, prime2: Long, position: Int) :
  override def toString: String = pos + ":(" + prime1 + "," + prime2 + ")"

  def pos: Int = position
