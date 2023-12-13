package gl.bci.ejercicio.exception;

public class UserAlreadyExistException extends Throwable{

    private String message;

    private Throwable cause;

    public UserAlreadyExistException(String message) {
        super(message);
    }

    public UserAlreadyExistException(String message, Throwable cause) {
        fillInStackTrace();
        this.message = message;
        this.cause = cause;
    }

}
