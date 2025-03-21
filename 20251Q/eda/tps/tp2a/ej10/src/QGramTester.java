

public class QGramTester {

    public static void main(String[] args) {
        QGram qgram = new QGram(3);

        qgram.printTokens("alal");
        System.out.println(qgram.similarity("salesal", "vale"));
    }
}
