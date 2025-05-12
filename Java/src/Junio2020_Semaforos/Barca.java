package Junio2020_Semaforos;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Barca {

	private Semaphore viaje;
	private Semaphore suben;
	private Semaphore bajan;
	private Semaphore[] embarcadero;
	private int pasajeros;
	private int orilla;

	public Barca(){
		this.pasajeros = 0;
		this.orilla = 1;
		embarcadero = new Semaphore[2];
		embarcadero[0] = new Semaphore(0);
		embarcadero[1] = new Semaphore(1);
		viaje = new Semaphore(0);
		suben = new Semaphore(1);
		bajan = new Semaphore(0);
	}

	/*
	 * El Pasajero id quiere darse una vuelta en la barca desde la orilla pos
	 */
	public  void subir(int id,int pos) throws InterruptedException{
		if (pos == orilla) {
			embarcadero[orilla].acquire();
			suben.acquire();
			System.out.println("El pasajero " + id + " sube a la barca, orilla " + orilla);
			pasajeros++;
			if (pasajeros < 3) {
				suben.release();
				embarcadero[orilla].release();
			} else {
				viaje.release();
			}
		}
	}
	
	/*
	 * Cuando el viaje ha terminado, el Pasajero que esta en la barca se baja
	 */
	public  int bajar(int id) throws InterruptedException{
		bajan.acquire();
		System.out.println("El pasajero " + id + " baja de la barca");
		pasajeros--;
		if (pasajeros > 0) bajan.release();
		if (pasajeros == 0) {
			System.out.println("			Barca vacia");
			suben.release();
		}
		return orilla;
	}
	/*
	 * El Capitan espera hasta que se suben 3 pasajeros para comenzar el viaje
	 */
	public  void esperoSuban() throws InterruptedException{
		viaje.acquire();
		System.out.println("La barca esta completa, comienza su viaje");
	}
	/*
	 * El Capitan indica a los pasajeros que el viaje ha terminado y tienen que bajarse
	 */
	public  void finViaje() throws InterruptedException{
		orilla = (orilla + 1) % 2;
		System.out.println("La barca llega a la orilla " + orilla);
		embarcadero[orilla].release();
		bajan.release();
	}

}
