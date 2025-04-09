package eda;

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
			String token = scannerLine.next();

			// validación, imprimo
			System.out.print(token); 
			System.out.print(" ");

			// chequeo si es operación
			if(token.matches("[+\\-*/]")) {
				Double b = auxi.pop(); // !! hay que popear en orden inverso
				Double a = auxi.pop();

				switch (token) {
					case "+":
						auxi.push(a + b);
						break;
						
					case "-":
						auxi.push(a - b);
						break;

					case "/":
					auxi.push(a / b);
						break;

					case "*":
					auxi.push(a * b);
						break;
				}

			}
			else if(token.matches("-?[0-9]+(\\.[0-9]*)?")) {
				auxi.push(Double.parseDouble(token));
			}
		}

		System.out.println();
		return auxi.pop();
	}

	
		
	public static void main(String[] args) {
		Double rta = new EvaluatorPostija().evaluate();
		System.out.println(rta);
	}
	

}


// mvn compile && mvn exec:java -Dexec.mainClass="eda.EvaluatorPostija"

// -9 -1 - 10 2 * / 1 5 - 2 -3 / / *
// devuelve = -2.4