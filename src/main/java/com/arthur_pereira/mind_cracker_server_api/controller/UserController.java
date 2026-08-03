package com.arthur_pereira.mind_cracker_server_api.controller;

import com.arthur_pereira.mind_cracker_server_api.dto.UserCreationDTO;
import com.arthur_pereira.mind_cracker_server_api.dto.UserLoginDTO;
import com.arthur_pereira.mind_cracker_server_api.model.User;
import com.arthur_pereira.mind_cracker_server_api.service.TokenService;
import com.arthur_pereira.mind_cracker_server_api.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/registrar")
    public ResponseEntity<?> register(@RequestBody UserCreationDTO u, HttpServletResponse response) {
        String rawPassword = u.password();
        userService.createUser(u);
        var usernamePassword = new UsernamePasswordAuthenticationToken(u.email(), rawPassword);
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        response.addCookie(tokenService.generateCookie(token));

        return ResponseEntity.ok("User registered with success!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO userLoginDTO, HttpServletResponse response) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(userLoginDTO.email(), userLoginDTO.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        response.addCookie(tokenService.generateCookie(token));
        return ResponseEntity.ok("User logged with success!");
    }
}
