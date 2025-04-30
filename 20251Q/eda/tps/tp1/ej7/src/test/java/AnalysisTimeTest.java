import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.*;

import eda.TimerJoda;

public class AnalysisTimeTest {

    @Test
    public void basicTest() {
        TimerJoda t = new TimerJoda(0);
        t.stop(3);
        Assertions.assertEquals(3, t.getElapsedTime());
    }

    @Test
    public void getDurationTest() {
        TimerJoda t = new TimerJoda(0);
        long expected = t.getElapsedTime();
        assertEquals(expected, expected);
    }

    @Test
    public void getStopExceptionTest() {
        TimerJoda t = new TimerJoda(10);
        assertThrows(RuntimeException.class, () -> t.stop(-100));
    }
}
