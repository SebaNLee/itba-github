package eda.clase.tp1_ej9.src;

public class MyTimerV2 {

    public long start;
    public long end;

    public MyTimerV2() {
        this.start = System.currentTimeMillis();
        this.end = 0;
    }

    public MyTimerV2(long start) {
        this.start = start;
        this.end = 0;
    }

    public void end() {
        this.end = System.currentTimeMillis();
    }

    public void end(long end) {
        this.end = end;
    }

    public long getElapsedTime() {
        return this.end - this.start;
    }

    @Override
    public String toString() {
        long exec = this.end - this.start;

        // no hago el formateo de tiempo
        //String toReturn = String.format("(exec ms) %d día %d hs %d min %f s", );

        //return toReturn;

        return exec + "d";
    }
}
