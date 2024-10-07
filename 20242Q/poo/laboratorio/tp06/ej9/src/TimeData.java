public class TimeData implements Comparable<TimeData> {

    private int hours;
    private int minutes;

    TimeData(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }

    @Override
    public int compareTo(TimeData comp) {
        
        int temp = this.hours - comp.hours;

        if(temp == 0) {
            temp = this.minutes - comp.minutes;
        }

        return temp;
    }


}
