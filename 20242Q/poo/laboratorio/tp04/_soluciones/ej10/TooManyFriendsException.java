package ar.edu.itba.poo.soltp4.ej10;

public class TooManyFriendsException extends NumberException {

    private static final String MESSAGE = "Too many friends";

    public TooManyFriendsException(String number) {
        super(number, MESSAGE);
    }

}
