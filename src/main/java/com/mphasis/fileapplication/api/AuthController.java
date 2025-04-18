package com.mphasis.fileapplication.api;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import com.mphasis.fileapplication.model.dto.AuthRequest;
import com.mphasis.fileapplication.model.dto.AuthResponse;
import com.mphasis.fileapplication.model.entity.UserEntity;
import com.mphasis.fileapplication.service.UserService;
import com.mphasis.fileapplication.utility.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UserService userService;

    @Autowired
    public AuthController(JwtUtil jwtUtil, AuthenticationManager authenticationManager,
                          UserDetailsService userDetailsService, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserEntity registerUser(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");
        if (username == null || password == null) {
            throw new RuntimeException("Username or password is missing");
        }
        return userService.registerUser(username, password);
    }


    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        String token = jwtUtil.generateToken(userDetails.getUsername());
        System.out.println("Inside authenticate method");
        System.out.println("Username: " + authRequest.getUsername());

        return ResponseEntity.ok(new AuthResponse(token));
    }

    @GetMapping("/user/{username}")
    public UserEntity getUser(@PathVariable String username) {
        return userService.getUser(username);
    }

    @PutMapping("/user/{username}/role")
    public String updateRole(@PathVariable String username, @RequestParam String newRole) {
        try {
            userService.updateRole(username, newRole);
            return "Role updated successfully!";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }
}
