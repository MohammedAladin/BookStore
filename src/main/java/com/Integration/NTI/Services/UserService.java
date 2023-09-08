package com.Integration.NTI.Services;

import com.Integration.NTI.Exception.CustomException;
import com.Integration.NTI.Models.Entities.Role;
import com.Integration.NTI.Models.Entities.User;
import com.Integration.NTI.Repositries.UserRepo;
import com.Integration.NTI.Models.Requests.SignUpRequset;
import com.Integration.NTI.Models.Response.UserResponse;
import com.Integration.NTI.Templates.UserWapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Component()
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthService authService;

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
    public List<UserResponse> findAll() throws CustomException {
        User authUser = authService.returnUserAuth();
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
