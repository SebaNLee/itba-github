public class InvalidIndexException extends Exception {

    private final static String MESSAGE = "ÍNDICE INVÁLIDO";

    public InvalidIndexException() {
        super(MESSAGE);
    }
}
