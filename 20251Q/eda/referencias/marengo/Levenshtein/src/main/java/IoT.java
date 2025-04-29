import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;


public class IoT {
 	private Scanner scannerLine;

	public IoT()
	{
	    Scanner inputScanner = new Scanner(System.in).useDelimiter("\\n");
	    System.out.print("Valores sensados (separados por blancos): ");
	    inputScanner.hasNextLine();

	    String line = inputScanner.nextLine();

	    scannerLine = new Scanner(line).useDelimiter("\\s+");
	}

	// completar !!!!
	// mientras haya tokens procesa e imprime en ventanas de tamano "windowSize"
	public void generate(int windowSize)
	{
		if (windowSize < 1)
			throw new RuntimeException("invalid window size");
			// bla bla bla 
		Queue<String> queue = new LinkedList<>();

		while( scannerLine.hasNext() ) {
			queue.add(scannerLine.next());
			if(queue.size()==windowSize) {
				queue.forEach(System.out::print);
				System.out.println();
				queue.remove();
			}
		}
	}

	public static void main(String[] args) {
		new IoT().generate(3);
		new IoT().generate(4);
	}
}