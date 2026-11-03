package com.fcrapid.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthResponse {
    private String token;
    private String email;
    private String username;
    private String imageURL;

    public AuthResponse(String token, String email, String username){
        this.token = token;
        this.email = email;
        this.username = username;
    }
    public AuthResponse(String token, String email, String username, String imageURL){
        this.token = token;
        this.email = email;
        this.username = username;
        this.imageURL = imageURL;
    }

}
