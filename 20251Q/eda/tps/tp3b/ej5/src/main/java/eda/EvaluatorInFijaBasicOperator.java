package eda;


import java.util.HashMap;  
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class EvaluatorInFijaBasicOperator {
	
	// opcion 1

	 private static Map<String, Integer> mapping = new HashMap<String, Integer>()
	 {   { put("+", 0); put("-", 1); put("*", 2); put("/", 3); }  
	 };
	 
	private static boolean[][] precedenceMatriz= 
	{   { true,  true,  false, false }, 
		{ true,  true,  false, false }, 
		{ true,  true,  true,  true  }, 
		{ true,  true,  true,  true }, 
	};

	private boolean getPrecedence(String tope, String current)
	{
		Integer topeIndex;
		Integer currentIndex;
	
		if ((topeIndex= mapping.get(tope))== null)
			throw new RuntimeException(String.format("tope operator %s not found", tope));
		
		if ((currentIndex= mapping.get(current)) == null)
			throw new RuntimeException(String.format("current operator %s not found", current));

		return precedenceMatriz[topeIndex][currentIndex];
	}


	// opcion 2: asumo que _ no es parte de ningun operador posible

	// private static Map<String, Boolean> precendeceMap= new HashMap<String, Boolean>() 
	// {	{
	// 		put("+_+", true); put("+_-", true); put("+_*", false); put("+_/", false);
	// 		put("-_+", true); put("-_-", true); put("-_*", false); put("-_/", false);
	// 		put("*_+", true); put("*_-", true); put("*_*", true); put("*_/", true);
	// 		put("/_+", true); put("/_-", true); put("/_*", true); put("/_/", true);
	// 		put("^_+", true); put("^_-", true); put("^_*", true); put("^_/", true);
	// 	}  };
		
	// private final static String extraSymbol= "_";

	// private boolean getPrecedence2(String tope, String current)
	// {
	// 	Boolean rta= precendeceMap.get(String.format("%s%s%s", tope, extraSymbol, current));
	// 	if (rta == null)
	// 		throw new RuntimeException(String.format("operator %s or %s not found", tope, current));
		
	// 	return rta;
	// }


	
	private Scanner scannerLine;
	
	public EvaluatorInFijaBasicOperator()  {
		Scanner input = new Scanner(System.in).useDelimiter("\\n");
		System.out.print("Introduzca la expresión en notación infija: ");
		String line= input.nextLine();
		input.close();
		
		scannerLine = new Scanner(line).useDelimiter("\\s+");
	}


	// esto es del ej3_3-4
	public Double evaluate() {
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



	
	private String infijaToPostfija()
	{
		StringBuilder postfija = new StringBuilder(); // lo cambio a StringBuilder
		Stack<String> theStack= new Stack<String>();
		

		// voy a usar opción 1, mapa de precedencia
		while( scannerLine.hasNext() )
		{
			String token = scannerLine.next();
			
			// si es operador
			if(token.matches("[+\\-*/]")) {
				// si no está vacío y si la precedencia es mayor
				while(!theStack.isEmpty() && getPrecedence(theStack.peek(), token)) {
					postfija.append(theStack.pop());
					postfija.append(" ");
				}

				theStack.push(token);
			}
			// si es número
			else if(token.matches("-?[0-9]+(\\.[0-9]*)?")) {
				postfija.append(token);
				postfija.append(" ");
			}
		}

		while (!theStack.isEmpty()) {
			postfija.append(theStack.pop()).append(" ");
		}


		System.out.println("Postfija= " + postfija);
		return postfija.toString();
	}		



	
	public static void main(String[] args) {

		EvaluatorInFijaBasicOperator e = new EvaluatorInFijaBasicOperator();


		// !! modifico esto del main para que ande
		String postfija = e.infijaToPostfija();
		e.scannerLine = new Scanner(postfija).useDelimiter("\\s+");
		System.out.println("Resultado: " + e.evaluate());


	}
}

// mvn compile && mvn exec:java -Dexec.mainClass="eda.EvaluatorInFijaBasicOperator"

// 2 - 3 * -3
// devuelve: 11