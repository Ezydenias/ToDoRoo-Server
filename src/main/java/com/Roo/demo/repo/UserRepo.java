package com.Roo.demo.repo;

import com.Roo.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    
    User findByUsername(String username);

    @Query(value = "SELECT MAX(id) FROM user", nativeQuery = true)
    int getMaxId();
}
