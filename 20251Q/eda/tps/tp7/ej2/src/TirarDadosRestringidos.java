import java.util.ArrayList;


public class TirarDadosRestringidos {
	public static void main(String[] args) {
		new TirarDadosRestringidos().solve(3, 10);
	}
	
	public void solve(int cantDadosPendientes, int umbral) {
		ArrayList<Integer> auxi= new ArrayList<>();
		solveHelper( cantDadosPendientes, auxi, umbral, 0);
	}

	private void solveHelper(int cantDadosPendientes, ArrayList<Integer> auxi, int umbral, int acum) {

		// V2, con Backtracking
		// int sum = 0;
		// for (int curr : auxi) {
		// 	sum += curr;
		// }

		// if(sum + cantDadosPendientes * 1 > umbral) {
		// 	return;
		// }

		// V3, Programación Dinámica
		if(acum + cantDadosPendientes * 1 > umbral) {
			return;
		}


		if (cantDadosPendientes == 0)
		{
			// V1, ineficiente
			// chqueo si se superó umbral 
			// int sum = 0;
			// for(int curr : auxi) {
			// 	sum += curr;
			// }

			// if(sum > umbral)
			// 	return;

			System.out.println(auxi);
			return;
		}

		for(int rec= 1; rec <= 6; rec++) {
			auxi.add(rec);					// resolver un caso pendiente

			// V3
			solveHelper(cantDadosPendientes-1, auxi, umbral, acum + rec);  // explorar nuevos pendientes
			
			auxi.remove( auxi.size() - 1);  // quitar el pendiente generado
		}
	}
}
