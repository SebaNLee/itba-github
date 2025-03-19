import java.util.HashMap;
import java.util.Map;

public class QGram {

    private int n;

    public QGram(int n) {
        this.n = n;
    }

    public void printTokens(String str) {

    }

    public float similarity(String str1, String str2) {

    }

    private Map<String, Integer> getStringMap(String str) {

        Map<String, Integer> toReturn = new HashMap<>();

        for(int i = 0; i < str.length() - n + 1; i++) {
            String subStr = str.subSequence(i, i + n - 1).toString();
            toReturn.merge(subStr, 1, (oldValue, newValue) -> oldValue + 1);
        }

        return toReturn;
    }


}
