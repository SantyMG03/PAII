package Septiembre2020_Semaforos;

import java.util.concurrent.Semaphore;

public class Curso {

	//Numero maximo de alumnos cursando simultaneamente la parte de iniciacion
	private final int MAX_ALUMNOS_INI = 10;
	
	//Numero de alumnos por grupo en la parte avanzada
	private final int ALUMNOS_AV = 3;

	private int alumIni = 0;
	private int alumAva = 0;
	private int worked = 0;
	private Semaphore mutex = new Semaphore(1);
	private Semaphore iniciacion = new Semaphore(1);
	private Semaphore avanzado = new Semaphore(1);
	private Semaphore working = new Semaphore(0);
	
	
	//El alumno tendra que esperar si ya hay 10 alumnos cursando la parte de iniciacion
	public void esperaPlazaIniciacion(int id) throws InterruptedException{
		//Espera si ya hay 10 alumnos cursando esta parte
		iniciacion.acquire();
		mutex.acquire();
		alumIni++;
		//Mensaje a mostrar cuando el alumno pueda conectarse y cursar la parte de iniciacion
		System.out.println("PARTE INICIACION: Alumno " + id + " cursa parte iniciacion");
		if (alumIni < 10) iniciacion.release();
		mutex.release();
	}

	//El alumno informa que ya ha terminado de cursar la parte de iniciacion
	public void finIniciacion(int id) throws InterruptedException{
		mutex.acquire();
		System.out.println("PARTE INICIACION: Alumno " + id + " termina parte iniciacion");
		alumIni--;
		if (alumIni < 10) iniciacion.release();
		mutex.release();
		//Libera la conexion para que otro alumno pueda usarla
	}
	
	/* El alumno tendra que esperar:
	 *   - si ya hay un grupo realizando la parte avanzada
	 *   - si todavia no estan los tres miembros del grupo conectados
	 */
	public void esperaPlazaAvanzado(int id) throws InterruptedException{
		//Espera a que no haya otro grupo realizando esta parte
		avanzado.acquire();
		mutex.acquire();
		alumAva++;
		System.out.println("PARTE AVANZADA: Alumno " + id + " espera a que haya " + ALUMNOS_AV + " alumnos");
		if (alumAva == 3) working.release();
		else avanzado.release();
		mutex.release();

		//Espera a que haya tres alumnos conectados
		working.acquire();
		mutex.acquire();
		System.out.println("PARTE AVANZADA: Hay " + ALUMNOS_AV + " alumnos. Alumno " + id + " empieza el proyecto");
		worked ++;
		mutex.release();
	}
	
	/* El alumno:
	 *   - informa que ya ha terminado de cursar la parte avanzada 
	 *   - espera hasta que los tres miembros del grupo hayan terminado su parte 
	 */ 
	public void finAvanzado(int id) throws InterruptedException{
		//Espera a que los 3 alumnos terminen su parte avanzada
		if (worked < 3) {
			working.release();
			System.out.println("PARTE AVANZADA: Alumno " +  id + " termina su parte del proyecto. Espera al resto");
		} else if (worked == 3) {
			mutex.acquire();
			System.out.println("PARTE AVANZADA: LOS " + ALUMNOS_AV + " ALUMNOS HAN TERMINADO EL CURSO");
			worked = 0;
			alumAva -= 3;
			avanzado.release();
			mutex.release();
		}
	}
}
