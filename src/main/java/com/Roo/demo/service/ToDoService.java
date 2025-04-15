package com.Roo.demo.service;

import com.Roo.demo.models.Todo;
import com.Roo.demo.models.UserPrincipal;
import com.Roo.demo.repo.TodoRepo;
import com.Roo.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ToDoService {

    @Autowired
    private TodoRepo repo;

    @Autowired
    private UserRepo userRepo;

    public List<Todo> loadTodos() {
        return repo.findByUserId(getCurrentUserId());
    }

    public void toggleTodo(long todo) {
        Todo founndTodo = repo.findById(todo).get();
        founndTodo.setDone(!founndTodo.isDone());
        repo.save(founndTodo);
    }

    public void save(Todo todo) {
        if (todo.getId() == 0)
            todo.setId(repo.getMaxId() + 1);
        todo.setUser(userRepo.getReferenceById(getCurrentUserId()));
        repo.save(todo);
    }

    private int getCurrentUserId() {
        return ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }

    public Todo loadTodo(int todo) {
        return repo.findById(Long.valueOf(todo)).get();
    }
}
