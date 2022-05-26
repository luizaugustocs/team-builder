package br.com.luizaugustocs.teambuilder.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({NotFoundException.class})
    public final ResponseEntity<ErrorDescription> handleNotFoundException(NotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorDescription(HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler({BindException.class})
    public final ResponseEntity<ErrorDescription> handleBadRequestException(BindException ex) {

        List<String> violations = ex.getBindingResult().getFieldErrors()
                .stream()
                .sorted(Comparator.comparing(FieldError::getField))
                .map(fieldError -> String.format("Error while parsing field [%s]: %s", fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorDescription(HttpStatus.BAD_REQUEST, violations));
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public final ResponseEntity<ErrorDescription> handleBadRequestException(MethodArgumentTypeMismatchException ex) {

        var message = ex.getCause() instanceof IllegalArgumentException ?
                ex.getCause().getMessage() : ex.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorDescription(HttpStatus.BAD_REQUEST,message));
    }
}
