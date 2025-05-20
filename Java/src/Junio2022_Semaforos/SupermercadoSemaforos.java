package Junio2022_Semaforos;

import java.util.concurrent.Semaphore;

public class SupermercadoSemaforos implements Supermercado {

	private Cajero permanente;

	private Semaphore mutex = new Semaphore(1, true);
	private Semaphore clien = new Semaphore(0, true);
	private Semaphore perma = new Semaphore(0, true);

	private boolean waiting = false;
	private boolean stop = false;

	private int numClientes = 0;
	
	public SupermercadoSemaforos() throws InterruptedException {
		permanente = new Cajero(this, true); //crea el primer cajero, el permanente
		permanente.start();
	}

	@Override
	public void fin() throws InterruptedException {
		mutex.acquire();
		stop = true;
		System.out.println("Supermercado cerrado!!!");
		perma.release();
		mutex.release();
	}

	@Override
	public void nuevoCliente(int id) throws InterruptedException {
		mutex.acquire();
		if (!stop) {
			numClientes++;
			System.out.println("Llega cliente  " + id + ". Hay " + numClientes);
			if (waiting) {
				waiting = false;
				perma.release();
			} else if (numClientes > Cajero.numCajeros() * 3) {
				Cajero c = new Cajero(this, false);
				System.out.println("Se crea un nuevo cajero " + c.id());
			}
			mutex.release();
			clien.acquire();
		} else {
			System.out.println("El super esta cerrado, me voy");
			mutex.release();
		}
	}

	@Override
	public boolean permanenteAtiendeCliente( int id) throws InterruptedException {
		mutex.acquire();
		if (numClientes == 0 && !stop) {
			waiting = true;
			System.out.println("Cajero permanenente esperando");
			mutex.release();
			perma.acquire();
			mutex.release();
			if (stop && numClientes == 0) {
				System.out.println("Cajero permanente termina: " + (numClientes));
				mutex.release();
				return false;
			}
		}
		if (numClientes > 0) {
			numClientes--;
			System.out.println("Cajero permanente atiende a un cliente. Quedan "+(numClientes));
			clien.release();
			mutex.release();
			return true;
		} else if (stop) {
			System.out.println("Cajero permanente termina: "+(numClientes));
			mutex.release();
			return false;
		}
		return true;
	}
		
	
	@Override
	public boolean ocasionalAtiendeCliente(int id) throws InterruptedException {
		mutex.acquire();
		if (numClientes == 0) { // Si no hay clientes el ocasional se va
			System.out.println("No hay clientes. Cajero "+id+" termina: "+(numClientes));
			mutex.release();
			return false;
		} else { // Si hay clientes el ocasional atiende a uno
			numClientes--;
			clien.release();
			System.out.println("Cajero "+id+" atiende a un cliente: "+(numClientes));
			mutex.release();
			return true;
		}
	}

}
