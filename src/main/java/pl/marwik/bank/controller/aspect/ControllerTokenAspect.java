package pl.marwik.bank.controller.aspect;

import org.apache.commons.io.IOUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pl.marwik.bank.service.OAuthService;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
public class ControllerTokenAspect {
    private OAuthService oAuthService;

//    @Autowired
    public ControllerTokenAspect(OAuthService oAuthService) {
        this.oAuthService = oAuthService;
    }

    @Before("execution(* pl.marwik.bank.controller.AccountController.*(..))")
    public void logBeforeAllMethods(JoinPoint joinPoint) {
        System.out.println("*****LoggingAspect.logBeforeAllMethods(): " + joinPoint.getSignature().getName());
        String token = (String)joinPoint.getArgs()[0];
        System.out.println("Token to: " + token);

        System.out.println(Arrays.toString(joinPoint.getArgs()));
        System.out.println(joinPoint.getSignature());
        System.out.println(joinPoint.getKind());
        System.out.println(joinPoint.getSourceLocation());
        System.out.println(joinPoint.getStaticPart());
        System.out.println(joinPoint.getTarget());
        System.out.println(joinPoint.getThis());

        oAuthService.throwIfTokenIsInvalid(token);
    }

    @Before("@annotation(pl.marwik.bank.controller.aspect.SpecialRole)")
    public void myAdvice() throws IOException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("tokenValue");
        System.out.println("Tutaj jest wymagana specjalna rola");
        System.out.println("Token: " + token);
    }
}
