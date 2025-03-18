package com.genie.quiz.service;

import com.genie.quiz.dto.UserDTO;
import com.genie.quiz.entity.UserEntity;
import com.genie.quiz.exception.ResourceNotFound;
import com.genie.quiz.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserService implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepo.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return User.withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }

    public void registerUser(UserDTO userDTO) {
        // Check if the user already exists
        if (userRepo.findByEmail(userDTO.getEmail()) != null) {
            throw new IllegalArgumentException("Email already exists: " + userDTO.getEmail());
        }

        UserEntity user = new UserEntity();
        user.setFullName(userDTO.getFullName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword())); // Encode the password
        user.setRole(userDTO.getRole());
        userRepo.save(user);
    }

    public String updatePassword(String email, String newPass){
        UserEntity existingUser = userRepo.findByEmail(email);
        if(existingUser==null){
            throw new UsernameNotFoundException("User with email id"+email+" not found");
        }
        existingUser.setPassword(passwordEncoder.encode(newPass));
        userRepo.save(existingUser);
        return "Password updates successfully. New password is: "+newPass;
    }

    public String deleteUser(Integer id){
        UserEntity existingUser = userRepo.findById(id).orElseThrow(()-> new ResourceNotFound("User with id"+id+" not found"));
        userRepo.deleteById(id);
        return "User deleted successfully";
    }
}
