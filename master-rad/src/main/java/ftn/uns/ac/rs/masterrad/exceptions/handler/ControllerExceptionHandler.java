package ftn.uns.ac.rs.masterrad.exceptions.handler;

import java.util.Date;

import ftn.uns.ac.rs.masterrad.controller.LogIndexerController;
import ftn.uns.ac.rs.masterrad.controller.RegistrationController;
import ftn.uns.ac.rs.masterrad.exceptions.ApplicationNameUniquenessException;
import ftn.uns.ac.rs.masterrad.exceptions.InvalidCredentialsException;
import ftn.uns.ac.rs.masterrad.exceptions.NoSuchApplicationException;
import ftn.uns.ac.rs.masterrad.utils.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(basePackageClasses = {LogIndexerController.class, RegistrationController.class})
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoSuchApplicationException.class)
    public ResponseEntity<Object> noSuchApplicationException(final NoSuchApplicationException ex) {
        final var errorResponse = new ErrorResponse(new Date(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ApplicationNameUniquenessException.class)
    public ResponseEntity<Object> applicationNameUniquenessException(final ApplicationNameUniquenessException ex) {
        final var errorResponse = new ErrorResponse(new Date(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Object> invalidCredentialsException(final InvalidCredentialsException ex) {
        final var errorResponse = new ErrorResponse(new Date(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
        final NoHandlerFoundException ex,
        final HttpHeaders headers, final HttpStatus status,
        final WebRequest request
    ) {
        final var errorResponse = new ErrorResponse(new Date(), "Unexpected error occurred");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
