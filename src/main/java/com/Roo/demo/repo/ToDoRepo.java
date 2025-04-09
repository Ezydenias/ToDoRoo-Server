package com.Roo.demo.repo;

import com.Roo.demo.models.Roo2Example;
import com.Roo.demo.models.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoRepo extends JpaRepository<ToDo, Long> {
}
