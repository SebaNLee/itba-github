import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.bag.HashBag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BagTest {
    private Bag<String> bag;

    @BeforeEach
    void setup() {
        bag = new HashBag<>();
    }

    @Test
    void test1() {
        Assertions.assertTrue(bag.add("hola"));
        Assertions.assertFalse(bag.add("hola"));
        Assertions.assertEquals(2,bag.getCount("hola"));
        Assertions.assertTrue(bag.remove("hola"));
        Assertions.assertEquals(0,bag.getCount("hola"));
    }
}
