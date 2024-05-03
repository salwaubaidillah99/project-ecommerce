package com.ecommerce.project.service;

import java.util.*;

import com.ecommerce.project.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.project.entity.Users;
import com.ecommerce.project.exception.ResourceNotFoundException;
import com.ecommerce.project.exception.Response;
import com.ecommerce.project.repository.UsersRepository;
import org.springframework.util.StringUtils;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;

    public Response findById(String id) {
        Optional<Users> usersOptional = usersRepository.findById(id);
        if (usersOptional.isPresent()) {
            Users findUsers = usersOptional.get();
            usersRepository.findById(id);
            return new Response(findUsers, "The users with " + id + " has been found.", HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("The users with ID " + id + "was not found");
        }
    }

    public Response findAll() {
        List<Users> categories = usersRepository.findAll();
            return new Response(categories, "Success , all users data is displayed", HttpStatus.OK);
    }

    public Response registerUser(Users users) {
        validateUser(users, true);

        String hashedPassword = hashPassword(users.getPassword());
        users.setPassword(hashedPassword);

        String userId = "users-" + UUID.randomUUID().toString();
        users.setId(userId);

        Users createdUser = usersRepository.save(users);

        return new Response(createdUser, "User successfully registered", HttpStatus.CREATED);
    }

    private void validateUser(Users users, boolean isNewUser) {
        if (!StringUtils.hasText(users.getUsername())) {
            throw new BadRequestException("Username is required!");
        }

        if (isNewUser || !users.getUsername().equals(usersRepository.findById(users.getId())
                .map(Users::getUsername)
                .orElse(null))) {
            if (usersRepository.existsByUsername(users.getUsername())) {
                throw new BadRequestException("Username " + users.getUsername() + " is already taken");
            }
        }

        if (users.getUsername().contains(" ")) {
            throw new BadRequestException("Username cannot contain spaces!");
        }
        if (!users.getUsername().matches("[a-zA-Z0-9]+")) {
            throw new BadRequestException("Username can only contain letters and numbers without symbols");
        }
        if (!users.getUsername().matches(".*\\d.*")) {
            throw new BadRequestException("Username must contain at least one number!");
        }

        if (isNewUser || !users.getEmail().equals(usersRepository.findById(users.getId())
                .map(Users::getEmail)
                .orElse(null))) {
            if (usersRepository.existsByEmail(users.getEmail())) {
                throw new BadRequestException("Email " + users.getEmail() + " is already taken");
            }
        }
        if (isNewUser || !users.getTelephone().equals(usersRepository.findById(users.getId())
                .map(Users::getTelephone)
                .orElse(null))) {
            if (usersRepository.existsByTelephone(users.getTelephone())) {
                throw new BadRequestException("telephone " + users.getTelephone() + " is already taken");
            }
        }
    }


    public Response loginUser(Users loginRequest) {
        Users user = usersRepository.findByUsername(loginRequest.getUsername());
        if (user != null && new BCryptPasswordEncoder().matches(loginRequest.getPassword(), user.getPassword())) {
            return new Response("Login Successful", HttpStatus.OK);
        }
        throw new ResourceNotFoundException("Invalid username or password");
    }

    private String hashPassword(String plainTextPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(plainTextPassword);
    }

    public Response editUser(Users updatedUser) {
        Optional<Users> optionalExistingUser = usersRepository.findById(updatedUser.getId());
        if (optionalExistingUser.isPresent()) {
            Users existingUser = optionalExistingUser.get();

            boolean isNewUser = !existingUser.getUsername().equals(updatedUser.getUsername());

            validateUser(updatedUser, isNewUser);

            if (StringUtils.hasText(updatedUser.getPassword())) {
                String hashedPassword = hashPassword(updatedUser.getPassword());
                existingUser.setPassword(hashedPassword);
            }

            existingUser.setUpdateAt(new Date());
            existingUser.setFullname(updatedUser.getFullname());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setAddress(updatedUser.getAddress());
            existingUser.setTelephone(updatedUser.getTelephone());
            existingUser.set_Active(updatedUser.is_Active());
            existingUser.setUser_role(updatedUser.getUser_role());
            existingUser.setUsername(updatedUser.getUsername());

            Users savedUser = usersRepository.save(existingUser);

            return new Response(savedUser, "User has been successfully updated.", HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("User with ID " + updatedUser.getId() + " not found");
        }
    }

    private boolean containsNumber(String str) {
        return str.matches(".*\\d.*");
    }


    public Response deleteById(String id) {
        Optional<Users> usersOptional = usersRepository.findById(id);
        if (usersOptional.isPresent()) {
            Users deletedUsers = usersOptional.get();
            usersRepository.deleteById(id);
            return new Response(deletedUsers, "The users with ID " +id+ " has been successfully deleted.", HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Users with ID " + id + " not found!");
        }
    }
}