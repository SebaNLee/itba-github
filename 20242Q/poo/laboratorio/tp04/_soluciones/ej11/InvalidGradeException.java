package ar.edu.itba.poo.soltp4.ej11;

public class InvalidGradeException extends Exception {

    private static final String MESSAGE = "Grado Inválido";

    public InvalidGradeException() {
        super(MESSAGE);
    }

}
