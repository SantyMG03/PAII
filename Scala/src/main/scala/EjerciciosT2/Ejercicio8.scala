package EjerciciosT2

class Book(val title: String, val author: String, val yearPublished: Int) {

  def older(that: Book): Book =
    if this.yearPublished < that.yearPublished then this
    else that

  override def toString: String = s"$title | $author | $yearPublished"
}

@main
def testBook(): Unit =
  val b1 = new Book("One piece", "Oda", 1997)
  val b2 = new Book("Stormligth", "Sanderson", 2011)

  println(s"El mas antiguo es: ${b1 older b2}")
