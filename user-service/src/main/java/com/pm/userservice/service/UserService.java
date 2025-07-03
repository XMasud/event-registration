package com.pm.userservice.service;

import com.pm.userservice.dto.UserRequestDTO;
import com.pm.userservice.dto.UserResponseDTO;
import com.pm.userservice.exception.ExistingUserException;
import com.pm.userservice.model.User;
import com.pm.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    public UserResponseDTO createUser(UserRequestDTO request) {

        User existingUser = userRepository.findUserByEmail(request.getEmail());

        if (existingUser != null)
            throw new ExistingUserException("Email already exists!");

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        User saveUser = userRepository.save(user);

        return new UserResponseDTO(
                saveUser.getName(),
                saveUser.getEmail()
        );
    }

    public UserResponseDTO getUserByEmail(String email) {

        User existingUser = userRepository.findUserByEmail(email);

        if (existingUser == null)
            throw new ExistingUserException("User not associated with this email!");

        return new UserResponseDTO(
                existingUser.getName(),
                existingUser.getEmail()
        );
    }
}
