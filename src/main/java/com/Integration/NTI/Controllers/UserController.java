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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLIntegrityConstraintViolationException;
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
        if (bindingResult.hasErrors()) {
            // handle validation errors
            return new ResponseEntity<>("",HttpStatus.BAD_REQUEST);
        }
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
        try {
            userService.addUser(newUser);
            return new ResponseEntity<>("USER ADDED SUCCESSFULLY", HttpStatus.CREATED);
        } catch (Exception e) {
            // handle other exceptions
            return new ResponseEntity<>("THIS USERNAME IS ALREADY TAKEN, TRY ANOTHER ONE.. ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping({"","/"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> findAll(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
        if(isAdmin){
            List<UserResponse> responses = new ArrayList<>();
            List<User> users = userService.findAll();
            for(User user : users){
                UserResponse userResponse;
                try {
                    userResponse = new UserResponse(user.getUserName(), user.getPassword(), user.getCart().getId());
                }catch (NullPointerException e){
                    userResponse = new UserResponse(user.getUserName(), user.getPassword());
                }
                responses.add(userResponse);
            }
            return new ResponseEntity<>(responses, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

    }
}
