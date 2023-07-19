package com.Integration.NTI.Controllers;
import com.Integration.NTI.Requests.CreateUserRequest;
import com.Integration.NTI.Models.Role;
import com.Integration.NTI.Models.User;
import com.Integration.NTI.Response.UserResponse;
import com.Integration.NTI.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
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
    public ResponseEntity<String> createUser(@Valid @RequestBody CreateUserRequest userRequest, BindingResult bindingResult)  {

        User newUser = new User();

        newUser.setUserName(userRequest.getUsername());
        newUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        newUser.setAdmin(userRequest.isAdmin());

        Set<Role> roles = new HashSet<>();
        roles.add(newUser.isAdmin()? Role.ADMIN : Role.USER);

        newUser.setRoles(roles);
        try {
            userService.addUser(newUser);
            return new ResponseEntity<>("USER ADDED SUCCESSFULLY", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("THIS USERNAME IS ALREADY TAKEN, TRY ANOTHER ONE.. ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping({"","/"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> findAll(){
        User authUser = userService.returnUserAuth();
        if(authUser.getRoles().contains(Role.ADMIN))
        {
            List<User> users = userService.findAll();
            List<UserResponse> responses = UserResponse.ConvertToUserResponse(users);
            return new ResponseEntity<>(responses, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
