package ar.edu.itba.poo.soltp6.ej9;

import java.util.Objects;

public class TimeData<T> {

    private T elem;
    private int hours;
    private int minutes;

    TimeData(T elem, int hours, int minutes) {
        this.elem = elem;
        this.hours = hours;
        this.minutes = minutes;
    }

    TimeData(T elem) {
        this.elem = elem;
    }

    public T getElem() {
        return elem;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof TimeData<?> timeData &&
                elem.equals(timeData.elem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getElem());
    }

    public boolean between(int hoursFrom, int minutesFrom, int hoursTo, int minutesTo) {
        return (hours > hoursFrom || hours == hoursFrom && minutes >= minutesFrom) &&
                (hours < hoursTo || hours == hoursTo && minutes <= minutesTo);
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

}
