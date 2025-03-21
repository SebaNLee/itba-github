package eda;

public class ManyLibrariesTester {
    public static void main(String[] args) {
        

        System.out.println(ManyLibraries.levenshteinDistance("exkusa", "ex-amigo"));

        System.out.println(ManyLibraries.soundex("maven", "meibem"));

        System.out.println(ManyLibraries.similarity3("john", "joe"));

        ManyLibraries.printTokens3("alal");
    }
}

// mvn compile && mvn exec:java -Dexec.mainClass="eda.ManyLibrariesTester"