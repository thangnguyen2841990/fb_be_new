package com.example.demo.controller;

import com.example.demo.config.JWTProvider;
import com.example.demo.config.exception.CustomException;
import com.example.demo.model.dto.*;
import com.example.demo.model.entity.User;
import com.example.demo.service.user.IUSerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
public class AuthRestController {
    @Autowired
    private IUSerService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTProvider jwtProvider;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
           Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt =  jwtProvider.generateTokenLogin(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User currentUser = userService.findByUsername(loginRequest.getUsername()).get();
            return ResponseEntity.ok(new JwtResponse(jwt, currentUser.getId(), currentUser.getUsername(), userDetails.getAuthorities()));
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody SignUpForm user) {
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User newUser = new User(user.getUsername(), user.getPassword(), user.getEmail(), user.getPhoneNumber(), user.getBirthDay());
        newUser.setAvatar("1651409918383facebook-cap-nhat-avatar-doi-voi-tai-khoan-khong-su-dung-anh-dai-dien-e4abd14d.jpg");
        newUser.setBackGround("16608036884971651369093230Cach-mo-duong-dan-tren-Facebook-bang-trinh-duyet-Chrome.jpg");
            userService.saveUser(newUser);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
    @PostMapping("/usernameExist/username/{username}")
    public ResponseEntity<UsernameExist> usernameExist(@PathVariable String username) {
        UsernameExist usernameExist = this.userService.isUsernameExist(username);
        return new ResponseEntity<>(usernameExist, HttpStatus.OK);
    }
    @PostMapping("/passwordCorrect/username/{username}/password/{password}")
    public ResponseEntity<PasswordCorrect> passwordCorrect(@PathVariable  String username,@PathVariable String password) {
        PasswordCorrect passwordCorrect = this.userService.isPasswordCorrect(username,password);
        return new ResponseEntity<>(passwordCorrect, HttpStatus.OK);
    }
}
