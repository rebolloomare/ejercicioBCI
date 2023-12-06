package gl.bci.ejercicio.exception;

import gl.bci.ejercicio.model.response.ErrorDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<ErrorDetails> handleRuntimeException(RuntimeException ex,
                                                                 WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDetails> handleAllException(Exception ex,
                                                                 WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<ErrorDetails> handleUserNotFoundException(UserNotFoundException ex,
                                                                          WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ErrorDetails> handleUserAlreadyExistException(Exception ex,
                                                                  WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                errors.toString());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

}