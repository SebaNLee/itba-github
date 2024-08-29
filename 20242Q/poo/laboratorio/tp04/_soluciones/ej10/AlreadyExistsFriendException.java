package ar.edu.itba.poo.soltp4.ej10;

public class AlreadyExistsFriendException extends NumberException {

    private static final String MESSAGE = "Friend already exists";

    public AlreadyExistsFriendException(String number) {
        super(number, MESSAGE);
    }

}
