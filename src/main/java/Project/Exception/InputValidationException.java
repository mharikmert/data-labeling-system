package Project.Exception;

public class InputValidationException extends Exception {

    public InputValidationException() {

    }

    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public void InputValidationExceptionMessage() {

        printStackTrace(System.err);
        System.err.println("Maximum number of labels can not bigger than label number");
        System.exit(1);

    }


}
