package EjerciciosBasicos

import scala.io.StdIn

def mcd(a: Int, b: Int): Int =
  if b == 0 then a
  else mcd(b, a % b)

def mcm(a: Int, b:Int): Int =
  (a * b) / mcd(a, b)


@main def calcularMCDyMCM(): Unit =
  print("Ingrese el primer número: ")
  val num1 = StdIn.readInt()
  print("Ingrese el segundo número: ")
  val num2 = StdIn.readInt()
  println(s"MCD de $num1 y $num2: " + mcd(num1, num2))
  println(s"MCM de $num1 y $num2: " + mcm(num1, num2))