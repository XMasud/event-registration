package com.pm.userservice.service;

import com.pm.userservice.dto.RegisterRequestDTO;
import com.pm.userservice.dto.RegisterResponseDTO;
import com.pm.userservice.exception.ExistingUserException;
import com.pm.userservice.model.User;
import com.pm.userservice.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public RegisterResponseDTO createUser(RegisterRequestDTO request) {

        Optional<User> existingUser = userRepository.findUserByEmail(request.getEmail());

        if (existingUser.isPresent())
            throw new ExistingUserException("Email already exists!");

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        User saveUser = userRepository.save(user);

        return new RegisterResponseDTO(
                saveUser.getId(),
                saveUser.getName(),
                saveUser.getEmail(),
                saveUser.getRole()
        );
    }

    public RegisterResponseDTO getUserByEmail(String email) {

        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new ExistingUserException("User not associated with this email!"));

        return new RegisterResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }

}
