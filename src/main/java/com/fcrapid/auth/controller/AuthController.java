package com.fcrapid.auth.controller;
import com.fcrapid.auth.dto.AuthResponse;
import com.fcrapid.auth.dto.LoginRequest;
import com.fcrapid.auth.dto.RegisterRequest;
import com.fcrapid.auth.dto.UpdateRequest;
import com.fcrapid.auth.model.User;
import com.fcrapid.auth.security.JwtUtil;
import com.fcrapid.auth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/auth")
@RestController
public class AuthController {
    private AuthService service;
    private JwtUtil jwtUtil;
    public AuthController(AuthService service, JwtUtil jwtUtil){
        this.service = service;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUser(@RequestBody RegisterRequest register){
        User user = service.registerUser(register.getEmail(), register.getPassword(), register.getUsername());
        if(user == null){
            return ResponseEntity.badRequest().build();
        }
         return ResponseEntity.ok(new AuthResponse(jwtUtil.generateToken(register.getEmail()), register.getEmail(), register.getUsername()));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody LoginRequest login){
         User user  = service.login(login.getEmail(), login.getPassword());
        if(user == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(new AuthResponse(jwtUtil.generateToken(login.getEmail()), login.getEmail(), user.getUsername()));
    }

    @PostMapping("/deleteUser")
    public void delete(@RequestBody LoginRequest login){
        service.deleteUser(login.getEmail());
    }

    @PutMapping("/update")
    public ResponseEntity<AuthResponse> update(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody UpdateRequest request) {
        String token = authHeader.substring(7);
        if(!jwtUtil.isTokenValid(token)){
            return ResponseEntity.status(401).build();
        }
        String currentEmail = jwtUtil.extractEmail(token);
        User user  = service.updateUser(currentEmail, request.getUsername(), request.getEmail());
        return ResponseEntity.ok(new AuthResponse(jwtUtil.generateToken(user.getEmail()), user.getEmail(), user.getUsername()));
    }

}
