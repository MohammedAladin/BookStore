package com.Integration.NTI.Controllers;
import com.Integration.NTI.Exception.CustomException;
import com.Integration.NTI.Requests.CreateUserRequest;
import com.Integration.NTI.Templates.ResponseWrapper;
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
import java.util.List;
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
    public ResponseEntity<String> createUser(@Valid @RequestBody CreateUserRequest userRequest)  {
        try {
            userService.addUser(userRequest);
            return new ResponseEntity<>("USER ADDED SUCCESSFULLY", HttpStatus.CREATED);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getDescription(), e.getStatus());
        }
    }
    @GetMapping({"","/"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseWrapper<UserResponse>> findAll(){
      try {
          List<UserResponse> userResponses = userService.findAll();
          ResponseWrapper<UserResponse> response = new ResponseWrapper<>();
          response.setDataList(userResponses);
          response.setErrorMessage("NO ERRORS HAVE BEEN CATCHED..");
          return new ResponseEntity<>(response, HttpStatus.OK);
      }catch (CustomException ex){
          ResponseWrapper<UserResponse> errorResponse = new ResponseWrapper<>();
          errorResponse.setErrorMessage("Error: " + ex.getMessage());
          return new ResponseEntity<>(errorResponse, ex.getStatus());
      }
    }
    @GetMapping(value = "/getByName")
    public ResponseEntity<ResponseWrapper<UserResponse>> getUserByName(@RequestParam String name){
        ResponseWrapper<UserResponse> response = new ResponseWrapper<>();
        try {
            UserResponse userResponse = userService.getByUsername(name).getUserResponse();
            response.setData(userResponse);
            response.setErrorMessage("NO ERRORS HAVE BEEN CATCHED..");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (CustomException ex){
            response.setErrorMessage("Error: " + ex.getMessage());
                return new ResponseEntity<>(response, ex.getStatus());
            }
    }
}
