public class AlreadyExistsFriendException extends Exception {
    
    private static final String MESSAGE = "Error for number %s: Friend already exists";

    public AlreadyExistsFriendException(String number) {
        super(MESSAGE.formatted(number)); // !! al super le mando para que genere la excepción
    }
}
