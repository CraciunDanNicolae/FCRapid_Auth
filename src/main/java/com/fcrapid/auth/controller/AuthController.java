package com.fcrapid.auth.controller;
import com.fcrapid.auth.dto.AuthResponse;
import com.fcrapid.auth.dto.LoginRequest;
import com.fcrapid.auth.dto.RegisterRequest;
import com.fcrapid.auth.dto.UpdateRequest;
import com.fcrapid.auth.model.User;
import com.fcrapid.auth.security.JwtUtil;
import com.fcrapid.auth.service.AuthService;
import com.fcrapid.auth.service.ImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/auth")
@RestController
public class AuthController {
    private AuthService authService;
    private JwtUtil jwtUtil;
    private ImageService imageService;
    public AuthController(AuthService authService, JwtUtil jwtUtil, ImageService imageService){
        this.authService = authService;
        this.jwtUtil = jwtUtil;
        this.imageService = imageService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUser(@RequestBody RegisterRequest register){
        User user = authService.registerUser(register.getEmail(), register.getPassword(), register.getUsername(), register.getImageURL() != null ? register.getImageURL() : "");
        if(user == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(new AuthResponse(jwtUtil.generateToken(user.getEmail()), user.getEmail(), user.getUsername(), user.getImageURL()));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody LoginRequest login){
        User user = authService.login(login.getEmail(), login.getPassword());
        if(user == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(new AuthResponse(jwtUtil.generateToken(user.getEmail()), user.getEmail(), user.getUsername(), user.getImageURL()));
    }

    @PostMapping("/deleteUser")
    public void delete(@RequestBody LoginRequest login){
        authService.deleteUser(login.getEmail());
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
        User user = authService.updateUser(currentEmail, request.getUsername(), request.getEmail());
        return ResponseEntity.ok(new AuthResponse(jwtUtil.generateToken(user.getEmail()), user.getEmail(), user.getUsername(), user.getImageURL()));
    }

    @PostMapping("/uploadPicture")
    public ResponseEntity<AuthResponse> uploadPicture(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam("image") MultipartFile image) throws Exception {
        String token = authHeader.substring(7);
        if(!jwtUtil.isTokenValid(token)){
            return ResponseEntity.status(401).build();
        }
        String email = jwtUtil.extractEmail(token);
        String imageURL = imageService.uploadImage(image);
        User user = imageService.changeImage(email, imageURL);
        return ResponseEntity.ok(new AuthResponse(jwtUtil.generateToken(user.getEmail()), user.getEmail(), user.getUsername(), user.getImageURL()));
    }

}
