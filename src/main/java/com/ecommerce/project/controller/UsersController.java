package com.ecommerce.project.controller;

import com.ecommerce.project.entity.Users;
import com.ecommerce.project.exception.ResourceNotFoundException;
import com.ecommerce.project.exception.Response;
import com.ecommerce.project.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UsersController {
    @Autowired
    private UsersService usersService;

    @GetMapping("/users")
    public ResponseEntity<Response> findAll(){
        Response response = usersService.findAll();
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Response> findById (@PathVariable ("id") String id){
        Response response = usersService.findById(id);
        return  new ResponseEntity<>(response, response.getStatus());
    }

    @PostMapping("/users/register")
    public ResponseEntity<Response>registerUser (@RequestBody Users users){
        Response response = usersService.registerUser(users);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PutMapping("/users/edit/{id}")
    public  ResponseEntity<Response>editUsers (@PathVariable("id") String id, @RequestBody Users updatedUsers){
        updatedUsers.setId(id);
        try{
        Response response = usersService.editUser(updatedUsers);
        return  ResponseEntity.ok().body(response);
        } catch (ResourceNotFoundException e){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(null, e.getMessage(), HttpStatus.NOT_FOUND));
        }
    }

    @DeleteMapping("/users/delete/{id}")
    public ResponseEntity<Response>deleteUser (@PathVariable("id") String id){
        Response response = usersService.deleteById(id);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PostMapping ("/users/login")
    public  ResponseEntity<Response>loginUser(@RequestBody Users users){
        Response response = usersService.loginUser(users);
        return new ResponseEntity<>(response, response.getStatus());
    }
}

