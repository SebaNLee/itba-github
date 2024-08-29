package ar.edu.itba.poo.soltp4.ej10;

public class NumberException extends Exception {

    private static final String MESSAGE = "Error for number %s: ";

    public NumberException(String number, String description) {
        super(MESSAGE.formatted(number) + description);
    }

}
