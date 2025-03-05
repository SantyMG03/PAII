package Practica1

import scala.annotation.tailrec

def generateParentheses(n: Int): List[String] = {
  @tailrec
  def backtrack(stack: List[(Int, Int, String)], acc: List[String]): List[String] = {
    stack match {
      case Nil => acc
      case (open, close, current) :: rest =>
        if (open == 0 && close == 0)
          backtrack(rest, current :: acc) // Caso base: Agregar la cadena al resultado acumulado
        else {
          val newStack =
            (if (close > open) List((open, close - 1, current + ")")) else Nil) :::
            (if (open > 0) List((open - 1, close, current + "(")) else Nil) ::: rest

          backtrack(newStack, acc) // Llamada en posición de cola
        }
    }
  }

  backtrack(List((n, n, "")), Nil)
}

@main
def testParentheses(): Unit =
  val res = generateParentheses(2)
  res.foreach(println)