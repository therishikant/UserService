package com.rishi.userservice.repositories;

import com.rishi.userservice.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Token save(Token token);
    Optional<Token> findByValueAndDeletedAndExpiryAtGreaterThan(String value,
                                                                boolean deleted,
                                                                Date expiryAt);
}
