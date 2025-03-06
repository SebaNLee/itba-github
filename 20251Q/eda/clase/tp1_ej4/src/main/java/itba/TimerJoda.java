package itba;

import org.joda.time.Instant;
import org.joda.time.Period;

public class TimerJoda {
    private Instant startTimeMillis;
    private Instant endTimeMillis;
    private boolean isRunning;

    public TimerJoda() { restart(); }

    public TimerJoda(long startTimestamp) { restart(startTimestamp); }

    public void restart(long startTimestamp) {
        startTimeMillis = new Instant(startTimestamp);
        isRunning = true;
    }

    public void restart() {
        startTimeMillis = Instant.now();
        isRunning = true;
    }

    public void stop(long stopTimestamp) {
        if (isRunning) {
            endTimeMillis = new Instant(stopTimestamp);
            isRunning = false;
        }
    }

    public void stop() {
        if (isRunning) {
            endTimeMillis = Instant.now();
            isRunning = false;
        }
    }

    public Period timestamp() {
        if (startTimeMillis.isAfter(endTimeMillis))
            throw new RuntimeException("This timer was stopped before it was started!");
        if (isRunning)
            throw new RuntimeException("Can't get timestamp of active timer");
        return new Period(startTimeMillis, endTimeMillis);
    }

    @Override
    public String toString() {

        Period p = timestamp();

        return String.format("(%dms) %d día%s %d hora%s %d min%s %d,%d s", p.toStandardDuration().getMillis(), p.getDays(), p.getDays() == 1 ? "" : "s", p.getHours(), p.getHours() == 1 ? "" : "s", p.getMinutes(), p.getMinutes() == 1 ? "" : "s", p.getSeconds(), p.getMillis());
        // (19239ms) 1 día 2 hs 0 min 23,040 s
    }
}



// !!
// CÓDIGO COPIADO DE DRIVE MARENGO