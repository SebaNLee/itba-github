package ar.edu.itba.poo.tp6.ej1;

import java.util.ArrayList;
import java.util.List;

public class Ej1 {

    public static void main(String[] args) {
        List<Integer> list1 = new ArrayList();
        List<Integer> list2 = new ArrayList<Integer>();
        List<Number> list3 = new ArrayList<Integer>();
        List list4 = new ArrayList<Integer>();
        List<? extends Number> list5 = new ArrayList<? extends Number>();
        List<?> list6 = new ArrayList();
    }

}
