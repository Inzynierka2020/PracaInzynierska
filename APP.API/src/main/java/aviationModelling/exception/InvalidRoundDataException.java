package aviationModelling.exception;

public class InvalidRoundDataException extends RuntimeException {

    public InvalidRoundDataException(String message) {
        super(message);
    }

    public InvalidRoundDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidRoundDataException(Throwable cause) {
        super(cause);
    }
}
