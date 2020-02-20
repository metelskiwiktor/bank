package pl.marwik.bank.controller.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.marwik.bank.exception.BankException;
import pl.marwik.bank.service.OAuthService;

import java.util.Arrays;

@Aspect
@Component
@ControllerAdvice
public class ControllerTokenAspect extends ResponseEntityExceptionHandler {
    private OAuthService oAuthService;

    @Autowired
    public ControllerTokenAspect(OAuthService oAuthService) {
        this.oAuthService = oAuthService;
    }

//    @Before("execution(* pl.marwik.bank.controller.AccountController.*(..))")
//    public void logBeforeAllMethods(JoinPoint joinPoint) throws Exception {
//        System.out.println("*****LoggingAspect.logBeforeAllMethods(): " + joinPoint.getSignature().getName());
//        String token = (String)joinPoint.getArgs()[0];
//        System.out.println("Token to: " + token);
//
//        oAuthService.throwIfTokenIsInvalid(token);
//    }

    @ExceptionHandler(value = BankException.class)
    protected ResponseEntity<Object> handleConflict(BankException ex, WebRequest request){
        System.out.println("Exception wleciał");
        String bodyOfResponse = ex.getMessage();
        System.out.println(bodyOfResponse);
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}
