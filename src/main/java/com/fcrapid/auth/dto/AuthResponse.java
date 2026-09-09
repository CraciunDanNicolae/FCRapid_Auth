package com.fcrapid.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthResponse {
    private String token;
    private String email;
    private String username;

    public AuthResponse(String token, String email, String username){
        this.token = token;
        this.email = email;
        this.username = username;
    }
}
