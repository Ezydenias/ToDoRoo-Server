package com.Roo.demo.controller;

import com.Roo.demo.models.Todo;
import com.Roo.demo.service.ToDoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@RestController
public class TodoController {

    @Autowired
    private ToDoService service;

    Logger logger = LoggerFactory.getLogger(UserController.class);

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

    @PutMapping("/toggleTodo")
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

    @DeleteMapping("/deleteTodo")
    public ModelAndView deleteTodo(@RequestParam(name = "todoId") String todo, Model model) {
        service.delete(Long.parseLong(todo));
        return todo(model);
    }

    @PostMapping("/createTodo")
    public ModelAndView createTodo(Model model) {
        var view = todo(model);
        model.addAttribute("edit", true);
        model.addAttribute("editedTodo", new Todo());
        return view;
    }

    @PostMapping("/saveTodo")
    public ModelAndView saveTodo(@RequestParam(name = "todo") String todo, @RequestParam(name = "todoId") String todoId, @RequestParam(name = "deadline") String deadline, Model model) {
        var todoItem = new Todo();
        todoItem.setTodo(todo);
        SetDeadline(deadline, todoItem);
        todoItem.setDone(false);
        todoItem.setId(Integer.parseInt(todoId));
        service.save(todoItem);
        return todo(model);
    }

    private void SetDeadline(String deadline, Todo todoItem) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try{
        todoItem.setDeadline(formatter.parse(deadline));}
        catch (ParseException parse) {
        logger.error("Failed to Parse Date for Todo");
        logger.error(parse.getMessage());
        }
    }
}
