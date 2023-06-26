package com.Integration.NTI.Repositries;

import com.Integration.NTI.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {

    User findByUserName(String userName);

}
