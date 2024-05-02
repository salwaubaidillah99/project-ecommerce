package com.ecommerce.project.service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public Response createUsers(Users users) {
        if (!StringUtils.hasText(users.getUsername())){
            throw new BadRequestException("Username is required!");
        }
        if (usersRepository.existsByUsername(users.getUsername())){
            throw new BadRequestException("Username " + users.getUsername() + " is already taken");
        }
        if (users.getUsername().contains(" ")){
            throw new BadRequestException("Username cannot contain spaces!");
        }
        if (!users.getUsername().matches("[a-zA-Z0-9]+")){
            throw new BadRequestException("Username can only contain letters and numbers without symbols");
        }
        if (!users.getUsername().matches(".*\\d.*")){
            throw new BadRequestException("Username must contain at least one number!");
        }
        if (usersRepository.existsByEmail(users.getEmail())){
            throw new BadRequestException("Email"+ users.getEmail()+"already taken");
        }
        if (usersRepository.existsByTelephone(users.getTelephone())){
            throw new BadRequestException("Telephone Number"+ users.getTelephone()+"already taken");
        }

        String hashedPassword = hashPassword(users.getPassword());
        users.setPassword(hashedPassword);

        String usersId = "users-" + UUID.randomUUID().toString();
        users.setId(usersId);

        Users createdUsers = usersRepository.save(users);

        return new Response(createdUsers, "User successfully created", HttpStatus.CREATED);
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

    public Response editUsers(Users updatedUsers) {
        Optional<Users> optionalExistingUsers = usersRepository.findById(updatedUsers.getId());
        if (optionalExistingUsers.isPresent()) {
            Users existingUsers = optionalExistingUsers.get();

            if (!existingUsers.getUsername().equals(updatedUsers.getUsername())) {
                if (usersRepository.existsByUsername(updatedUsers.getUsername())) {
                    throw new BadRequestException("Username " + updatedUsers.getUsername() + " already in use");
                }
            }

            if (!existingUsers.getEmail().equals(updatedUsers.getEmail())) {
                if (usersRepository.existsByEmail(updatedUsers.getEmail())){
                    throw new BadRequestException("Email "+ updatedUsers.getEmail() + " already taken");
                }
            }

            if (!existingUsers.getTelephone().equals(updatedUsers.getTelephone())) {
                if (usersRepository.existsByTelephone(updatedUsers.getTelephone())){
                    throw new BadRequestException("Telephone Number " + updatedUsers.getTelephone() + " already taken");
                }
            }

            if (updatedUsers.getUsername().contains(" ")) {
                throw new BadRequestException("Username cannot contain spaces!");
            }

            if (!containsNumber(updatedUsers.getUsername())) {
                throw new BadRequestException("Username must contain at least one number!");
            }

            existingUsers.setFullname(updatedUsers.getFullname());
            existingUsers.setEmail(updatedUsers.getEmail());
            existingUsers.setAddress(updatedUsers.getAddress());
            existingUsers.setTelephone(updatedUsers.getTelephone());
            existingUsers.set_Active(updatedUsers.is_Active());
            existingUsers.setUser_role(updatedUsers.getUser_role());
            existingUsers.setPassword(updatedUsers.getPassword());
            existingUsers.setUsername(updatedUsers.getUsername());
            existingUsers.setUpdateAt(new Date());

            Users savedUsers = usersRepository.save(existingUsers);

            return new Response(savedUsers, "User has been successfully updated.", HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("User with ID " + updatedUsers.getId() + " not found");
        }
    }

    private boolean containsNumber(String str) {
        return str.matches(".*\\d.*");
    }

    private boolean containsSymbol(String str) {
        Pattern pattern = Pattern.compile("[!@#$%^&*(),.?\":{}|<>]");
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }

    private String hashPassword(String plainTextPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(plainTextPassword);
    }
}