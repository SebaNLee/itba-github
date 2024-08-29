package ar.edu.itba.poo.soltp4.ej10;

public class FriendNotFoundException extends NumberException {

    private static final String MESSAGE = "Friend not found";

    public FriendNotFoundException(String number) {
        super(number, MESSAGE);
    }

}
