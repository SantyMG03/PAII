package Septiembre2019_Semaforos;

import java.util.concurrent.Semaphore;

public class Concurso {

	private int[] caras = {0, 0};
	private int[] lanzamientos = {0, 0};
	private int[] ganadas = {0, 0};
	private boolean finConcurso = false;
	private Semaphore esperaTurno = new Semaphore(1);

	public void tirarMoneda(int id,boolean cara) throws InterruptedException {
		esperaTurno.acquire();
		lanzamientos[id]++;
		if (cara) caras[id]++;
		if (lanzamientos[id] + lanzamientos[1 - id] == 20) {
			if (caras[id] > caras[1 - id]) {
				System.out.println("Ha ganado la partida el jugador " + id + " con " +caras[id]+ " caras");
				ganadas[id]++;
			} else if (caras[id] < caras[1 - id]) {
				System.out.println("Ha ganado la partida el jugador " + id + " con " +caras[1 - id]+ " caras");
				ganadas[1 - id]++;
			} else {
				System.out.println("El juego a empatado");
			}

			caras[id] = 0;
			caras[1 - id] = 0;
			lanzamientos[id] = 0;
			lanzamientos[1 - id] = 0;
			if (ganadas[id] == 3 || ganadas[1 - id] == 3) {
				finConcurso = true;
				System.out.print("Final del concurso. Ha ganado ");
				if (ganadas[id] == 3) System.out.println("el jugador " + id);
				else System.out.println("el jugador " + (1 -id));
			}
			esperaTurno.release();
		} else if (lanzamientos[id] == 10) {
			esperaTurno.release();
		} else {
			esperaTurno.release();
		}
	}	
	
	public boolean concursoTerminado() throws InterruptedException {
		return finConcurso;
	}
}