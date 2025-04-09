package core;

import java.util.Scanner;  
import java.util.Stack;

public class EvaluatorPostija {

	
 	private Scanner scannerLine;
	

	public EvaluatorPostija()
	{
	    Scanner inputScanner = new Scanner(System.in).useDelimiter("\\n");
	    System.out.print("Introduzca la expresiOn en notaciOn postfija: ");
	    inputScanner.hasNextLine();

	    String line = inputScanner.nextLine();

	    scannerLine = new Scanner(line).useDelimiter("\\s+");

	}
	
	public Double evaluate()
	{
		Stack<Double> auxi= new Stack<Double>();

		while( scannerLine.hasNext() )
		{
			// bla bla
		}
	}

	
	
	
		
	public static void main(String[] args) {
		Double rta = new EvaluatorPostija().evaluate();
		System.out.println(rta);
	}
	

}
