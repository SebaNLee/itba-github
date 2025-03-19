

public class LevenshteinTester {

    public static void main(String[] args) {
        
        System.out.println(Levenshtein.distance("big data", "bigdaa"));
        System.out.println(Levenshtein.normalizedSimilarity("big data", "bigdaa"));
    }
}
