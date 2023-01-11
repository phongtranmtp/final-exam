package phong.com.example.project3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import phong.com.example.project3.service.JwtTokenService;

@RestController
@RequestMapping("/api")
public class LoginAPI {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenService jwtTokenProvider;

    @PostMapping("/login")
    public String login(@RequestParam(value = "username") String username,
                        @RequestParam(value = "password") String password){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        return jwtTokenProvider.createToken(username);
    }
}
