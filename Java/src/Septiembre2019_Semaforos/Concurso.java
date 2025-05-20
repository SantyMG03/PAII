package Septiembre2019_Semaforos;

import java.util.concurrent.Semaphore;

public class Concurso {

	private Semaphore mutex = new Semaphore(1);
	private Semaphore fin = new Semaphore(0);
	private int[] ronda = new int[2];
	private int[] ganadas = new int[2];
	private int lanzamientos = 0;

	
	public void tirarMoneda(int id,boolean cara) throws InterruptedException {
		mutex.acquire();
		lanzamientos++;
		if (cara) {
			ronda[id]++;
		}
		// Compruebo si ya lanzaron todas las veces
		if (lanzamientos == 20) {
			if (ronda[0] > ronda[1]){ // Gana j0
				System.out.println("Gana el jugador 0");
				ganadas[0]++;
			} else if (ronda[0] < ronda[1]) { // Gana j1
				System.out.println("Gana el jugador 1");
				ganadas[1]++;
			} else { // Empate
				System.out.println("Empate");
			}

			// Compruebo si ha terminado el concurso
			if (ganadas[0] == 3 || ganadas[1] == 3){
				fin.release();
			} else { // Si no reinicio los contadores
				ronda[0] = 0;
				ronda[1] = 0;
			}
		}
		mutex.release();
	}	
	
	public boolean concursoTerminado() throws InterruptedException {
		fin.acquire();
		System.out.println("Final del concurso");
		if (ganadas[0] == 3){
			System.out.println("El ganador es el jugador 0");
			return true;
		} else if (ganadas[1] == 3) {
			System.out.println("El ganador es el jugador 1");
			return true;
		} else {
			return false;
		}
	}
}