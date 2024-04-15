package com.example.register.controller;

import com.example.register.model.ResponseObject;
import com.example.register.model.User;
import com.example.register.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/user")
public class UserController{
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<User>> findAll() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }
//    @GetMapping("/findByName")
//    public ResponseEntity<List<User>> findByName(@RequestParam String name){
//        return new ResponseEntity<>(userService.findByName(name), HttpStatus.OK);
//    }
@GetMapping("/findByName")
public ResponseEntity<ResponseObject> findByNameContaining(@RequestParam String name){
        List<User> foundUser = userService.findByNameContaining(name);
        return foundUser.isEmpty() ?
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("Not OK", "No data with name = " + name, foundUser)
                ) : ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("OK","FOUNDED with name " + name,foundUser)
        );
}
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> findById(@PathVariable Long id){
        Optional<User> foundUser = userService.findById(id);
        return foundUser.isPresent() ?
             ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("OK","Found successfully", foundUser)
            ):             ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("false","Nothing founded with id = " + id,foundUser));
        }
    @PostMapping("/add")
    public ResponseEntity<ResponseObject> addUser(@RequestBody User user){
//        User addUser = userService.addUser(user);

//        return new ResponseEntity<>(addUser, HttpStatus.CREATED);
        List<User> foundUser = userService.findByNameContaining(user.getName().trim());
        if(foundUser.size() > 0){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("Fail to add new", "Duplicated user","wtf was that")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("Add success","Successfully add new user", userService.addUser(user))
        );
    }
    // upsert == if found => update else insert
    @PutMapping("/{id}")
    public  ResponseEntity<ResponseObject> updateUser(@PathVariable Long id, @RequestBody User user){
        User updateUser = userService.findById(id)
                .map(user1 -> {
                        user1.setName(user.getName());
                        user1.setEmail(user.getEmail());
                        user1.setPassword(user.getPassword());
                        return userService.updateUser(user1);
                }).orElseGet(() -> {
                    user.setId(id);
                    return userService.addUser(user);
                });
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("OK", "Update User Success", updateUser)
                );
    }
    }

