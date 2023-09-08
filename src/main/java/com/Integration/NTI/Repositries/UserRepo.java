package com.Integration.NTI.Repositries;

import com.Integration.NTI.Models.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepo extends JpaRepository<User,Long> {

    User findByUserName(String userName);
    @Query("SELECT COUNT(u) FROM User u WHERE u.userName = ?1")
    long countByUsername(String username);

}
