package com.rishi.userservice.services;

import ch.qos.logback.classic.encoder.JsonEncoder;
import com.rishi.userservice.models.Token;
import com.rishi.userservice.models.User;
import com.rishi.userservice.repositories.TokenRepository;
import com.rishi.userservice.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;
    TokenRepository tokenRepository;

    UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public User signup(String name, String email, String password) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        return userRepository.save(user);
    }

    @Override
    public Token login(String email, String password) {
        /*
        * check if email exits
        * check if password is valid
        * generate a token and save it to DB
        * return the token
         */
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
             return null;
        }
        if(!bCryptPasswordEncoder.matches(password, userOptional.get().getPassword())) {
            return null;
        }

        Token token = new Token();
        token.setUser(userOptional.get());
        token.setValue(RandomStringUtils.randomAlphanumeric(128));

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 30);
        Date expiration = calendar.getTime();
        token.setExpiryAt(expiration);
        
        return tokenRepository.save(token);
    }

    @Override
    public Boolean logout(String tokenValue) {
        /*
        * check if token exist
        * check if token is deleted
        * check if expired
        *
        */
        Optional<Token> optionalToken = tokenRepository.findByValueAndDeletedAndExpiryAtGreaterThan(tokenValue, false, new Date());
        if(optionalToken.isEmpty()) {
            return false;
        }
        Token token = optionalToken.get();
        token.setDeleted(true);
        token.setExpiryAt(new Date());
        tokenRepository.save(token);
        return true;
    }

    @Override
    public User validateToken(String tokenValue) {
        /*
        * check if token exist
        * check if token is deleted
        * check if expired
        * */
        Optional<Token> tokenOptional = tokenRepository.findByValueAndDeletedAndExpiryAtGreaterThan(tokenValue, false, new Date());
        if (tokenOptional.isEmpty()) {
            //throw an exception
            return null;
        }
        return tokenOptional.get().getUser();
    }
}
