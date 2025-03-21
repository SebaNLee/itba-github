import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

        float notSharedTrigrams = 0;

        // me creo un set con solo los no repetidos de ambos
        Set<String> uniqueTrigrams = new HashSet<>(map1.keySet());
        uniqueTrigrams.addAll(map2.keySet());

        // recorro una única vez para contar la cantidad de trigramas que tienen con el conjunto de no repetidos
        int count1 = 0;
        int count2 = 0;
        for(String trigram : uniqueTrigrams) {
            int temp1 = map1.getOrDefault(trigram, 0);
            int temp2 = map2.getOrDefault(trigram, 0);
            count1 += temp1;
            count2 += temp2;
            notSharedTrigrams += Math.abs(temp1 - temp2);
        }

        return (count1 + count2 - notSharedTrigrams) / (count1 + count2);
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
