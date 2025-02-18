package EjerciciosBasicos

import scala.io.StdIn

def esPrimo(v: Int): Boolean =
  if v < 2 then false
  else !(2 until math.sqrt(v).toInt + 1).exists(v % _ == 0)

def primerosPrimos(n: Int): List[Int] =
  LazyList.from(2).filter(esPrimo).take(n).toList

@main def imprimirPrimos(): Unit =
  print("Ingrese la cantidad de números primos a generar: ")
  val cantidad = StdIn.readInt()
  println(s"Los primeros $$cantidad números primos son: ")
  println(primerosPrimos(cantidad).mkString(", "))
  