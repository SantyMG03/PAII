package Septiembre2022_Semaforos;


import java.util.concurrent.Semaphore;

public class Tren {

	private int numPasTotal;
	private Semaphore suben;
	private Semaphore viajan;
	private Semaphore bajan;

	public Tren() {
		numPasTotal = 0;
		suben = new Semaphore(1);
		viajan = new Semaphore(0);
		bajan = new Semaphore(0);
	}
	
	public void viaje(int id) throws InterruptedException {
		suben.acquire();
		numPasTotal++;
		if (numPasTotal <= 5)
			System.out.println("El pasajero " + id + " sube al vagon 1");
		else
			System.out.println("El pasajero " + id + " sube al vagon 2");

		if (numPasTotal < 10) suben.release();
		else viajan.release();

		// Comienza la bajada
		bajan.acquire();
		numPasTotal--;
		if (numPasTotal >= 5)
			System.out.println("El pasajero " + id + " baja del vagon 1");
		else
			System.out.println("El pasajero " + id + " baja del vagon 2");

		if (numPasTotal > 0) bajan.release();
		else {
			System.out.println("*********************************************");
			suben.release();
		}
	}

	public void empiezaViaje() throws InterruptedException {
		viajan.acquire();
		System.out.println("        Maquinista:  empieza el viaje");
	}
	public void finViaje() throws InterruptedException  {
		System.out.println("        Maquinista:  fin del viaje");
		bajan.release();
	}
}
