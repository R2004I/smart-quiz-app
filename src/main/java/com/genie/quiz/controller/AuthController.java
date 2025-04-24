package com.genie.quiz.controller;

import com.genie.quiz.dto.LoginDTO;
import com.genie.quiz.dto.UserDTO;
import com.genie.quiz.service.CustomUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api")
@Tag(name = "Auth Controller", description = "APIs for registration and login")
public class AuthController {

    @Autowired
    private CustomUserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Operation(summary = "Register here", description = "Register here to interact with APIs")
    @ApiResponse(responseCode = "200", description = "User registered successfully",
            content = @Content(mediaType = "application/json"))
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDTO userDTO) {
        try {
            userService.registerUser(userDTO);
            return ResponseEntity.ok("User registered successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @Operation(summary = "Login here", description = "Login here if you already registered, to interact with APIs")
    @ApiResponse(responseCode = "200", description = "Login successfully",
            content = @Content(mediaType = "application/json"))
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
            );
            return ResponseEntity.ok("Login successful");
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @Operation(summary = "Delete a existing user", description = "Only admin API",security = @SecurityRequirement(name = "basicAuth"))
    @ApiResponse(responseCode = "200", description = "User deleted successfully",
            content = @Content(mediaType = "application/json"))
    @DeleteMapping("/admin/user/delete")
    public ResponseEntity<String> deleteUser(@RequestParam Integer id){
        String response = userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Delete a existing user", description = "update password using your registered email id")
    @ApiResponse(responseCode = "200", description = "Password updated successfully",
            content = @Content(mediaType = "application/json"))
    @PutMapping("/admin/user/update")
    public ResponseEntity<String> updatePass(@RequestParam String email, @RequestParam String newPass){
        String response = userService.updatePassword(email,newPass);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
