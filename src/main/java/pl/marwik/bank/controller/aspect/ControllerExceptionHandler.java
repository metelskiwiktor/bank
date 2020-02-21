package pl.marwik.bank.controller.aspect;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.marwik.bank.exception.BankException;

//@RestControllerAdvice
//public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
public class ControllerExceptionHandler{

//    @ExceptionHandler(value = BankException.class)
//    protected ResponseEntity<Object> handleConflict(BankException ex, WebRequest request) {
//        System.out.println("Exception wleciał");
//        String bodyOfResponse = ex.getMessage();
//        System.out.println(bodyOfResponse);
//        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
//    }
}
