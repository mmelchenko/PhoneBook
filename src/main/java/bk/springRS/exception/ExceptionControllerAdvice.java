package bk.springRS.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> badRequestException(BadRequestException ex) {
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode(HttpStatus.BAD_REQUEST.value());
        error.setMessage("Bad request. Please, verify populated data.");
        ex.printStackTrace();
        return new ResponseEntity<>(error, HttpStatus.OK);
    }

    @ExceptionHandler(ContactNotFoundException.class)
    public ResponseEntity<ErrorResponse> contactNotFoundExceptionHandler(ContactNotFoundException ex) {
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode(HttpStatus.NO_CONTENT.value());
        error.setMessage("Contact not found.");
        ex.printStackTrace();
        return new ResponseEntity<>(error, HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setMessage("Please contact your administrator");
        ex.printStackTrace();
        return new ResponseEntity<>(error, HttpStatus.OK);
    }
}
