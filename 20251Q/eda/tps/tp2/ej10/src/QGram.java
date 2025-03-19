import java.util.HashMap;
import java.util.Map;

public class QGram {

    private int n;

    public QGram(int n) {
        this.n = n;
    }

    public void printTokens(String str) {
        Map<String, Integer> map = getStringMap(str);

        for (String subStr : map.keySet()) {
            System.out.println("%s: %s".formatted(subStr, map.get(subStr)));
        }
    }

    public float similarity(String str1, String str2) {

        Map<String, Integer> map1 = getStringMap(str1);
        Map<String, Integer> map2 = getStringMap(str2);




        return 0; // TODO
    }

    private Map<String, Integer> getStringMap(String s) {

        Map<String, Integer> toReturn = new HashMap<>();

        // add # padding
        StringBuilder str = new StringBuilder();
        str.append("#".repeat(n - 1)).append(s).append("#".repeat(n - 1));

        for(int i = 1; i < str.length() - n + 1; i++) {
            String subStr = str.subSequence(i, i + n - 1).toString();
            toReturn.merge(subStr, 1, (oldValue, newValue) -> oldValue + 1);
        }

        return toReturn;
    }


}
