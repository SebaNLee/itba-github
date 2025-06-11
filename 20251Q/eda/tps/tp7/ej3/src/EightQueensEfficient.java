

public class EightQueensEfficient {
	Integer[] tablero;
	boolean[] diag;
	boolean[] contradiag;


	public static void main(String[] args) {
		new EightQueensEfficient(4).solve();
	}

	public EightQueensEfficient(int dim) {
		tablero = new Integer[dim];
		diag = new boolean[2*dim-1];
		contradiag = new boolean[2*dim-1];
	}
	
	public void solve() {
		solveHelper(0);
	}
	
	public void solveHelper(int col) {
		if (col == tablero.length) {
			print();
			return;
		}
		
		for(int row= 0; row < tablero.length; row++) {
			
			if ( check(row, col) )    { 

				tablero[row] = col; // resolver un caso pendiente
				diag[row+col] = true;
				contradiag[row-col+ tablero.length-1] = true;

				solveHelper(col+1);		// explorar nuevos pendientes si satisface restricciones
			
				tablero[row]= null;  // quitar el pendiente generado
				diag[row+col] = false;
				contradiag[row-col+tablero.length-1] = false;
			}
		}
	}
			
	private boolean check(int row, int col) {
		return tablero[row] == null && !diag[row + col] && !contradiag[row - col + tablero.length - 1];
	}

	private void print() {
		for (Integer integer : tablero) {
			for (int col = 0; col < tablero.length; col++) {
				if (integer == col)
					System.out.print("Q");
				else
					System.out.print("-");
			}
			System.out.println();
		}
		System.out.println("\n");
	}
}


// copiado de Marengo