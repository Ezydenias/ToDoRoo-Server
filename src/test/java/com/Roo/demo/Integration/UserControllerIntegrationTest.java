package com.Roo.demo.Integration;


import com.Roo.demo.controller.TodoController;
import com.Roo.demo.controller.UserController;
import com.Roo.demo.dto.TodoDto;
import com.Roo.demo.models.Todo;
import com.Roo.demo.models.User;
import com.Roo.demo.service.JWTService;
import com.Roo.demo.service.ToDoService;
import com.Roo.demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    // bugged and doesn't work, fix doesn't seem to be out yet
    @MockitoBean
    private UserService userService;

    @MockitoBean
    private JWTService jwtService;

    @MockitoBean
    private ModelMapper modelMapper;

    private List<TodoDto> todos;

    private Todo todo;

    @BeforeEach
    void beforeEach() {

    }

    @Test
    void loginpage() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/login").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("login.html"));
    }

    @Test
    void loginuser() throws Exception {
        var user = new User(1,"username","password","email");
        
        this.mockMvc.perform(MockMvcRequestBuilders.post("/login").with(csrf()).param("User", String.valueOf(user)))
                .andExpect(status().isFound());

        verify(userService, times(1)).verify(any());
//        verify(userService, times(1)).verify(user);

    }

}
