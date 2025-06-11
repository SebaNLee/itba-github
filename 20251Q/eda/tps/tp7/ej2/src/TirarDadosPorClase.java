import java.util.ArrayList;


public class TirarDadosPorClase {
	public static void main(String[] args) {
		new TirarDadosPorClase().solve(3, 10);
	}
	
	public void solve(int cantDadosPendientes, int umbral) {
		ArrayList<Integer> auxi= new ArrayList<>();
		solveHelper( cantDadosPendientes, auxi, umbral, 0);
	}

	private void solveHelper(int cantDadosPendientes, ArrayList<Integer> auxi, int umbral, int acum) {

		if(acum + cantDadosPendientes * 1 > umbral) {
			return;
		}

		if (cantDadosPendientes == 0)
		{
			System.out.println(auxi);
			return;
		}

		// tengo que obtener el anterior para el criterio de monótona creciente
		int last = (auxi.isEmpty() ? 1 : auxi.get(auxi.size() - 1));

		// e itero desde el anterior
		for(int rec = last; rec <= 6; rec++) {
			auxi.add(rec);					// resolver un caso pendiente

			solveHelper(cantDadosPendientes-1, auxi, umbral, acum + rec);  // explorar nuevos pendientes
			
			auxi.remove( auxi.size() - 1);  // quitar el pendiente generado
		}
	}
}
