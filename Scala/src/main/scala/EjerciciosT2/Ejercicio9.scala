package EjerciciosT2

import scala.io.StdIn
import scala.util.Try

def readInt(): Int = {
  Iterator.continually {
    print("Introduce un número entero: ")
    Try(StdIn.readInt()).toOption
  }.collectFirst { case Some(numero) => numero }.get
}

@main
def testReadInt(): Unit = {
  val num = readInt()
  println(s"El numero introducido es: $num")
}
