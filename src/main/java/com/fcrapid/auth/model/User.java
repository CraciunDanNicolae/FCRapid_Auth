package com.fcrapid.auth.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Table(name = "users")
@Entity
public class User {
    @jakarta.persistence.Id
    @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    @Getter
    private String email;
    @Getter
    private String username;
    @Getter
    private String password;
    @Getter
    private LocalDateTime creationDate;

    public User(){
        email = "";
        username = "";
        password = "";
        creationDate = LocalDateTime.now();
    }

    public User(String email, String username, String password){
        this.email = email;
        this.username = username;
        this.password = password;
        creationDate = LocalDateTime.now();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
