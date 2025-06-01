package Junio2016_Locks;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Aseos {

	ReentrantLock lock = new ReentrantLock(true);
	Condition entraCli = lock.newCondition();
	Condition entraLim = lock.newCondition();
	int numCli = 0;
	boolean limpieza = false;
	
	/**
	 * Utilizado por el cliente id cuando quiere entrar en los aseos
	 * CS Version injusta: El cliente espera si el equipo de limpieza está trabajando
	 * CS Version justa: El cliente espera si el equipo de limpieza está trabajando o
	 * está esperando para poder limpiar los aseos
	 * 
	 */
	public void entroAseo(int id) throws InterruptedException {
		lock.lock();
		while (limpieza) entraCli.await();
		numCli++;
		System.out.println("Entra al aseo el cliente " +id+ ". Hay " +numCli);
		lock.unlock();
	}

	/**
	 * Utilizado por el cliente id cuando sale de los aseos
	 * 
	 */
	public void salgoAseo(int id){
		lock.lock();
		numCli--;
		System.out.println("Sale del aseo el cliente " +id+ ". Hay " +numCli);
		if (numCli == 0) entraLim.signal();
		lock.unlock();
	}
	
	/**
	 * Utilizado por el Equipo de Limpieza cuando quiere entrar en los aseos 
	 * CS: El equipo de trabajo está solo en los aseos, es decir, espera hasta que no
	 * haya ningún cliente.
	 * 
	 */
    public void entraEquipoLimpieza() throws InterruptedException {
		lock.lock();
		while (numCli > 0) entraLim.await();
		limpieza = true;
		System.out.println("Entra el equipo de limpieza");
		lock.unlock();
	}
    
    /**
	 * Utilizado por el Equipo de Limpieza cuando  sale de los aseos 
	 * 
	 * 
	 */
    public void saleEquipoLimpieza(){
    	lock.lock();
		System.out.println("Sale el equipo de limpieza");
		limpieza = false;
		entraCli.signalAll();
		lock.unlock();
	}
}
