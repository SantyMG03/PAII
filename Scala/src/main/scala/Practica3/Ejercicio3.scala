package Practica3

def existe[A](l:List[A],f:A=>Boolean):Boolean = {
  l.foldRight(false)((e, acc) => acc || f(e))
}

@main
def testEjercicio3(): Unit = {
  val list = List(1,2,3,4,5,6)
  println(existe(List(1,2,3),_>2))
  println(existe(List("Hola","Mundo"),_.length >= 5))
  println(existe(List("Hola","Mundo"),_.length < 3))
}