package pl.marwik.bank.controller;

import org.springframework.web.bind.annotation.*;
import pl.marwik.bank.model.oauth.RequireUserAuthenticate;
import pl.marwik.bank.model.request.LoginDTO;
import pl.marwik.bank.service.OAuthService;

@RestController
@RequestMapping("/oauth")
public class OAuthController {
    private OAuthService oAuthService;

    public OAuthController(OAuthService oAuthService) {
        this.oAuthService = oAuthService;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDTO loginDTO){
        return this.oAuthService.login(loginDTO);
    }

    @RequireUserAuthenticate
    @GetMapping("/logout")
    public void logout(@RequestHeader("tokenValue") String tokenValue){
        this.oAuthService.logout(tokenValue);
    }
}
