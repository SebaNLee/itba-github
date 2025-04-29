public class MainTimer {
    public static void main(String[] args) {
        Timer timer = new Timer(100);
        timer.stop(400);
        timer.stop(500);
        System.out.println(timer);
    }
}
