public class mainExactSearch {
    public static void main(String[] args) {
//        char[] target= "abracadabra".toCharArray();
//        char[] query= "ra".toCharArray();
//        System.out.println( ExactSearch.indexOf( query, target) ); //2
//
//        target= "abracadabra".toCharArray();
//        query= "abra".toCharArray();
//        System.out.println(ExactSearch.indexOf( query, target) ); //0
//
//        target= "abracadabra".toCharArray();
//        query= "aba".toCharArray();
//        System.out.println(ExactSearch.indexOf( query, target) ); //-1
//
//        target= "ab".toCharArray();
//        query= "aba".toCharArray();
//        System.out.println(ExactSearch.indexOf( query, target) ); //-1
//
//        target= "xa".toCharArray();
//        query= "aba".toCharArray();
//        System.out.println(ExactSearch.indexOf( query, target) ); //-1
//
//        target= "abracadabras".toCharArray();
//        query= "abras".toCharArray();
//        System.out.println(ExactSearch.indexOf( query, target) ); //7

        KMP kmp = new KMP();
        //System.out.println(kmp.nextComputation("abracadabra".toCharArray()));

        char[] target= "ababcabcabababd".toCharArray();
        char[] query= "ababd".toCharArray();
        System.out.println(kmp.indexOf( query, target) ); //2

//        target= "abracadabra".toCharArray();
//        query= "abra".toCharArray();
//        System.out.println(kmp.indexOf( query, target) ); //0
//
//        target= "abracadabra".toCharArray();
//        query= "aba".toCharArray();
//        System.out.println(kmp.indexOf( query, target) ); //-1
//
//        target= "ab".toCharArray();
//        query= "aba".toCharArray();
//        System.out.println(kmp.indexOf( query, target) ); //-1
//
//        target= "xa".toCharArray();
//        query= "aba".toCharArray();
//        System.out.println(kmp.indexOf( query, target) ); //-1

        target= "abracadabras".toCharArray();
        query= "abras".toCharArray();
        System.out.println(kmp.indexOf( query, target) ); //7

//        query= "no".toCharArray();
//        target= "sino se los digo no se si es nocivo".toCharArray();
//        System.out.println(kmp.findAll(query, target)); //2, 17, 29
//        query= "ni".toCharArray();
//        target= "sino se los digo no se si es nocivo".toCharArray();
//        System.out.println(kmp.findAll(query, target)); //empty arraylist
//        query= "aaa".toCharArray();
//        target= "aaabaaaaab".toCharArray();
//        System.out.println(kmp.findAll(query, target)); // 0, 4, 5, 6
    }
}
