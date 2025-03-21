package eda;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.apache.commons.codec.language.Soundex;
import info.debatty.java.stringsimilarity.QGram;

public class ManyLibraries {

    private static String getSoundex(String str) {

        Soundex soundex = new Soundex();

        return soundex.encode(str);

    }


    public static double soundex(String word1, String word2) {

        double simil = 0;
        String encodeWord1 = ManyLibraries.getSoundex(word1);
        String encodeWord2 = ManyLibraries.getSoundex(word2);

        for(int i=0; i<4; i++) {
            if(encodeWord1.charAt(i) == encodeWord2.charAt(i))
                simil += 0.25;
        }
        return simil;
    }


    public static float levenshteinDistance(String str1, String str2) {

        LevenshteinDistance ldApache = new LevenshteinDistance();
        
        return 1 - ((float) ldApache.apply(str1, str2) / Math.max(str1.length(), str2.length()));

    }


    public static void printTokens3(String str) {
        QGram qg = new QGram(3);

        System.out.println(qg.getProfile(str));

        
    }

    public static float similarity3(String str1, String str2) {

        QGram qg = new QGram(3);
        
        return (float) qg.distance(str1, str2);
    }


}

