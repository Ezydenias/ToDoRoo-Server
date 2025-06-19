package com.Roo.demo.Integration;


import com.Roo.demo.controller.TodoController;
import com.Roo.demo.dto.TodoDto;
import com.Roo.demo.models.Todo;
import com.Roo.demo.models.User;
import com.Roo.demo.service.JWTService;
import com.Roo.demo.service.ToDoService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TodoController.class)
public class TodoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ToDoService toDoService;

    @MockitoBean
    private JWTService jwtService;

    private List<TodoDto> todos;

    private Todo todo;

    @BeforeEach
    void beforeEach() {
        todo = new Todo(1, "test", true, Date.valueOf(LocalDate.of(2025, 06, 06)), null);
        TodoDto todoDto = new TodoDto(1, "test", true, Date.valueOf(LocalDate.of(2025, 06, 06)));
        TodoDto todoDto2 = new TodoDto(2, "test2", false, Date.valueOf(LocalDate.of(2025, 01, 01)));
        todos = new ArrayList<>();
        todos.add(todoDto);
        todos.add(todoDto2);

        long todoToggled = 1;
        toDoService.toggleTodo(1);

        when(toDoService.loadTodos()).thenReturn(todos);
        when(toDoService.loadTodo(1)).thenReturn(todo);

        doAnswer(invocation -> {
            todos.get(0).setDone(!todos.get(0).isDone());
            return null;
        }).when(toDoService).toggleTodo(1);
    }

    @Test
    @WithMockUser
    void showDefaultPage() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("todo.html"))
                .andExpect(model().attribute("todos", hasSize(2)))
                .andExpect(model().attribute("todos", hasItem(
                        allOf(
                                hasProperty("todo", is(todos.get(1).getTodo())),
                                hasProperty("id", is(todos.get(1).getId())),
                                hasProperty("deadline", is(todos.get(1).getDeadline())),
                                hasProperty("done", is(todos.get(1).isDone()))
                        )
                )))
                .andExpect(model().attribute("todos", hasItem(
                        allOf(
                                hasProperty("todo", is(todos.get(0).getTodo())),
                                hasProperty("id", is(todos.get(0).getId())),
                                hasProperty("deadline", is(todos.get(0).getDeadline())),
                                hasProperty("done", is(todos.get(0).isDone()))
                        )
                )));
    }

    @Test
    @WithMockUser
    void showTodoPage() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/todo"))
                .andExpect(status().isOk())
                .andExpect(view().name("todo.html"))
                .andExpect(model().attribute("todos", hasSize(2)))
                .andExpect(model().attribute("todos", hasItem(
                        allOf(
                                hasProperty("todo", is(todos.get(1).getTodo())),
                                hasProperty("id", is(todos.get(1).getId())),
                                hasProperty("deadline", is(todos.get(1).getDeadline())),
                                hasProperty("done", is(todos.get(1).isDone()))
                        )
                )))
                .andExpect(model().attribute("todos", hasItem(
                        allOf(
                                hasProperty("todo", is(todos.get(0).getTodo())),
                                hasProperty("id", is(todos.get(0).getId())),
                                hasProperty("deadline", is(todos.get(0).getDeadline())),
                                hasProperty("done", is(todos.get(0).isDone()))
                        )
                )));
    }

    @Test
    @WithMockUser
    void toggleTodo() throws Exception {

        boolean beforeToggle = todos.get(0).isDone();

        this.mockMvc.perform(MockMvcRequestBuilders.put("/toggleTodo").with(csrf()).param("todoId", String.valueOf(1)))
                .andExpect(status().isOk())
                .andExpect(view().name("todo.html"))
                .andExpect(model().attribute("todos", hasItem(
                        allOf(
                                hasProperty("todo", is(todos.get(1).getTodo())),
                                hasProperty("id", is(todos.get(1).getId())),
                                hasProperty("deadline", is(todos.get(1).getDeadline())),
                                hasProperty("done", is(todos.get(1).isDone()))
                        )
                )))
                .andExpect(model().attribute("todos", hasItem(
                        allOf(
                                hasProperty("todo", is(todos.get(0).getTodo())),
                                hasProperty("id", is(todos.get(0).getId())),
                                hasProperty("deadline", is(todos.get(0).getDeadline())),
                                hasProperty("done", is(!beforeToggle))
                        )
                )));
    }

    @Test
    @WithMockUser
    void getTodoElement() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/todoelement").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("todoelement"));
    }

    @Test
    @WithMockUser
    void edit() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.post("/editTodo").with(csrf()).param("todoId", String.valueOf(1)))
                .andExpect(status().isOk())
                .andExpect(view().name("todo.html"))
                .andExpect(model().attribute("edit", true))
                .andExpect(model().attribute("editedTodo", is(todo)));
    }

    @Test
    @WithMockUser
    void delete() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/deleteTodo").with(csrf()).param("todoId", String.valueOf(1)))
                .andExpect(status().isOk())
                .andExpect(view().name("todo.html"));

        verify(toDoService, times(1)).delete(1);
    }

    @Test
    @WithMockUser
    void create() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/createTodo").with(csrf()).param("todoId", String.valueOf(1)))
                .andExpect(status().isOk())
                .andExpect(view().name("todo.html"))
                .andExpect(model().attribute("edit", true))
                .andExpect(model().attribute("editedTodo", is(new TodoDto())));
    }

    @Test
    @WithMockUser
    void save() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/saveTodo").with(csrf())
                        .param("todo", String.valueOf("new todo"))
                        .param("todoId", String.valueOf(0))
                        .param("deadline", String.valueOf("2025-05-05")))
                .andExpect(status().isOk())
                .andExpect(view().name("todo.html"));

        var temp2 = LocalDate.of(2025, 05, 05);
        var temp = new Todo(0, "new todo", false, Date.valueOf(LocalDate.of(2025, 05, 05)), null);
        
        verify(toDoService, times(1)).save(new Todo(0, "new todo", false, Date.valueOf(LocalDate.of(2025, 05, 05)), null));
    }

}
