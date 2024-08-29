package ar.edu.itba.poo.soltp4.ej8;

public class IntervalTester {

    public static void main(String[] args) {
        Interval interval = new Interval(-1, 1, 0.5);
        System.out.println(interval);
        System.out.println(interval.size());
        System.out.println(interval.at(3));
        System.out.println(interval.indexOf(0.5));
        System.out.println(interval.includes(0.5));
        System.out.println(interval.equals(new Interval(-1, 1, 0.5)));
        System.out.println(interval.hashCode() == new Interval(-1, 1, 0.5).hashCode());
        System.out.println(interval.count(new IntervalCondition() {
            @Override
            public boolean satisfies(double value) {
                return value > 0;
            }
        }));
    }

}
