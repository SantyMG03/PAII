package Septiembre2016_Locks;

public class Principal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		int N = 5;
		Mesa mesa = new Mesa(N);
		Jugador[] jug = new Jugador[N];
		for (int i = 0; i<N ; i++){
			jug[i] = new Jugador(i,mesa,N);
		}
		for (int i = 0; i<N ; i++){
			jug[i].start();;
		}
	}

}
