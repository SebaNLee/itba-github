import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class QueueTest {
    private QueueImp<Integer> queue;

    @BeforeEach
    void setup() {
        queue = new QueueImp<>(5);
    }

    @Test
    void test1() {
        Assertions.assertTrue(queue.isEmpty());
        Assertions.assertFalse(queue.isFull());
    }

    @Test
    void test2() {
        queue.queue(10);
        queue.queue(10);
        queue.queue(10);
        queue.queue(10);
        queue.queue(10);
        Assertions.assertTrue(queue.isFull());
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
        Assertions.assertFalse(queue.isFull());
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
        int i;
        for(i=1; i<2; i++)
            System.out.println("hola");
        System.out.println(i);
    }
}
