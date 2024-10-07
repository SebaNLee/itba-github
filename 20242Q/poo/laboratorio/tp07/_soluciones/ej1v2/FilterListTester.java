package ar.edu.itba.poo.soltp7.ej1v2;

public class FilterListTester {

    public static void main(String[] args) {
        FilterList<Integer> list = new ArrayFilterList<>();
        list.add(4);
        list.add(3);
        list.add(8);
        System.out.println(list.filter(obj -> obj % 2 == 0));
    }

}
