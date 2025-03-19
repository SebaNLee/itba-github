import java.util.HashMap;
import java.util.Map;

public class Soundex {

    private static Map<Character, Character> map = new HashMap<>();

    public Soundex() {
        map.put('A','0'); map.put('E','0'); map.put('I','0'); map.put('O','0'); map.put('U','0');
        map.put('Y','0'); map.put('W','0'); map.put('H','0'); map.put('B','1'); map.put('F','1');
        map.put('P','1'); map.put('V','1'); map.put('C','2'); map.put('G','2'); map.put('J','2');
        map.put('K','2'); map.put('Q','2'); map.put('S','2'); map.put('X','2'); map.put('Z','2');
        map.put('D','3'); map.put('T','3'); map.put('L','4'); map.put('M','5'); map.put('N','5');
        map.put('R','6');
    }

    public String representation(String word) {
        char[] in;
        char[] out = {'0', '0', '0', '0'};

        in = word.toUpperCase().toCharArray();

        out[0] = in[0];
        int count = 1;
        char last = map.get(in[0]);

        for (int i = 1; i < in.length && count < 4; i++) {
            char iterValue = map.getOrDefault(in[i], '0');

            if (iterValue != '0' && iterValue != last) {
                out[count] = iterValue;
                count++;
                last = iterValue;
            }
        }

        return new String(out);
    }

    public double similitude(String word1, String word2) {

        double simil = 0;
        String encodeWord1 = representation(word1);
        String encodeWord2 = representation(word2);

        for(int i=0; i<4; i++) {
            if(encodeWord1.charAt(i) == encodeWord2.charAt(i))
                simil += 0.25;
        }
        return simil;
    }


}
