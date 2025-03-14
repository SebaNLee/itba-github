
public class Performance {

    public static final int N = 1000000000;
	
	
    public static void main(String[] args) {
 
    	MyTimerV2 t2;
    	try {
    		t2= new MyTimerV2();
    		t2.end();
    	}
    	catch(Exception e) {
     	}
        
        int[] myArray = new int[N];
        int rta;
        
    	   // generate array
        for (int rec = N; rec > 0; rec--)
            myArray[N - rec] = rec;

         
        t2= new MyTimerV2();
        rta = AlgoA.max(myArray);
        t2.end();

        System.out.println(String.format("max Algo A %d. Delay %d (ms)", rta, t2.getElapsedTime()));
        
        // generate array
        for (int rec = N; rec > 0; rec--)
            myArray[N - rec] = rec;
        
        t2= new MyTimerV2();
        rta = AlgoB.max(myArray);
        t2.end();
        System.out.println(String.format("max Algo B %d. Delay %d (ms)", rta, t2.getElapsedTime()));

    }

}