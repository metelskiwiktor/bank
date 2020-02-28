package pl.marwik.bank.controller.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pl.marwik.bank.service.OAuthService;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Aspect
@Component
public class ControllerTokenAspect {
    private OAuthService oAuthService;
    private HttpServletRequest request;

    public ControllerTokenAspect(OAuthService oAuthService) {
        this.oAuthService = oAuthService;
    }

    @Before("@annotation(pl.marwik.bank.model.oauth.RequireUserAuthenticate)")
    public void requireUserAuthenticate() {
        initializeServletRequest();
        String ipAddress = request.getRemoteAddr();
        String token = request.getHeader("tokenValue");
        oAuthService.throwIfTokenIsInvalid(token, ipAddress);
    }

    @Before("@annotation(pl.marwik.bank.model.oauth.RequireAdminAuthenticate)")
    public void requireAdminAuthenticate() {
        initializeServletRequest();
        String ipAddress = request.getRemoteAddr();
        String token = request.getHeader("tokenValue");
        oAuthService.throwIfTokenIsNotAdmin(token, ipAddress);
    }

    private void initializeServletRequest(){
        request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }
}
