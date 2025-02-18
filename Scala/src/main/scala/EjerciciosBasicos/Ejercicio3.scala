package EjerciciosBasicos

import scala.io.StdIn

def esPalindromo(str: String): Boolean =
  val clear = str.toLowerCase.replaceAll("[^a-zA-Z0-9]", "")
  clear == clear.reverse

@main def verificarPalindromo(): Unit =
  print("Ingrese una cadena para verificar si es palíndromo: ")
  val entrada = StdIn.readLine()
  if esPalindromo(entrada) then
    println("La cadena es un palíndromo.")
  else
    println("La cadena no es un palíndromo.")