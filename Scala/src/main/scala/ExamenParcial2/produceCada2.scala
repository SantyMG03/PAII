/*
Considera un sistema con dos productores y un consumidor. Los tres procesos se comunican a través de un buffer
compartido pero, para acelerar la producción, un productor utiliza las componentes pares del buffer, y el otro las
impares. Resuelve este problema utilizando sólo semáforos binarios, de manera que:
•	el buffer se utiliza en exclusión mutua;
•	el consumidor lee los datos del buffer de forma ordenada, es decir, primero el dato que está en la posición 0,
luego el que está en la posición 1, etc;
•	el buffer se utiliza de forma circular;
•	el productor "par" no puede escribir sobre el buffer si todas las componentes pares están ocupadas;
•	el productor "impar" no puede escribir sobre el buffer si todas las componentes impares están ocupadas;
•	el consumidor no puede leer el dato i-ésimo, si el correspondiente productor (par o impar) no lo ha almacenado
todavía.
Para resolver el problema debes proporcionar una clase Buffer con métodos nuevoDatoPar(Int), nuevoDatoImpar(Int) y
leerDato(), usados por los productores para poner datos en el buffer y por el consumidor para leerlos.
Supón que los datos a almacenar en el buffer son enteros, y que el tamaño del buffer es par.
Las clases Productor y Consumidor simulan el comportamiento de los procesos, suponiendo que todos se ejecutan de
forma ininterrumpida.
*/

import concurrencia.log
import java.util.concurrent.*
import scala.util.Random

class Buffer(tam: Int) {
	if (tam <= 1 || tam % 2 != 0) throw new RuntimeException("Tamaño de Buffer no válido")
	private val buffer = new Array[Int](tam)
	private var iPar = 0 // índice del dato a escribir
	private var iImpar = 1 // índice del dato a escribir
	private var j = 0 // índice del dato a leer

	private var parDisponibles = tam / 2
	private var imparDisponible = tam / 2

	private val mutex = new Semaphore(1)
	private val ponePar = new Semaphore(1)
	private val poneImpar = new Semaphore(1)
	private val consumePar = new Semaphore(0)
	private val consumeImpar = new Semaphore(0)

	def nuevoDatoPar(dato: Int): Unit = {
		ponePar.acquire()
		mutex.acquire()
		buffer(iPar) = dato
		iPar = (iPar + 2) % tam
		parDisponibles -= 1
		log(s"Productor par pone dato " + buffer.mkString("[", ", ", "]"))
		if (parDisponibles > 0) ponePar.release()
		if (parDisponibles < tam / 2) consumePar.release()
		mutex.release()
	}

	def nuevoDatoImpar(dato: Int): Unit = {
		poneImpar.acquire()
		mutex.acquire()
		buffer(iImpar) = dato
		iImpar = (iImpar + 2) % tam
		imparDisponible -= 1
		log("Productor impar pone dato " + buffer.mkString("[", ", ", "]"))
		if (imparDisponible > 0) poneImpar.release()
		if (imparDisponible < tam / 2) consumeImpar.release()
		mutex.release()
		// ... 
	}

	def leerDato: Int = {
		var dato = 0
		if (j % 2 == 0) {
			consumePar.acquire()
			mutex.acquire()
			dato = buffer(j)
			buffer(j) = 0
			parDisponibles += 1
			log("Consumidor lee dato par " + buffer.mkString("[", ", ", "]"))
			j = (j + 1) % tam
			if (parDisponibles == 1) ponePar.release()
			if (parDisponibles < tam / 2) consumePar.release()
			mutex.release()
		} else {
			consumeImpar.acquire()
			mutex.release()
			dato = buffer(j)
			buffer(j) = 0
			j = (j + 1) % tam
			imparDisponible += 1
			log("Consumidor lee dato impar " + buffer.mkString("[", ", ", "]"))
			if (imparDisponible == 1) poneImpar.release()
			if (imparDisponible < tam / 2) consumeImpar.release()
			mutex.release()
		}
		dato
	}
}

class Productor(private var par: Boolean, b: Buffer) extends Thread {
	override def run(): Unit = {
		var dato = 0
		while (true)
			dato = Random.nextInt(1000) + 1
			Thread.sleep(Random.nextInt(2000))
			if (par) b.nuevoDatoPar(dato)
			else b.nuevoDatoImpar(dato)
	}
}

class Consumidor(b: Buffer) extends Thread {
	override def run(): Unit = {
		var dato = 0
		while (true)
			Thread.sleep(Random.nextInt(500))
			dato = b.leerDato
	}
}

@main def produceCada2 =
	val b = Buffer(6)
	val p1 = Productor(true, b)
	val p2 = Productor(false, b)
	val c = Consumidor(b)
	p1.start()
	p2.start()
	c.start()
