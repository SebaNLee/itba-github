public class InvalidGradeException extends Exception {

    private final static String MESSAGE = "GRADO INVÁLIDO";

    public InvalidGradeException() {
        super(MESSAGE);
    }
}
