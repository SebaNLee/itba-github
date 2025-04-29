import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QGram {
    private final int qnumber;

    public QGram(int qnumber){
        if(qnumber <= 0)
            throw new IllegalArgumentException("Q debe ser positivo");
        this.qnumber = qnumber;
    }

    public void printTokens(String str) {
        for(Map.Entry<String, Integer> entry : getTokensInMap(str).entrySet()) {
            System.out.format("%s - %d\n",entry.getKey(), entry.getValue());
        }
    }

    private Map<String, Integer> getTokensInMap(String str) {
        StringBuilder s = new StringBuilder();
        s.append("#".repeat(qnumber-1)).append(str).append("#".repeat(qnumber-1));
        Map<String, Integer> map = new HashMap<>();
        for(int i=qnumber; i<=s.length(); i++){
            String qGram = s.subSequence(i-qnumber,i).toString();
            map.merge(qGram,1,(oldValue, newValue) -> oldValue + 1);
        }
        return map;
    }

    private List<String> getTokensInList(String str)  {
        StringBuilder s = new StringBuilder();
        s.append("#".repeat(qnumber-1)).append(str).append("#".repeat(qnumber-1));
        List<String> list = new ArrayList<>();
        for(int i=qnumber; i<=s.length(); i++){
            String qGram = s.subSequence(i-qnumber,i).toString();
            list.add(qGram);
        }
        return list;
    }

    public double similarityList(String str1, String str2) {
        List<String> list1 = getTokensInList(str1);
        List<String> list2 = getTokensInList(str2);
        int sumLength = list1.size() + list2.size();
        int removed = 0;

        for(String qGram : list1) {
            if(list2.remove(qGram))
                removed++;
        }
        System.out.println(removed);
        System.out.println(sumLength);
        return (double)2*removed/(sumLength);
    }

    public double similarityMap(String str1, String str2) {
        Map<String, Integer> map1 = getTokensInMap(str1);
        Map<String, Integer> map2 = getTokensInMap(str2);
        int removed = 0;

        for(Map.Entry<String, Integer> entry1 : map1.entrySet()) {
            do {
                if (map2.getOrDefault(entry1.getKey(), 0) != 0) {
                    map2.merge(entry1.getKey(), 1, (oldValue, newValue) -> oldValue - 1);
                    removed++;
                    System.out.println(entry1.getKey());
                }
                entry1.setValue(entry1.getValue()-1);
            }while(entry1.getValue()!=0);
        }

        return (double)2*removed/(str1.length()+2*(qnumber-1)+str2.length());
    }
}