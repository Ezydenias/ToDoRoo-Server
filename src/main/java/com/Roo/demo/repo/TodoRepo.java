package com.Roo.demo.repo;

import com.Roo.demo.models.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TodoRepo extends JpaRepository<Todo, Long> {

    @Query(value = "select * from todo as t where t.user_id = :user_id", nativeQuery = true)
    List<Todo> findByUserId(int user_id);

    @Query(value = "SELECT MAX(id) FROM todo", nativeQuery = true)
    int getMaxId();
}
