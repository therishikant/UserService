package com.rishi.userservice.controllers;

import com.rishi.userservice.dtos.*;
import com.rishi.userservice.models.Token;
import com.rishi.userservice.models.User;
import com.rishi.userservice.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public UserDto signUp(@RequestBody SignupRequestDto signupRequestDto) {
        User user = userService.signup(signupRequestDto.getName(),
                signupRequestDto.getEmail(),
                signupRequestDto.getPassword());
        
        return UserDto.fromUser(user);
    }

    @PostMapping("/signin")
    public SigninResponseDto signIn(@RequestBody SigninRequestDto signinRequestDto) {
        Token token = userService.login(signinRequestDto.getEmail(), signinRequestDto.getPassword());
        return new SigninResponseDto(token.getValue());
    }

    @PostMapping("/signout")
    public ResponseEntity<Void> signOut(@RequestBody SignoutRequestDto signoutRequestDto) {
        /*
        * check if token exist
        * Change it to deleted
        * */
        Boolean status =  userService.logout(signoutRequestDto.getTokenValue());
        if(!status){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/validate/{token}")
    public ResponseEntity<Boolean> validateToken(@PathVariable("token") String tokenValue) {
        User user = userService.validateToken(tokenValue);
        if(user == null) {
            return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
