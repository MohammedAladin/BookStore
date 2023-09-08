package com.Integration.NTI;

import com.Integration.NTI.Models.Entities.User;
import com.Integration.NTI.Repositries.UserRepo;
import com.Integration.NTI.Services.UserService;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class FirstTimeInitializer implements CommandLineRunner {

    private final Log logger = LogFactory.getLog(FirstTimeInitializer.class);
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner myCommandLineRunner() {
        return new FirstTimeInitializer();
    }
    @Override
    public void run(String... args) throws Exception {
        if(userRepo.findAll().isEmpty())
        {

            logger.info("No users are found");
            User user = new User();
            user.setUserName("Admin.com");
            user.setPassword("123456");
            user.setPassword(passwordEncoder.encode("123456"));
            user.setAdmin(true);// Encode the password
            userRepo.save(user);

        }
    }
}