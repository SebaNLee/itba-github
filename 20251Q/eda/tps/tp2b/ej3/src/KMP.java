public class KMP {

    // provisto por cátedra
    private static int[] nextComputation(char[] query) {
		int[] next = new int[query.length];

	     int border=0;  // Length of the current border

	     int rec=1;  
	     while(rec < query.length){
	          if(query[rec]!=query[border]){  
	               if(border!=0)  
	                    border=next[border-1]; 
	               else  
	                    next[rec++]=0;  
	          }
	          else{ 
	        	   border++;  
	               next[rec]=border;  
	               rec++;  
	          }
	     }
	     return next;
	}

    // algoritmo original
    private static int[] nextComputation2(char[] query) {
		int[] next = new int[query.length];
		next[0] = 0;     // Always. There's no proper border.
		int border = 0;  // Length of the current border
		for (int rec = 1; rec < query.length; rec++) {
			while ((border > 0) && (query[border] != query[rec]))
				border = next[border - 1];     // Improving previous computation
			if (query[border] == query[rec])
				border++;
			// else border = 0;  // redundant
			next[rec] = border;
		}
		return next;
	}




    public static int indexOf(char[] query, char[] target) {

        int[] next = nextComputation(query);

        int rec = 0;
        int pquery = 0;

        while(rec < target.length) {

            // caso coinciden los caracteres
            if(target[rec] == query[pquery]) {
                rec++;
                pquery++;
            }

            // se encontró coincidencia completa
            if(pquery == query.length) {
                return rec - pquery;
            }
            else {
                // caso no matchea
                if(rec < target.length && target[rec] != query[pquery]) {

                    // si no es el primero, chequeo vector next para repeticiones
                    if(pquery != 0) {
                        pquery = next[pquery - 1];
                    }
                    else {
                        rec++;
                    }
                }
            }

        }

        // si no se encontró
        return -1;
    }


    
}
