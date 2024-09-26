package ar.edu.itba.poo.soltp6.ej9;

import java.util.*;

public class TimeSetImpl<T> implements TimeSet<T> {

    private Set<TimeData<T>> data = new TreeSet<>(
            Comparator.<TimeData<T>>comparingInt(TimeData::getHours)
                    .thenComparingInt(TimeData::getMinutes));

    @Override
    public void add(T elem, int hours, int minutes) {
        TimeData<T> timeData = new TimeData<>(elem, hours, minutes);
        remove(timeData.getElem());
        data.add(timeData);
    }

    @Override
    public void remove(T elem) {
        Iterator<TimeData<T>> iterator = data.iterator();
        while(iterator.hasNext()) {
            if(iterator.next().getElem().equals(elem)) {
                iterator.remove();
                break;
            }
        }
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public boolean contains(T elem) {
        for (TimeData<T> datum : data) {
            if (datum.getElem().equals(elem)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Set<T> retrieve(int hoursFrom, int minutesFrom, int hoursTo, int minutesTo) {
        Set<T> response = new HashSet<>();
        for(TimeData<T> timeData : data) {
            if(timeData.getHours() > hoursTo) {
                // Optimización con colección ordenada cronológicamente
                break;
            }
            if(timeData.between(hoursFrom, minutesFrom, hoursTo, minutesTo)) {
                response.add(timeData.getElem());
            }
        }
        return response;
    }

}
