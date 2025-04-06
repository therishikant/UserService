package com.rishi.userservice.services;

import com.rishi.userservice.models.Token;
import com.rishi.userservice.models.User;

public interface UserService {
    User signup(String name, String email, String password);
    Token login(String email, String password);
    Boolean logout(String tokenValue);
    User validateToken(String tokenValue);
}
