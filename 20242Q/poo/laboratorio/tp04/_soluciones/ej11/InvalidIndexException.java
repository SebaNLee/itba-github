package ar.edu.itba.poo.soltp4.ej11;

public class InvalidIndexException extends Exception {

    private static final String MESSAGE = "Índice Inválido";

    public InvalidIndexException() {
        super(MESSAGE);
    }

}

