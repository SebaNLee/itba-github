import java.util.HashMap;
import java.util.Map;

public class MainQGram {
    public static void main(String[] args) {
       QGram qGram = new QGram(2);
        System.out.println(qGram.similarityList("salesal","alale"));
        System.out.println(qGram.similarityMap("salesal","alale"));

    }
}

