package com.mphasis.fileapplication.api;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import com.mphasis.fileapplication.exceptions.InvalidCredentialsException;
import com.mphasis.fileapplication.exceptions.ResourceNotFoundException;
import com.mphasis.fileapplication.model.dto.AuthRequest;
import com.mphasis.fileapplication.model.dto.AuthResponse;
import com.mphasis.fileapplication.model.entity.UserEntity;
import com.mphasis.fileapplication.service.UserService;
import com.mphasis.fileapplication.utility.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

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
    @Operation(summary = "Register A User",description="Register the new user")
	@ApiResponse(responseCode ="200",description="success")
    @PostMapping("/register")
    public UserEntity registerUser(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");
        if (username == null ||username.trim().isEmpty()|| password == null) {
            throw new RuntimeException("Username or password is missing");
        }
        return userService.registerUser(username, password);
    }
    @Operation(summary = "Login the User",description="The registered user will be logged in")
	@ApiResponse(responseCode ="200",description="success")
    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest authRequest) {
    	try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (AuthenticationException e) {
            throw new InvalidCredentialsException("Invalid username or password!");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        String token = jwtUtil.generateToken(userDetails.getUsername());
        return ResponseEntity.ok(new AuthResponse(token));
    }
    @Operation(summary = "Retrive A User",description="The registered user will be retrived")
	@ApiResponse(responseCode ="200",description="success")
    @GetMapping("/user/{username}")
    public UserEntity getUser(@PathVariable String username) {
    	UserEntity user = userService.getUser(username);
        if (user == null) {
            throw new ResourceNotFoundException("User not found: " + username);
        }
        return user;
    }
    @Operation(summary = "Update the User",description="Update the user in database")
	@ApiResponse(responseCode ="200",description="success")
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
