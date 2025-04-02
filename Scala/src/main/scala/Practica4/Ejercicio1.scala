package Practica4

object Ejercicio1 {
  class MiHebra(t: Int, c: Char, miId: Int, turno: Array[Int]) extends Thread {
    override def run(): Unit = {
      for (_ <- 1 to t) {
        while (turno(0) != miId) Thread.sleep(0) // Espera activa
        for (_ <- 1 to miId) { // Imprime la cantidad correcta antes de cambiar el turno
          print(c)
        }
        turno(0) = (turno(0) % 3) + 1 // Cambia turno: 1 -> 2 -> 3 -> 1
      }
    }
  }

  def main(args: Array[String]): Unit = {
    val t = 3
    val turno = Array(1) // Turno inicial para la hebra A

    val h1 = new MiHebra(t, 'A', 1, turno)
    val h2 = new MiHebra(t, 'B', 2, turno)
    val h3 = new MiHebra(t, 'C', 3, turno)

    h1.start()
    h2.start()
    h3.start()

    h1.join()
    h2.join()
    h3.join()

    println() // Salto de línea final
  }
}
