package com.Roo.demo.service;

import com.Roo.demo.dto.TodoDto;
import com.Roo.demo.models.Todo;
import com.Roo.demo.models.UserPrincipal;
import com.Roo.demo.repo.TodoRepo;
import com.Roo.demo.repo.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ToDoService {

    @Autowired
    private TodoRepo repo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    public List<TodoDto> loadTodos() {
        return convertToDto(repo.findByUserId(getCurrentUserId()));
    }

    private List<TodoDto> convertToDto(List<Todo> todos) {
        List<TodoDto> todoDtos = new ArrayList<>();

        for (int i = 0; i < todos.stream().count(); i++) {
            var temp = todos.get(i);
            var temp2 = modelMapper.map(todos.get(i), TodoDto.class);
            todoDtos.add(modelMapper.map(todos.get(i), TodoDto.class));

        }
        
        return todoDtos;
    }

    public void toggleTodo(long todo) {
        Todo founndTodo = repo.findById(todo).get();
        if (founndTodo != null) {
            founndTodo.setDone(!founndTodo.isDone());
            repo.save(founndTodo);
        }
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

    public void delete(long todo) {
        Todo founndTodo = repo.findById(todo).get();
        if (founndTodo != null && founndTodo.isDone())
            repo.delete(founndTodo);
    }
}
