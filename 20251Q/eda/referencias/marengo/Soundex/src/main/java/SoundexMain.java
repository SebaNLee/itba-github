package eda;

import info.debatty.java.stringsimilarity.NGram;
import info.debatty.java.stringsimilarity.QGram;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.language.Metaphone;
import org.apache.commons.codec.language.Soundex;
import org.apache.commons.text.similarity.CosineSimilarity;
import org.apache.commons.text.similarity.LevenshteinDetailedDistance;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.apache.commons.text.similarity.LevenshteinResults;


public class SoundexMain {
    public static void main(String[] args) throws EncoderException {
        System.out.println("---- Soundex ----");
        Soundex soundex = new Soundex();
        System.out.println(soundex.difference("threshold", "hold")/4);

        System.out.println("---- Levenshtein ----");
        LevenshteinDetailedDistance levenshteinDetailedDistance = new LevenshteinDetailedDistance();
        System.out.println(levenshteinDetailedDistance.apply("threshold", "hold"));
        LevenshteinResults levenshteinResults = levenshteinDetailedDistance.apply("threshold", "hold");
        System.out.println(levenshteinResults.getDeleteCount());

        System.out.println("---- Qgram ----");
        int qnumber = 2;
        QGram qGram = new QGram(qnumber);
        String str1 = "#salesal#";
        String str2 = "#alale#";
        int str1l = str1.length();
        int str2l = str2.length();
        double distance = qGram.distance(str1, str2);
        System.out.println(distance);
        System.out.println((str1l+str2l-2*(qnumber-1)-distance)/(str1l+str2l-2*(qnumber-1)));
        System.out.println(qGram.getProfile(str1));
        System.out.println(qGram.getProfile(str2));

        System.out.println("---- Metaphone ----");
        Metaphone metaphone = new Metaphone();
        metaphone.setMaxCodeLen(8);
        System.out.println(metaphone.encode("stardust"));
        System.out.println(metaphone.encode("moondust"));
    }
}

// mvn compile && mvn exec:java -Dexec.mainClass="eda.SoundexMain"