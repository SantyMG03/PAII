package Septiembre2018_Semaforos;


import java.util.concurrent.Semaphore;

public class Aseo {

	private final Semaphore mutex = new Semaphore(1);
	private final Semaphore hombresEsperando = new Semaphore(0);
	private final Semaphore mujeresEsperando = new Semaphore(0);

	private int hombresDentro = 0;
	private int mujeresDentro = 0;
	private int hombresEnEspera = 0;
	private int mujeresEnEspera = 0;

	public void llegaHombre(int id) throws InterruptedException {
		mutex.acquire();
		if (mujeresDentro > 0) {
			hombresEnEspera++;
			mutex.release(); // Para no generar bloqueos suelto el mutex
			hombresEsperando.acquire(); // Bloqueo la ejecucion hasta que salgan todas las mujeres
			mutex.acquire(); // Vuelvo a tomarlo para la exclusion mutua
			hombresEnEspera--;
		}
		hombresDentro++;
		System.out.println("Entra el hombre " + id + ". Hombres dentro: " + hombresDentro);
		mutex.release();
	}

	public void saleHombre(int id) throws InterruptedException {
		mutex.acquire();
		hombresDentro--;
		System.out.println("Sale el hombre " + id + ". Hombres dentro: " + hombresDentro);
		if (hombresDentro == 0 && mujeresEnEspera > 0) {
			mujeresEsperando.release(mujeresEnEspera);
		}
		mutex.release();
	}

	public void llegaMujer(int id) throws InterruptedException {
		mutex.acquire();
		if (hombresDentro > 0) {
			mujeresEnEspera++;
			mutex.release();
			mujeresEsperando.acquire();
			mutex.acquire();
			mujeresEnEspera--;
		}
		mujeresDentro++;
		System.out.println("Entra la mujer " + id + ". Mujeres dentro: " + mujeresDentro);
		mutex.release();
	}

	public void saleMujer(int id) throws InterruptedException {
		mutex.acquire();
		mujeresDentro--;
		System.out.println("Sale la mujer " + id + ". Mujeres dentro: " + mujeresDentro);
		if (mujeresDentro == 0 && hombresEnEspera > 0) {
			hombresEsperando.release(hombresEnEspera);
		}
		mutex.release();
	}
}

