package EjerciciosBasicos

import scala.io.StdIn

def esPalindromo(str: String): Boolean =
  val clear = str.toLowerCase.replaceAll("[^a-zA-Z0-9]", "")
  clear == clear.reverse

@main def verificarPalindromo(): Unit =
  print("Ingrese una cadena: ")
  val entrada = StdIn.readLine()
  if esPalindromo(entrada) then
    println("Es un palíndromo.")
  else
    println("No es un palíndromo.")