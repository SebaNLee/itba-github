import java.sql.Date;

public class Main {

    public static void main(String[] args) {
        Affiliate af1 = new Affiliate("hola", "como", Date.valueOf("1111-11-11"));

        af1.printAll();
    }
}
