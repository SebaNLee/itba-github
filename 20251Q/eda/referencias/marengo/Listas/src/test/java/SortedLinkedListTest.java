import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

public class SortedLinkedListTest {
    private SortedListService<String> l;

    @BeforeEach
    public void setup() {
        l = new SortedLinkedList<>();
    }

    @Test
    public void test1() {
        Assertions.assertTrue(l.insert("casa"));
        Assertions.assertFalse(l.insert("casa"));
        Assertions.assertTrue(l.insert("hola"));
        Assertions.assertTrue(l.insert("casaa"));
        Assertions.assertTrue(l.insert("casaaa"));
        Assertions.assertTrue(l.insert("casaaaa"));
        Assertions.assertTrue(l.insert("zzz"));
        for(String s : l) {
            System.out.println(s);
        }
        Assertions.assertEquals("zzz", l.getMax());
    }

    @Test
    public void TestEmpty1() {
        Assertions.assertTrue(l.isEmpty());
    }

    @Test
    public void TestEmpty2() {
        l.insert("bio");
        l.insert("tito");
        l.insert("hola");
        l.insert("aca");
        Assertions.assertFalse(l.isEmpty());
    }

    @Test
    public void TestInsert() {
        Assertions.assertTrue(l.insert("tal"));
        Assertions.assertTrue(l.insert("ah"));
        Assertions.assertTrue(l.insert("hola"));
        Assertions.assertTrue(l.insert("veo"));
        Assertions.assertTrue(l.insert("bio"));
        Assertions.assertTrue(l.insert("tito"));
        Assertions.assertFalse(l.insert("hola"));
    }

    @Test
    public void TestSize() {
        l.insert("bio");
        l.insert("tito");
        l.insert("hola");
        l.insert("aca");
        Assertions.assertEquals(4, l.size());
    }

    @Test
    public void TestGetMax() {
        l.insert("bio");
        l.insert("tito");
        l.insert("veo");
        l.insert("hola");
        l.insert("aca");
        Assertions.assertEquals("veo", l.getMax());
    }

    @Test
    public void TestGetMin() {
        l.insert("bio");
        l.insert("tito");
        l.insert("veo");
        l.insert("hola");
        l.insert("aca");
        Assertions.assertEquals("aca", l.getMin());
    }

    @Test
    public void testRemove() {
        l.insert("aaa");
        l.insert("bbb");
        l.insert("ccc");
        l.remove("ccc");
        Assertions.assertFalse(l.remove("ccc"));

        for(String s : l) {
            System.out.println(s);
        }
    }

    @Test
    public void testRemoveIterator() {
        l.insert("aca");
        l.insert("ah");
        l.insert("bio");
        l.insert("hola");
        l.insert("tal");
        l.insert("tito");
        l.insert("veo");
        Iterator<String> iter = l.iterator();
        int rec= 1;
        while(iter.hasNext()) {
            if (rec++ >= 4)
                break;
            String v= iter.next();
            if (v.equals("ah") || v.equals("hola") )
                iter.remove();
        }
        for(String s : l) {
            System.out.println(s);
        }
    }

    @Test
    public void testRemoveIterator2() {
        l.insert("aca");
        Iterator<String> iter = l.iterator();
        Assertions.assertThrows(IllegalStateException.class, iter::remove);
        iter.next(); iter.remove();
        for(String s : l) {
            System.out.println(s);
        }
    }
}
