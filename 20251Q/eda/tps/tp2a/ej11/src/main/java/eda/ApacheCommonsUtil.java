package eda;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.apache.commons.codec.language.Soundex;

public class ApacheCommonsUtil {



    private static String getSoundex(String str) {

        Soundex soundex = new Soundex();

        return soundex.encode(str);

    }


    public static double soundex(String word1, String word2) {

        double simil = 0;
        String encodeWord1 = ApacheCommonsUtil.getSoundex(word1);
        String encodeWord2 = ApacheCommonsUtil.getSoundex(word2);

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
}
