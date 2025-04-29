package core;

import org.apache.commons.lang3.time.StopWatch;

import java.util.ArrayList;


public class TirarDadosConRestriccionBacktrackingPgmDinamica {
	public static void main(String[] args) {
		new TirarDadosConRestriccionBacktrackingPgmDinamica().solve(3);
	}
	
	public void solve(int cantDadosPendientes) {
		StopWatch stopwatch = new StopWatch();
		stopwatch.start();
		ArrayList<Integer> auxi= new ArrayList<>();
		solveHelper( cantDadosPendientes, auxi, 106, 0);
		stopwatch.stop();
		System.out.println(stopwatch.getNanoTime());
	}

	private void solveHelper(int cantDadosPendientes, ArrayList<Integer> auxi, int umbral, int sumaAcumulada) {

		if (sumaAcumulada + cantDadosPendientes > umbral)
			return;

		if (cantDadosPendientes == 0) {
			System.out.println(auxi);
			return;
		}

		int prev = auxi.isEmpty() ? 1 : auxi.get(auxi.size()-1);
		for(int rec = prev; rec <= 6; rec++) {
			auxi.add(rec);					// resolver un caso pendiente

			solveHelper(cantDadosPendientes-1, auxi, umbral, sumaAcumulada + rec);  // explorar nuevos pendientes
			
			auxi.remove( auxi.size() - 1);  // quitar el pendiente generado
		}		
	}
}
