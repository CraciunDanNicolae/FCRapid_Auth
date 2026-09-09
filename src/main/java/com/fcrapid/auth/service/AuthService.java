package com.fcrapid.auth.service;
import com.fcrapid.auth.model.User;
import com.fcrapid.auth.repository.AuthRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {
    private AuthRepository repository;
    private BCryptPasswordEncoder encoder;
    public AuthService(AuthRepository repository, BCryptPasswordEncoder encoder){
        this.repository = repository;
        this.encoder = encoder;
    }
    public User registerUser(String email, String passsword, String username){
        if(repository.findByEmail(email) != null){
            return null;
        }
        String hiddenPassword = encoder.encode(passsword);
        User user = new User(email, username, hiddenPassword);
        repository.save(user);
        return user;
    }
    public User login(String email, String password){
        User user = repository.findByEmail(email);
        if(user == null){
            return null;
        }
        if(!encoder.matches(password, user.getPassword())){
            return null;
        }
        return user;
    }
    public void deleteUser(String email){
        repository.deleteByEmail(email);
    }
}
