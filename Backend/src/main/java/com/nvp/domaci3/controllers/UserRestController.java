package com.nvp.domaci3.controllers;

import com.nvp.domaci3.model.User;
import com.nvp.domaci3.services.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService){
        this.userService=userService;
    }

    @PreAuthorize("hasAuthority('READ_USERS')")
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAllUsers(){
        return userService.findAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<User> findUserById(@PathVariable Integer id){
    return userService.findById(id);
  }

    @GetMapping(value = "/email/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<User> findUserByEmail(@PathVariable String email){
    return userService.findByEmail(email);
  }

    @PreAuthorize("hasAuthority('CREATE_USERS')")
    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public User addUser(@RequestBody User user){
        return userService.save(user);
    }

    @PreAuthorize("hasAuthority('DELETE_USERS')")
    @DeleteMapping(value = "/remove/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        Optional<User> optionalUser = userService.findById(id);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            userService.deleteById(id);

            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAuthority('UPDATE_USERS')")
    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public User updateUser(@PathVariable Integer id, @RequestBody User user){
        return userService.update(id, user);
    }
}
