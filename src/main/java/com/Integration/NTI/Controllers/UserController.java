package com.Integration.NTI.Controllers;

import com.Integration.NTI.Requests.CreateUserRequest;
import com.Integration.NTI.Models.Role;
import com.Integration.NTI.Models.User;
import com.Integration.NTI.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")

public class UserController {


    private UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @Autowired
    private PasswordEncoder passwordEncoder;
    @PostMapping("/SignUp")
    public ResponseEntity<String> createUser(@RequestBody CreateUserRequest userRequest) {
        System.out.println("USER ADDED SUCCESSFULLY");

        User newUser = new User();

        newUser.setUserName(userRequest.getUsername());
        newUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        newUser.setAdmin(userRequest.isAdmin());
        Set<Role> roles = new HashSet<>();
        if(userRequest.isAdmin()){
            roles.add(Role.ADMIN);
        }
        else{
            roles.add(Role.USER);
        }
        newUser.setRoles(roles);
        userService.addUser(newUser);
        return new ResponseEntity<>("USER ADDED SUCCESSFULLY", HttpStatus.CREATED);
    }
    @GetMapping({"","/"})
    public ResponseEntity<List<User>> findAll(){
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }


}
