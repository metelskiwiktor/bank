package pl.marwik.bank.controller.aspect;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.marwik.bank.exception.BankException;
import pl.marwik.bank.exception.ResponseException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = BankException.class)
    public ResponseEntity<ResponseException> handleConflict(HttpServletRequest request, BankException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(new ResponseException(ex.getCode().getDetailsPattern(), ex.getCode().getCode(), LocalDateTime.now().toString()), HttpStatus.valueOf(ex.getCode().getHttpStatus()));
    }

    @ExceptionHandler
    public ResponseEntity<ResponseException> handleConflict(Exception ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(new ResponseException(ex.getMessage(), null, LocalDateTime.now().toString()), HttpStatus.CONFLICT);
    }
}
