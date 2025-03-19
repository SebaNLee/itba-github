public class ExactSearch {


    public static int indexOf(char[] query, char[] target) {

        for(int i = 0; i <= target.length - query.length; i++) {

            boolean match = true;

            for(int j = 0; j < query.length; j++) {
                
                if(target[i + j] != query[j]) {
                    match = false;
                    break;
                }
            }

            if (match) {
                return i;
            }
        }

        return -1;
    }
}
