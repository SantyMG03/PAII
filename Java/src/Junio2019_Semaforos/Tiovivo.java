package Junio2019_Semaforos;

import java.util.concurrent.Semaphore;

public class Tiovivo {

	private int cap;
	private int pasajeros;
	private Semaphore suben;
	private Semaphore bajan;
	private Semaphore viaje;

	public Tiovivo(int cap) {
		this.cap = cap;
		this.pasajeros = 0;
		this.suben = new Semaphore(1);
		this.bajan = new Semaphore(0);
		this.viaje = new Semaphore(0);
	}
	
	public void subir(int id) throws InterruptedException {
		suben.acquire();
		pasajeros++;
		System.out.println("El pasajeros " +id+ " sube. Hay " +pasajeros+ " personas subidas");
		if (pasajeros < cap) suben.release();
		else viaje.release();
	}
	
	public void bajar(int id) throws InterruptedException {
		bajan.acquire();
		pasajeros--;
		System.out.println("El pasajeros " +id+ " baja. Hay " +pasajeros+ " personas subidas");
		if (pasajeros > 0) bajan.release();
		else {
			System.out.println("**** Tiovivo vacio, ya se pueden subir ****");
			suben.release();
		}
	}
	
	public void esperaLleno () throws InterruptedException {
		viaje.acquire();
		System.out.println("Tiovivo completo. Inicia el viaje");
	}
	
	public void finViaje () 
	{
		System.out.println("Viaje completo, ya se pueden bajar");
		bajan.release();
	}
}
