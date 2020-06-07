package com.ada.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@SpringBootApplication
@EnableResourceServer
@EnableAuthorizationServer
public class Oauth2ClientCredentialApplication {

    public static void main(String[] args) {
        SpringApplication.run(Oauth2ClientCredentialApplication.class, args);
    }

    @RequestMapping("/validateUser")
    public Principal user(Principal user) {
        return user;
    }

    @GetMapping("/test")
    public String test() {
        return "Hello World";
    }
}
