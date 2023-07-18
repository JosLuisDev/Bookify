package com.accenture.demobookify.controller;

import com.accenture.demobookify.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    //Solo validar que informacion hay en BD
    @GetMapping("/users/")
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(userRepository.findAll());
    }
}
