package com.myApp.task_management.Controller;

import com.myApp.task_management.Configuration.JwtUtil;
import com.myApp.task_management.Entity.UserDTO;
import com.myApp.task_management.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth/v1")
@ComponentScan
public class LoginController {

    private final UserService userService;

    private final JwtUtil jwtUtil;

    private final AuthenticationManager authenticationManager;

    public LoginController(UserService userService, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/user")
    public ResponseEntity<Object> createUser(@RequestBody UserDTO userDTO) {
        UserDTO createdUserDTO = userService.createUser(userDTO);
        return new ResponseEntity<>(createdUserDTO, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDTO userDTO) {
        log.info("Attempting to authenticate user: {}", userDTO.getUserName());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                (userDTO.getUserName(), userDTO.getPassword()));
        log.info("Authentication successful for user: {}", userDTO.getUserName());
        String jwt = jwtUtil.generateToken(userDTO.getUserName());
        return new ResponseEntity<>(jwt, HttpStatus.OK);
    }
}
