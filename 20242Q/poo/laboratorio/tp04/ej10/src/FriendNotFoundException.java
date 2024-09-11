public class FriendNotFoundException extends Exception {

    private static final String MESSAGE = "Error for number %s: Friend not found";

    public FriendNotFoundException(String number) {
        super(MESSAGE.formatted(number)); // !! al super le mando para que genere la excepción
    }
}
