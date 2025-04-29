public class ExactSearch {
    static int indexOf(char[] query, char[] target) {
        if (target.length < query.length)
            return -1;

        int currentTarget = 0;
        int currentQuery = 0;

        for (int i = 0; i < target.length; i++) {
            currentTarget = i;
            while (currentTarget< target.length && target[currentTarget] == query[currentQuery]) {
                if (currentQuery == query.length -1)
                    return currentTarget - currentQuery;
                currentQuery++;
                currentTarget++;
            }

            currentQuery = 0;
        }
        return -1;
    }
}
