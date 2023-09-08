package com.Integration.NTI.Controllers;

import com.Integration.NTI.Exception.CustomException;
import com.Integration.NTI.Models.Requests.SignInRequest;
import com.Integration.NTI.Models.Requests.SignUpRequset;
import com.Integration.NTI.Models.Response.TokenResponse;
import com.Integration.NTI.Services.AuthService;
import com.Integration.NTI.Templates.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RequestMapping(value = "/api/auth")
@RestController
public class AuthController {



    @Autowired
    private AuthService authService;


    @PostMapping("/SignIn")
    public ResponseEntity<ResponseWrapper<TokenResponse>> signIn(@RequestBody SignInRequest signInRequest) {
        ResponseWrapper<TokenResponse> response = new ResponseWrapper<>();
        System.out.println("SignIn Method has been started");

        try {
            TokenResponse tokenResponse = authService.signInUser(signInRequest);
            response.setData(tokenResponse);
            response.setErrorMessage("User Signed In Successfully..");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (CustomException ex) {
            response.setErrorMessage("Error: " + ex.getMessage());
            return new ResponseEntity<>(response, ex.getStatus());
        }
    }
    @PostMapping("/SignUp")
    public ResponseEntity<String> createUser(@Valid @RequestBody SignUpRequset userRequest)  {
        try {
            authService.SignUp(userRequest);
            return new ResponseEntity<>("USER Signed Up SUCCESSFULLY", HttpStatus.CREATED);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getDescription(), e.getStatus());
        }
    }
}
