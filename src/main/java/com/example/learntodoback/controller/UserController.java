package com.example.learntodoback.controller;

import com.example.learntodoback.entity.User;
import com.example.learntodoback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;

    @GetMapping()
    public String getUser() {
        System.out.println("yes");
        return "yes";
    }
}
