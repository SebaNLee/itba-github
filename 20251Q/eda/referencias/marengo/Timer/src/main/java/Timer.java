public class Timer {
    private final long MILLIS_PER_SECOND = 1000;
    private final long MILLIS_PER_MINUTE = MILLIS_PER_SECOND * 60;
    private final long MILLIS_PER_HOUR = MILLIS_PER_MINUTE * 60;
    private final long MILLIS_PER_DAY = MILLIS_PER_HOUR * 24;

    private long startTimeMillis;
    private long endTimeMillis;
    private boolean isRunning;

    public Timer() { restart(); }

    public Timer(long startTimestamp) { restart(startTimestamp); }

    public void restart(long startTimestamp) {
        startTimeMillis = startTimestamp;
        isRunning = true;
    }

    public void restart() { restart(System.currentTimeMillis()); }

    public void stop(long stopTimestamp) {
        if (isRunning) {
            endTimeMillis = stopTimestamp;
            isRunning = false;
        }
        else
            throw new RuntimeException("The timer is already stopped");
    }

    public void stop() { stop(System.currentTimeMillis()); }

    public long timestamp() {
        if (endTimeMillis < startTimeMillis)
            throw new RuntimeException("This timer was stopped before it was started!");
        if (isRunning)
            throw new RuntimeException("Can't get timestamp of active timer");
        return endTimeMillis - startTimeMillis;
    }

    public long getElapsedTime() {
        return timestamp();
    }

    @Override
    public String toString() {
        long millisDelta = timestamp();

        long millis = millisDelta;
        long days = millis / MILLIS_PER_DAY;
        millis -= days * MILLIS_PER_DAY;
        long hours = millis / MILLIS_PER_HOUR;
        millis -= hours * MILLIS_PER_HOUR;
        long minutes = millis / MILLIS_PER_MINUTE;
        millis -= minutes * MILLIS_PER_MINUTE;

        return String.format("(%dms) %d día%s %d hora%s %d min%s %.3f s", millisDelta, days, days == 1 ? "" : "s", hours, hours == 1 ? "" : "s", minutes, minutes == 1 ? "" : "s", millis / 1000.0);
        // (19239ms) 1 día 2 hs 0 min 23,040 s
    }
}