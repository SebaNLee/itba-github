import java.time.Duration;
import java.time.Instant;

public class Timer {
    private Duration duration;
    private final long start;
    private boolean isRunning;

    public Timer(){
        this(Instant.now().toEpochMilli());
    }

    public Timer(long start) {
        this.start = start;
        isRunning = true;
    }

    public void stop(){
        stop(Instant.now().toEpochMilli());
    }

    public void stop(long end) {
        if(!isRunning)
            throw new RuntimeException("The timer is already stopped");
        if(end < start)
            throw new RuntimeException("the timer stop occurred before it started");

        duration = Duration.ofMillis(end-start);
        isRunning = false;
    }

    @Override
    public String toString() {
        if(isRunning)
            throw new RuntimeException("The timer is not stopped");

        long days = duration.toDaysPart();
        long hours = duration.toHoursPart();
        long minutes = duration.toMinutesPart();
        double seconds = (duration.toMillis()/1000.0)%60;
        return String.format("(%dms) %d día%s %d hora%s %d min%s %.3f s", duration.toMillis(), days, days == 1 ? "" : "s", hours, hours == 1 ? "" : "s", minutes, minutes == 1 ? "" : "s", seconds);
    }
}
