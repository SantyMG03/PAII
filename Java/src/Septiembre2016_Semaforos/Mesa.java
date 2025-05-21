package Septiembre2016_Semaforos;

import java.util.concurrent.*;
public class Mesa {

	private int[] lanzamientos;
	private Semaphore[] jugadores;
	private Semaphore mutex;
	private int nJugadores;
	private int nJugagadas;
	private boolean hayGanador;

	public Mesa(int N){
		this.nJugadores = N;
		this.nJugagadas = 0;
		this.mutex = new Semaphore(1);
		this.jugadores = new Semaphore[N];
		for (int i = 0; i < N; i++)
			jugadores[i] = new Semaphore(1);
		this.lanzamientos = new int[N];
		this.hayGanador = false;
	}

	/**
	 * 
	 * @param id del jugador que llama al método
	 * @param cara : true si la moneda es cara, false en otro caso
	 * @return un valor entre 0 y N. Si devuelve N es que ningún jugador 
	 * ha ganado y hay que repetir la partida. Si  devuelve un número menor que N, es el id del
	 * jugador ganador.
	 * 
	 * Un jugador llama al método nuevaJugada cuando quiere poner
	 * el resultado de su tirada (cara o cruz) sobre la mesa.
	 * El jugador deja su resultado y, si no es el último, espera a que el resto de 
	 * los jugadores pongan sus jugadas sobre la mesa.
	 * El último jugador comprueba si hay o no un ganador, y despierta
	 * al resto de jugadores
	 *  
	 */
	public int nuevaJugada(int id,boolean cara) throws InterruptedException{
		jugadores[id].acquire();
		mutex.acquire();
		nJugagadas++;
		if (cara) lanzamientos[id]++;
		if (nJugagadas == nJugadores) { // Caso de ultimo lanzamiento
			int i = 0;
			int n1 = 0;
			while (i < nJugadores) {
				if (lanzamientos[i] == 1) n1++;
				i++;
			}
			if (n1 == 1) {
				i = 0;
				while (i < nJugadores && lanzamientos[i] != 1) {
					i++;
				}
				System.out.println("El ganador es: " + i);
				hayGanador = true;
				mutex.release();
			} else {
				System.out.println("Hay empate con: " + n1 + " caras");
				nJugagadas = 0;
				for (int j = 0; j < nJugadores; j++) {
					lanzamientos[j] = 0;
					jugadores[j].release();
				}
				mutex.release();
			}
		} else {
			mutex.release();
		}

		if (hayGanador) {
			return nJugadores - id;
		} else {
			return nJugadores;
		}
	}
}
