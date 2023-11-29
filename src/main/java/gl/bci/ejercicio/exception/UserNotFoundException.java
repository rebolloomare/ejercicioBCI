package gl.bci.ejercicio.exception;

public class UserNotFoundException extends Throwable{

    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(Throwable cause) {
        super(cause);
    }

}
