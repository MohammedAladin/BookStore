package com.Integration.NTI;

import com.Integration.NTI.Models.Role;
import com.Integration.NTI.Models.User;
import com.Integration.NTI.Requests.CreateUserRequest;
import com.Integration.NTI.Services.UserService;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class FirstTimeInitializer implements CommandLineRunner {

    private final Log logger = LogFactory.getLog(FirstTimeInitializer.class);
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) throws Exception {
        if(userService.findAll().isEmpty())
        {

            logger.info("No users are found");
            CreateUserRequest user = new CreateUserRequest();
            user.setUsername("Admin.com");
            user.setPassword("123456");
            user.setAdmin(true);// Encode the password
            userService.addUser(user);

        }
    }
}