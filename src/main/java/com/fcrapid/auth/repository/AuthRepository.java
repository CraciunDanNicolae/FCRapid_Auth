package com.fcrapid.auth.repository;

import com.fcrapid.auth.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    @Transactional
    void deleteByEmail(String email);
}
