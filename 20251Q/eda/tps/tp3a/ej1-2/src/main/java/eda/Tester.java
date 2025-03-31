package eda;


public class Tester {
    public static void main(String[] args) {
        IndexService myIndex = new IndexWithDuplicates();

        myIndex.insert(20);
        myIndex.insert(80);
        myIndex.insert(20);

        myIndex.sortedPrint();

        myIndex.initialize(new int[] {100, 50, 30, 50, 80, 100, 100, 30});

        System.out.println(myIndex.search(20));

        System.out.println(myIndex.occurrences(50));

        myIndex.delete(50);

        System.out.println(myIndex.occurrences(50));

        myIndex.sortedPrint();

        
    }
}




// mvn compile && mvn exec:java -Dexec.mainClass="eda.Tester"