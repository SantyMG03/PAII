package EjerciciosBasicos

import scala.io.StdIn

class Cajero(private var saldo: Double):
  def checkSaldo(): Unit =
    println(s"Saldo actual: $saldo")

  def depositar(cantidad: Double): Unit =
    if cantidad > 0 then
      saldo += cantidad
      println(s"Cantidad depositada. Nuevo saldo $saldo")
    else
      println("Cantidad invalida")

  def retirar(cantidad: Double): Unit =
    if cantidad > 0 && cantidad <= saldo then
      saldo -= cantidad
      println(s"Cantidad retirada. Nuevo saldo $saldo")
    else if cantidad > saldo then
      println("Saldo insuficiente")
    else
      println("Cantidad invalida")

@main def cajeroApp(): Unit =
  val scanner = StdIn
  val cajero = Cajero(100)

  var continuar = true
  while continuar do
    println("\n--- Cajero Automático ---")
    println("1. Verificar saldo")
    println("2. Depositar")
    println("3. Retirar")
    println("4. Salir")
    print("Seleccione una opción: ")
    val opt = scanner.readInt()

    opt match
      case 1 => cajero.checkSaldo()
      case 2 =>
        println("Ingresa la cantidad a depositar: ")
        val deposito = scanner.readDouble()
        cajero.depositar(deposito)
      case 3 =>
        println("Ingresa la cantidad a retirar: ")
        val deposito = scanner.readDouble()
        cajero.retirar(deposito)
      case 4 =>
        println("Gracias por usar el cajero. Adios :)")
        continuar = false
      case _ => println("Opcion invalida")