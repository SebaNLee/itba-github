import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.*;

import eda.TimerJoda;

public class Tester {

    @Test
    void basicTest() {
        Assertions.assertEquals(3, new TimerJoda());
    }

    @Test
    void getDurationTest() {
        TimerJoda t = new TimerJoda(0);
        t.stop(0 + 63);
        assertEquals(63, t.getElapsedTime());
    }

    @Test
    void getStopExceptionTest() {
        TimerJoda t = new TimerJoda(10);
        assertThrows(RuntimeException.class, () -> t.stop(10 - 3));
    }
}
