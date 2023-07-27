package com.Integration.NTI.Services;

import com.Integration.NTI.Exception.CustomException;
import com.Integration.NTI.Models.Book;
import com.Integration.NTI.Models.Role;
import com.Integration.NTI.Models.User;
import com.Integration.NTI.Repositries.BookRepo;
import com.Integration.NTI.Repositries.UserRepo;
import com.Integration.NTI.Requests.CreateUserRequest;
import com.Integration.NTI.Response.UserResponse;
import com.Integration.NTI.Templates.UserWapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Load the user from your data source
        User user = userRepo.findByUserName(username);

        // Add the "ADMIN" role to the user's authorities
        List<GrantedAuthority> authorities = new ArrayList<>(user.getRoles().size());
        if(user.isAdmin()){
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        else{
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        // Return a UserDetails object with the modified authorities
        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),
                authorities
        );
    }
    public User returnUserAuth() throws CustomException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = ((UserDetails) authentication.getPrincipal()).getUsername();
        return getByUsername(userName).getUser();

    }

    public User addUser(CreateUserRequest userRequest) throws CustomException {
        User newUser = new User();
        User current;

        try {
            current = returnUserAuth();
        }catch (CustomException exception){
            throw new CustomException(exception.getDescription(), exception.getStatus());
        }
        if(current.isAdmin()) {
            newUser.setUserName(userRequest.getUsername());
            newUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            newUser.setAdmin(userRequest.isAdmin());

            for(User user : userRepo.findAll()){
                if(user.getUserName().equals(userRequest.getUsername()))
                    throw new CustomException("THIS USER IS ALREADY EXISTS TRY ANOTHER USERNAME.. ", HttpStatus.FORBIDDEN);
            }

            Set<Role> roles = new HashSet<>();
            roles.add(newUser.isAdmin() ? Role.ADMIN : Role.USER);
            newUser.setRoles(roles);
            return userRepo.save(newUser);
        }
        else{
            throw new CustomException("ONLY ADMINS CAN ADD NEW USER....", HttpStatus.UNAUTHORIZED);
        }
    }
    public List<UserResponse> findAll() throws CustomException {
        User authUser = returnUserAuth();
        if(authUser.isAdmin())
        {
            List<User> users = userRepo.findAll();
            return UserResponse.ConvertToUserResponse(users);
        }
        else {
            throw  new CustomException("ONLY ADMINS CAN DO THAT..", HttpStatus.UNAUTHORIZED);
        }
    }
    public UserWapper getByUsername(String username) throws CustomException{
        try {
            User user = userRepo.findByUserName(username);
            UserResponse userResponse = UserResponse.ConvertToUserResponse(user);
            UserWapper userWapper = new UserWapper(user,userResponse);
            return userWapper;
        }catch (NullPointerException ex){
            throw new CustomException("USER IN NOT FOUND... ",HttpStatus.NOT_FOUND);
        }

    }

    public User getById(Long id){
        return userRepo.findById(id).get();
    }

}
