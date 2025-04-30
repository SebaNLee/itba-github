package lee;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Set;

public class Laberinto {

    public static boolean existeCamino(int[][] laberinto, int filaInicio, int columnaInicio, int filaFin, int columnaFin) {
        
        // lo hago recursivo

        int[][] possibleMoves = {
            {filaInicio, columnaInicio - 1}, // izq
            {filaInicio - 1, columnaInicio}, // arriba
            {filaInicio, columnaInicio + 1}, // der
            {filaInicio + 1, columnaInicio} // abajo
        };

        // marco la posición actual como visitada
        laberinto[filaInicio][columnaInicio] = -1;

        boolean flagFound = false;

        // si no hay movimientos disponibles, entonces retorno false
        for(int i = 0; i < 4 && flagFound == false; i++) {

            int fil = possibleMoves[i][0]; 
            int col = possibleMoves[i][1];

            // chequeo si los índices son válidos
            if (fil >= 0 && fil < laberinto.length && col >= 0 && col < laberinto[0].length && laberinto[fil][col] == 0) {

                // caso base, encontré camino
                if (fil == filaFin && col == columnaFin) {
                    return true;
                }
                // si hay camino disponible, entonces llamada recursiva
                else if (laberinto[fil][col] == 0) {
                    boolean returned = existeCamino(laberinto, fil, col, filaFin, columnaFin);
                
                    if (returned == true) {
                        flagFound = true;
                    }
                }
            } 
        }

        return flagFound;
    }

    public static void main(String[] args) {
        int[][] laberinto = {
                {0, 0, 1, 0},
                {1, 0, 1, 0},
                {0, 0, 0, 0},
                {0, 1, 1, 1}
        };

        boolean existe = existeCamino(laberinto, 0, 0, 3, 0);
        if (existe) {
            System.out.println("Existe un camino en el laberinto.");
        } else {
            System.out.println("No existe un camino en el laberinto.");
        }
        System.out.println("Caminos recorridos:");
        imprimirLaberinto(laberinto);

        int[][] laberintoSinSalida = {
                {0, 0, 1, 0},
                {1, 0, 1, 1},
                {0, 0, 0, 0},
                {0, 1, 1, 1}
        };
        boolean existeSinSalida = existeCamino(laberintoSinSalida, 0, 0, 0, 3);
        if (existeSinSalida) {
            System.out.println("Existe un camino en el laberinto sin salida (¡error!).");
        } else {
            System.out.println("No existe un camino en el laberinto sin salida.");
        }
        System.out.println("Caminos recorridos:");
        imprimirLaberinto(laberintoSinSalida);
    }

    public static void imprimirLaberinto(int[][] laberinto) {
        for (int[] fila : laberinto) {
            for (int celda : fila) {
                System.out.print(celda + " ");
            }
            System.out.println();
        }
    }
}


// mvn compile && mvn exec:java -Dexec.mainClass="lee.Laberinto"