package com.Roo.demo.controller;

import com.Roo.demo.models.Todo;
import com.Roo.demo.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class TodoController {

    @Autowired
    private ToDoService service;

    @GetMapping("/")
    public ModelAndView greet(Model model) {
        return todo(model);
    }

    @GetMapping("/todo")
    public ModelAndView todo(Model model) {
        model.addAttribute("todos", service.loadTodos());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("todo.html");
        return modelAndView;
    }

    // Handler to Handler fragment expression
    @GetMapping("/todoelement")
    public String fragmentExpression() {
        return "todoelement";
    }

    @PostMapping("/toggleTodo")
    public ModelAndView toggleTodo(@RequestParam(name = "todoId") String todo, Model model) {
        service.toggleTodo(Integer.parseInt(todo));
        return todo(model);
    }

    @PostMapping("/editTodo")
    public ModelAndView editTodo(@RequestParam(name = "todoId") String todo, Model model) {
        var view = todo(model);
        model.addAttribute("edit", true);
        model.addAttribute("editedTodo", service.loadTodo(Integer.parseInt(todo)));
        return view;
    }

    @PostMapping("/createTodo")
    public ModelAndView createTodo(Model model) {
        var view = todo(model);
        model.addAttribute("edit", true);
        model.addAttribute("editedTodo", new Todo());
        return view;
    }

    @PostMapping("/saveTodo")
    public ModelAndView saveTodo(@RequestParam(name = "todo") String todo, @RequestParam(name = "todoId") String todoId, Model model) {
        var todoItem = new Todo();
        todoItem.setTodo(todo);
        todoItem.setDone(false);
        todoItem.setId(Integer.parseInt(todoId));
        service.save(todoItem);
        return todo(model);
    }
}
