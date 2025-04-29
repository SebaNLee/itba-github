import org.junit.jupiter.api.*;

public class TimerTest {

    private Timer timer;

    @BeforeAll
    static void initAll() {
        System.out.println("Empiezan los tests");
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("Terminaron todos los tests");
    }

    @BeforeEach
    void setup() {
        timer = new Timer(100);
    }

    @Test
    void stringTest() {
        timer.stop(400);
        Assertions.assertTrue(timer.toString().contains("(300ms) 0 días 0 horas 0 mins 0.300 s"));
    }

/*    @Test
            void
    assertThrows( IllegalArgumentException.class, ()->{new MyTimer(-10);});*/
}