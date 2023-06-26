package com.Integration.NTI.Services;

import com.Integration.NTI.Models.Book;
import com.Integration.NTI.Models.User;
import com.Integration.NTI.Repositries.BookRepo;
import com.Integration.NTI.Repositries.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;
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
    public List<User> findAll(){
        return userRepo.findAll();
    }
    public User addUser(User user){
         return userRepo.save(user);
    }
    public User getById(Long id){
        return userRepo.findById(id).get();
    }

}
