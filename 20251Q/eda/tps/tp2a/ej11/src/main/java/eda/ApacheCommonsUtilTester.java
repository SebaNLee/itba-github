package eda;

public class ApacheCommonsUtilTester {
    public static void main(String[] args) {
        

        System.out.println(ApacheCommonsUtil.levenshteinDistance("exkusa", "ex-amigo"));

        System.out.println(ApacheCommonsUtil.soundex("maven", "meibem"));


    }
}


// mvn compile && mvn exec:java -Dexec.mainClass="eda.ApacheCommonsUtilTester"