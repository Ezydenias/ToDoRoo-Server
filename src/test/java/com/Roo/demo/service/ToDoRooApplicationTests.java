package com.Roo.demo.service;

import com.Roo.demo.SecurityContextFactory;
import com.Roo.demo.dto.TodoDto;
import com.Roo.demo.models.Todo;
import com.Roo.demo.models.User;
import com.Roo.demo.repo.TodoRepo;
import jakarta.annotation.Resource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ToDoRooApplicationTests {

    private static final String TEST_USER = "TEST_USER";

    @Mock
    private TodoRepo resource;

    @Mock
    private AuthentificationService authentificationService;

    @InjectMocks
    @Resource
    private ToDoService testedClass;

    @Spy
    private ModelMapper modelMapperMock;

//    private ModelMapper modelMapper;

	@BeforeEach
    public void setUp() throws Exception {
        User user = new User();
        user.setUsername("Larry");
        // todos
        var todos = new ArrayList<Todo>();
        todos.add(new Todo(1, "1", true, new Date(2020, 2, 2), user));

        // Initialize mocks created above
        MockitoAnnotations.initMocks(this);

//        when(resource.getMaxId()).thenReturn(12);
        when(resource.findByUserId(1)).thenReturn(todos);
        when(authentificationService.getCurrentUserId()).thenReturn(1);
	}

    @Test
    void contextLoads() {
 

        List<TodoDto> temp = testedClass.loadTodos();

        Assert.assertNotNull(testedClass.loadTodos());
        Assert.assertTrue( temp.get(0).isDone());
        Assert.assertEquals( "1", temp.get(0).getTodo());
    }

}
