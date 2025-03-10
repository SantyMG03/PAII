package EjerciciosT2

class BankAccount(var saldo: Int) {
  def checkSaldo(): Unit =
    println(s"El saldo actual es $saldo")

  def depositar(n: Int): Unit =
    saldo += n
    println(s"Saldo agregado, nuevo saldo: $saldo")

  def retirar(n: Int): Unit =
    if n > saldo then println(s"Cantidad invalida, saldo insuficiente. Cantidad disponible $saldo")
    else
      saldo -= n
      println(s"Saldo retirado, nuevo saldo: $saldo")
}

@main
def testBankAccount():Unit =
  val acc = new BankAccount(10)
  acc.checkSaldo()
  acc.depositar(35)
  acc.retirar(20)
  acc.retirar(30)
