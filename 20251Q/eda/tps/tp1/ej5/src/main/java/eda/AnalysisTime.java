package eda;

import eda.TimerJoda;

public class AnalysisTime {

    public static void main(String[] args) {
        TimerJoda myCrono = new TimerJoda(10);
        myCrono.stop(10 + 93623040);

        System.out.println(myCrono);
    }
}