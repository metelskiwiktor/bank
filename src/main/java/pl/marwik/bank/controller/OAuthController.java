package pl.marwik.bank.controller;

import org.springframework.web.bind.annotation.*;
import pl.marwik.bank.model.oauth.RequireUserAuthenticate;
import pl.marwik.bank.model.request.login.CredentialsDTO;
import pl.marwik.bank.model.request.login.CreditCardDTO;
import pl.marwik.bank.model.request.login.IdCardDTO;
import pl.marwik.bank.service.OAuthService;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4201", "http://localhost:4202"})
@RestController
@RequestMapping("/oauth")
public class OAuthController {
    private OAuthService oAuthService;

    public OAuthController(OAuthService oAuthService) {
        this.oAuthService = oAuthService;
    }

    @PostMapping("/login")
    public String login(@RequestBody CredentialsDTO credentialsDTO){
        return this.oAuthService.login(credentialsDTO);
    }

    @PostMapping("/login/id")
    public String login(@RequestBody IdCardDTO idCardDTO){
        return this.oAuthService.login(idCardDTO);
    }

    @PostMapping("/login/credit-card")
    public String login(@RequestBody CreditCardDTO creditCardDTO, HttpServletRequest request){
        return this.oAuthService.login(creditCardDTO);
    }

    @RequireUserAuthenticate
    @GetMapping("/logout")
    public void logout(@RequestHeader("tokenValue") String tokenValue){
        this.oAuthService.logout(tokenValue);
    }
}