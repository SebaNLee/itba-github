import java.util.ArrayList;

public class Levenshtein {
	
	private int[][] matrix;
	private char[] horizontalWord;
	private char[] verticalWord;
	
	public Levenshtein(char[] horizontalWord, char[] verticalWord) {
		
		this.horizontalWord= horizontalWord;
		this.verticalWord= verticalWord;
		
		matrix = new int[ verticalWord.length+1][ horizontalWord.length + 1];
		
		for(int col = 1; col <= horizontalWord.length; col++)
	    {
	        matrix[0][col] = col;
	    }
	 
		for(int row = 1; row <= verticalWord.length; row++)
	    {
	        matrix[row][0] = row;        
	    }
		
		
	}

	
	public int distance() {
		// no se ha calculado antes
		if (verticalWord != null) {
			// calcular
			for(int row=1; row<= verticalWord.length; row++)
			{
				for(int col=1; col<= horizontalWord.length; col++) {
					matrix[row][col] = Math.min(   matrix[row-1][col-1] + (( verticalWord[row-1]== horizontalWord[col-1])?0:1) ,
	                					           Math.min( matrix[row-1][col]+1, matrix[row][col-1]+1 )
	                					);
				}                
			}    
			// no las necesito mas. Las destruyo
			verticalWord= null;
			horizontalWord= null;
		}

		return matrix [ matrix.length-1][ matrix[0].length-1] ;  
	}
	
	
	//  COMPLETAR!!!
	public ArrayList<Character> getOperations() {
		ArrayList<Character> seqOps= new ArrayList<Character>();
		int count = Math.max(matrix.length-1, matrix[0].length-1);
		for(int i=0; i<count; i++)
			seqOps.add(i,'-');
		int row=matrix.length-1, col=matrix[0].length-1;
		while(row>=1 && col>=1) {
			if(matrix[row][col-1]<matrix[row][col]) {
				seqOps.set(--count, 'D');
				col--;
			}
			else if(matrix[row-1][col-1]<matrix[row][col]) {
				seqOps.set(--count,'S');
				row--;
				col--;
			}
			else if(matrix[row-1][col]<matrix[row][col]) {
				seqOps.set(--count, 'I');
				row--;
			}

			else if(matrix[row-1][col-1]==matrix[row][col]) {
				seqOps.set(--count,'-');
				row--;
				col--;
			}
		}

		while(row>=1) {
			seqOps.set(--count, 'I');
			row--;
		}
		while(col>=1) {
			seqOps.set(--count, 'D');
			col--;
		}


		return seqOps;
	}
	

	public static void main(String[] args) {
		
		String p1;
		String p2;
		Levenshtein l;
		
		p1= "elewat";
		p2= "elewaut";
		
		
		l = new Levenshtein(p1.toCharArray(), p2.toCharArray());
		System.out.println(l.distance());
		System.out.println(String.format("las operaciones a realizar para transformar \"%s\" en \"%s\" son:", p1, p2 ) );
		System.out.println( l.getOperations() );
		
	}
}