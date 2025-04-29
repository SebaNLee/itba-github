import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OpenHashingTest {
    private IndexParametricService<Integer, String> hash;

    @BeforeEach
    public void setup() {
        hash = new OpenHashing<>(f->f);
    }

    @Test
    public void test1() {
        hash.insertOrUpdate(1, "hola1");
        hash.insertOrUpdate(2, "hola2");
        hash.insertOrUpdate(3, "hola3");
        hash.insertOrUpdate(4, "hola4");
        hash.insertOrUpdate(5, "hola5");
        hash.remove(1);
        hash.remove(15);
        hash.dump();
    }

    @Test
    public void test2() {
        hash.insertOrUpdate(3, "dick");
        hash.insertOrUpdate(23, "joe");
        hash.insertOrUpdate(4, "sue");
        hash.insertOrUpdate(15, "meg");
        hash.remove(23);
        hash.remove(15);
        hash.insertOrUpdate(4, "sue2");
        hash.insertOrUpdate(43,"paul");
        hash.dump();
    }

    @Test
    public void test3() {
        hash.insertOrUpdate(1, "hola1");
        hash.insertOrUpdate(2, "hola2");
        hash.remove(1);
        hash.insertOrUpdate(3, "hola3");
        hash.remove(2);
        hash.insertOrUpdate(4, "hola4");
        hash.remove(3);
        hash.insertOrUpdate(5, "hola5");
        hash.remove(4);
        hash.insertOrUpdate(6, "hola6");
        hash.remove(5);
        hash.dump();
    }

    @Test
    public void test4() {
        hash.insertOrUpdate(5,"E");
        hash.insertOrUpdate(15,"E");
        hash.insertOrUpdate(25,"E");
        hash.insertOrUpdate(35,"E");
        hash.insertOrUpdate(45,"E");
        hash.insertOrUpdate(55,"E");
        hash.insertOrUpdate(65,"E");
        hash.insertOrUpdate(75,"E");
        hash.insertOrUpdate(85,"E");
        hash.insertOrUpdate(95,"E");
        hash.insertOrUpdate(105,"E");
        System.out.println(hash.size());
        hash.dump();
    }

    @Test
    public void test5() {
        hash.insertOrUpdate(0,"E");
        hash.insertOrUpdate(10,"E");
        hash.insertOrUpdate(20,"E");
        hash.insertOrUpdate(30,"E");
        hash.insertOrUpdate(40,"E");
        hash.insertOrUpdate(50,"E");
        hash.insertOrUpdate(60,"E");
        hash.remove(10);
        hash.remove(20);
        hash.remove(30);
        hash.insertOrUpdate(7,"E");
        hash.insertOrUpdate(8,"E");
        hash.insertOrUpdate(9,"E");
        hash.dump();
    }
}
