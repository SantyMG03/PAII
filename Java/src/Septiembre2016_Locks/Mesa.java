package Septiembre2016_Locks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Mesa {

	ReentrantLock lock;
	Condition cTira;
	int[] resultados;
	int tiradas;
	int total;
	int numCaras;
	
	public Mesa(int N){
		lock = new ReentrantLock(true);
		resultados = new int[N];
		cTira = lock.newCondition();
		tiradas = 0;
		total = N;
		numCaras = 0;
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
		lock.lock();
		int res = total;
		while (resultados[id] != 0) cTira.await(); // Si ese jugador ya ha tirado, espera
		tiradas++;
		if (cara) {
			resultados[id] = 1; // Si sale cara lo pongo a 1
			numCaras++;
		}
		if (tiradas == total) { // Si ya han lanzado todos
			System.out.println(Arrays.toString(resultados));
			if (numCaras == 1) {
				System.out.println("Hay ganador");
				int i = 0;
				while (i < total && resultados[i] != 1) {
					i++;
				}
				System.out.println("El jugador " + i);
				res = i;
			} else {
				System.out.println("No hay ganador");
				Arrays.fill(resultados, 0); // Limpio el array de resultados
				tiradas = 0;
				numCaras = 0;
				cTira.signalAll(); // Aviso a todos que no hay gandor
			}
		}
		lock.unlock();
		return res;
	}
}
