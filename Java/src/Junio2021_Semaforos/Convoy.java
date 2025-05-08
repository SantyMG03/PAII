package Junio2021_Semaforos;

import java.util.concurrent.Semaphore;

public class Convoy {

	private int tam;
	private int size;
	private Semaphore esperaLleno;
	private Semaphore viaje;
	private Semaphore esperaVacio;
	private Semaphore ultimo;

	public Convoy(int tam) {
		//TODO
		this.tam = tam;
		this.size = 0;
		esperaLleno = new Semaphore(1);
		viaje = new Semaphore(0);
		esperaVacio = new Semaphore(0);
		ultimo = new Semaphore(0);
	}
	
	/**
	 * Las furgonetas se unen al convoy
	 * La primera es la lider, el resto son seguidoras 
	 **/
	public int unir(int id) throws InterruptedException {
		//TODO: Poner los mensajes donde corresponda
		esperaLleno.acquire();
		if (size == 0){
			System.out.println("** Furgoneta " +id + " lidera del convoy **");
		} else {
			System.out.println("Furgoneta "+id+" seguidora");
		}
		size++;
		if(size == tam) viaje.release();
		else esperaLleno.release();

		return 0;
	}

	/**
	 * La furgoneta lider espera a que todas las furgonetas se unan al convoy 
	 * Cuando esto ocurre calcula la ruta y se pone en marcha
	 * */
	public void calcularRuta(int id) throws InterruptedException {
		//TODO
		viaje.acquire();
		System.out.println("** Furgoneta "+id+" lider:  ruta calculada, nos ponemos en marcha **");
	}
	
	
	/** 
	 * La furgoneta lider avisa al las furgonetas seguidoras que han llegado al destino y deben abandonar el convoy
	 * La furgoneta lider espera a que todas las furgonetas abandonen el convoy
	 **/
	public void destino(int id) throws InterruptedException {
		//TODO
		System.out.println("** 				Destino alcanzado			 **");
		esperaVacio.release();
		ultimo.acquire();
		System.out.println("** Furgoneta "+id+" lider abandona el convoy **");
		size--;
		esperaLleno.release();
	}

	/**
	 * Las furgonetas seguidoras hasta que la lider avisa de que han llegado al destino
	 * y abandonan el convoy
	 **/
	public void seguirLider(int id) throws InterruptedException {
		//TODO
		esperaVacio.acquire();
		System.out.println("Furgoneta "+id+" abandona el convoy");
		size--;
		if (size > 1) esperaVacio.release();
		else ultimo.release();
	}

	
	
	/**
	* Programa principal. No modificar
	**/
	public static void main(String[] args) {
		final int NUM_FURGO = 10;
		Convoy c = new Convoy(NUM_FURGO);
		Furgoneta flota[ ]= new Furgoneta[NUM_FURGO];
		
		for(int i = 0; i < NUM_FURGO; i++)
			flota[i] = new Furgoneta(i,c);
		
		for(int i = 0; i < NUM_FURGO; i++)
			flota[i].start();
	}

}
