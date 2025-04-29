import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CircularListTest {
    private CircularList<Integer> queue;

    @BeforeEach
    void setup() {
        queue = new CircularList<>();
    }

    @Test
    void test1() {
        Assertions.assertTrue(queue.isEmpty());
    }

    @Test
    void test2() {
        queue.queue(10);
        queue.queue(10);
        queue.queue(10);
        queue.queue(10);
        queue.queue(10);
        Assertions.assertFalse(queue.isEmpty());
    }

    @Test
    void test3() {
        queue.queue(10);
        Assertions.assertEquals(10, queue.dequeue());
        Assertions.assertThrows(RuntimeException.class, ()->queue.dequeue());
        queue.queue(10);
        queue.queue(10);
        queue.queue(10);
        Assertions.assertFalse(queue.isEmpty());
        Assertions.assertEquals(3, queue.size());
    }

    @Test
    void test4() {
        for(int i=0; i<4; i++)
            queue.queue(10);
        for(int i=0; i<2; i++)
            queue.dequeue();
        for(int i=0; i<3; i++)
            queue.queue(10);
        for(int i=0; i<2; i++)
            queue.dequeue();
        Assertions.assertEquals(3, queue.size());
    }

    @Test
    void test5() {
        queue.queue(1);
        queue.queue(2);
        queue.queue(3);
        queue.dequeue();
        queue.queue(4);
        queue.queue(5);
        queue.dequeue();
        queue.dump();
    }
}
