package com.Integration.NTI.Services;

import com.Integration.NTI.Models.Book;
import com.Integration.NTI.Models.Role;
import com.Integration.NTI.Models.User;
import com.Integration.NTI.Repositries.BookRepo;
import com.Integration.NTI.Repositries.UserRepo;
import com.Integration.NTI.Requests.CreateUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
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
    public User getByUsername(String username){
        return userRepo.findByUserName(username);
    }
    public User returnUserAuth(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = ((UserDetails) authentication.getPrincipal()).getUsername();
        return getByUsername(userName);

    }

    public List<User> findAll(){
        return userRepo.findAll();
    }

    public User addUser(CreateUserRequest userRequest){
        User newUser = new User();

        newUser.setUserName(userRequest.getUsername());
        newUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        newUser.setAdmin(userRequest.isAdmin());
        System.out.println(newUser.isAdmin());

        Set<Role> roles = new HashSet<>();
        roles.add(newUser.isAdmin()? Role.ADMIN : Role.USER);
        newUser.setRoles(roles);
        return userRepo.save(newUser);
    }
    public User getById(Long id){
        return userRepo.findById(id).get();
    }

}
