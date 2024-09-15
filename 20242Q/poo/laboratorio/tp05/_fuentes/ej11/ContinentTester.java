package ar.edu.itba.poo.tp5.ej11;

import java.util.Arrays;

public class ContinentTester {

    public static void main(String[] args) {
        Continent[] continents = new Continent[]{
                Continent.OCEANIA,
                Continent.AMERICA,
                Continent.EUROPE};
        Arrays.sort(continents);
        for(Continent continent : continents) {
            System.out.println(continent);
        }
    }

}
