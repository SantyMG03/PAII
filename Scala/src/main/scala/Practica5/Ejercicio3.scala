package Practica5

import java.util.concurrent.*
import scala.util.Random
object aseo{
  // CS-Cliente: Esperan si está el Equipo de Limpieza en el aseo
  // CS-EquipoLimpieza: Espera si hay clientes en el aseo

  // ...
  var numClientes = 0
  private val esperaLimpieza = Semaphore(0) // CS-EquipoLimpieza
  private val esperaCliente = Semaphore(1) // CS-Cliente

  def entraCliente(id:Int)={
    // ...
    esperaCliente.acquire()
    numClientes += 1
    log(s"Entra cliente $id. Hay $numClientes clientes.")
    esperaCliente.release()
    // ...
  }
  def saleCliente(id:Int)={
    // ...
    esperaCliente.acquire()
    numClientes -= 1
    log(s"Sale cliente $id. Hay $numClientes clientes.")
    if numClientes == 0 then esperaLimpieza.release()
    else esperaCliente.release()
    // ...
  }
  def entraEquipoLimpieza ={
    // ...
    esperaLimpieza.acquire()
    log(s"        Entra el equipo de limpieza.")
    // ...
  }
  def saleEquipoLimpieza = {
    // ...
    log(s"        Sale el equipo de limpieza.")
    esperaCliente.release()
    // ...
  }
}

object Ejercicio3 {
  def main(args: Array[String]) = {
    val cliente = new Array[Thread](10)
    for (i <- 0 until cliente.length)
      cliente(i) = thread {
        while (true)
          Thread.sleep(Random.nextInt(500))
          aseo.entraCliente(i)
          Thread.sleep(Random.nextInt(50))
          aseo.saleCliente(i)
      }
    val equipoLimpieza = thread {
      while (true)
        Thread.sleep(Random.nextInt(500))
        aseo.entraEquipoLimpieza
        Thread.sleep(Random.nextInt(100))
        aseo.saleEquipoLimpieza
    }
  }
}
