package Septiembre2022_Locks;



public class Tren {
	
	private int nPasajeros = 0;
	private final int MAX_TOTAL = 10;
	private boolean viaje = false;
	private boolean subiendo = true;
	private boolean bajando = false;

	public synchronized void viaje(int id) throws InterruptedException {
		while (nPasajeros == MAX_TOTAL || bajando) wait();
		if (nPasajeros <= 5) {
			System.out.println("El pasajero " + id + " sube al vagon 1");
		} else {
			System.out.println("El pasajero " + id + " sube al vagon 2");
		}
		nPasajeros++;
		if (nPasajeros == MAX_TOTAL) {
			subiendo = false;
			viaje = true;
			notify();
		}
		while (!bajando) wait();
		System.out.println("El pasajero " +id+ " baja de su bagon");
		nPasajeros--;
		if (nPasajeros > 0) {
			notify();
		} else if (nPasajeros == 0) {
			subiendo = true;
			bajando = false;
			notifyAll();
		}
	}

	public synchronized void empiezaViaje() throws InterruptedException {
		while (!viaje) wait();
		System.out.println("        Maquinista:  empieza el viaje");
	}
	public synchronized void finViaje() throws InterruptedException  {
		System.out.println("        Maquinista:  fin del viaje");
		bajando = true;
		viaje = false;
		notifyAll();
	}
}
