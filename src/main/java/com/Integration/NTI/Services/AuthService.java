package com.Integration.NTI.Services;

import com.Integration.NTI.Exception.CustomException;
import com.Integration.NTI.Models.Entities.Role;
import com.Integration.NTI.Models.Entities.User;
import com.Integration.NTI.Models.Requests.SignInRequest;
import com.Integration.NTI.Models.Requests.SignUpRequset;
import com.Integration.NTI.Models.Response.TokenResponse;
import com.Integration.NTI.Repositries.UserRepo;
import com.Integration.NTI.Security.TokenUtil;
import com.Integration.NTI.Templates.UserWapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenUtil tokenUtil;


    public TokenResponse signInUser(SignInRequest signInRequest) throws CustomException {
        System.out.println("SignIn Method has been started");
        final Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getUserName(), signInRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(auth);
        UserWapper userWapper = userService.getByUsername(signInRequest.getUserName());

        String token = tokenUtil.generateToken(userWapper.getUser());
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setToken(token);

        return tokenResponse;
    }
    public User returnUserAuth() throws CustomException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = ((UserDetails) authentication.getPrincipal()).getUsername();
        return userService.getByUsername(userName).getUser();

    }

    public User SignUp(SignUpRequset userRequest) throws CustomException {
            User newUser = new User();

            long count = userRepo.countByUsername(userRequest.getUsername());
            if (count > 0) {
                throw new CustomException("THIS USER IS ALREADY EXISTS TRY ANOTHER USERNAME.. ", HttpStatus.FORBIDDEN);
            }

            newUser.setUserName(userRequest.getUsername());
            newUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            newUser.setAdmin(userRequest.isAdmin());

            Set<Role> roles = new HashSet<>();
            roles.add(newUser.isAdmin() ? Role.ADMIN : Role.USER);
            newUser.setRoles(roles);
            return userRepo.save(newUser);
        }

}

