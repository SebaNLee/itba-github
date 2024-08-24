package ar.edu.itba.poo.tp3.ej5;

public class B extends A {
    public void print(Number num) {
        System.out.println(MESSAGE.formatted("B","Number",num,num.getClass()));
    }
}
