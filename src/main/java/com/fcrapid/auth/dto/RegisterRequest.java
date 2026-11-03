package com.fcrapid.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegisterRequest {
    private String email;
    private String password;
    private String username;
    private String imageURL;
    public RegisterRequest(String email, String password, String username, String imageURL){
        this.email = email;
        this.password = password;
        this.username = username;
        this.imageURL = imageURL;
    }
}
