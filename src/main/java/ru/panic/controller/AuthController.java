package ru.panic.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.panic.entity.User;
import ru.panic.exception.InvalidCredentialsException;
import ru.panic.mappers.UserRepository;
import ru.panic.security.JwtDecoder;
import ru.panic.security.JwtUtil;

import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
public class AuthController {

    public AuthController(PasswordEncoder passwordEncoder, JwtUtil jwtUtil, JwtDecoder jwtDecoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.jwtDecoder = jwtDecoder;
        this.userRepository = userRepository;
    }

    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final JwtDecoder jwtDecoder;
    private final UserRepository userRepository;

    @GetMapping("/auth/check")
    public HashMap<String, Object> getInfoByJwt(
            @RequestParam(name = "jwtToken") String jwtToken
    ){
        log.debug("AuthController.getInfoByJwt() called with jwtToken: {}", jwtToken);

        if (jwtDecoder.isJwtValid(jwtToken) && !jwtDecoder.isJwtExpired(jwtToken)){
            User user = userRepository.findByUsername(jwtUtil.extractUsername(jwtToken));
            HashMap<String, Object> response = new HashMap<>();
            response.put("status", 200);
            response.put("username", user.getUsername());
            log.debug("AuthController.getInfoByJwt() response: {}", response);
            return response;
        }else{
            HashMap<String, Object> response = new HashMap<>();
            response.put("status", 400);
            log.debug("AuthController.getInfoByJwt() response: {}", response);
            return response;
        }
    }

    @PostMapping("/auth/signIn")
    public HashMap<String, Object> signIn(
            @RequestParam(name = "username") String username,
            @RequestParam(name = "password") String password
    ){
        log.debug("AuthController.signIn() called with username: {}", username);

        User user = userRepository.findByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())){
            HashMap<String, Object> response = new HashMap<>();
            response.put("username", user.getUsername());
            response.put("jwtToken", jwtUtil.generateToken(user));
            response.put("timestamp", System.currentTimeMillis());
            log.debug("AuthController.signIn() response: {}", response);
            return response;
        }else{
            throw new InvalidCredentialsException("Неверный логин или пароль");
        }
    }
}

