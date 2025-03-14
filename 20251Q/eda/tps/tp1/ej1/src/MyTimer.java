package eda.tps.tp1.ej1.src;

public class MyTimer {

    public long start;
    public long end;

    public MyTimer() {
        this.start = System.currentTimeMillis();
        this.end = 0;
    }

    public void end() {
        this.end = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        long exec = this.end - this.start;

        // no hago el formateo de tiempo
        //String toReturn = "(" + exec + ")" + days + hours + minutes + seconds;

        //return toReturn;

        return exec + "d";
    }
}
