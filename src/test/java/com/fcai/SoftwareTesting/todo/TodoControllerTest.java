package com.fcai.SoftwareTesting.todo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class TodoControllerTest {

    @Mock
    private TodoService todoService;

    @InjectMocks
    private TodoController todoController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateTodo() {
        TodoCreateRequest todoCreateRequest = new TodoCreateRequest("Title", "Description");
        Todo createdTodo = new Todo("1", "Title", "Description", false);

        when(todoService.create(todoCreateRequest)).thenReturn(createdTodo);

        ResponseEntity<Todo> responseEntity = todoController.create(todoCreateRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(createdTodo, responseEntity.getBody());
    }

    @Test
    public void testCreateTodo_emptyConstructor() {

        TodoCreateRequest todoCreateRequest = new TodoCreateRequest("", "");

        ResponseEntity<Todo> responseEntity = todoController.create(todoCreateRequest);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
/*
this test shows us that the controller is letting todo to be null and that is not desirable .
 */

    }
    @Test
    public void testCreateTodo_MaxConstructor() {
        String maxTitle = "A".repeat(200000000);
        String maxDescription = "B".repeat(200000000);
        TodoCreateRequest todoCreateRequest = new TodoCreateRequest(maxTitle, maxDescription);

        ResponseEntity<Todo> responseEntity = todoController.create(todoCreateRequest);

        todoCreateRequest = new TodoCreateRequest(maxTitle, maxDescription);

        responseEntity = todoController.create(todoCreateRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }







    @Test
    public void testReadTodo() {
        String id = "1";
        Todo todo = new Todo("1", "Title", "Description", false);

        when(todoService.read(id)).thenReturn(todo);

        ResponseEntity<Todo> responseEntity = todoController.read(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(todo, responseEntity.getBody());



    }
    @Test
    public void testReadTodo_EmptyId() {

        String emptyId = "";

        ResponseEntity<Todo> responseEntity = todoController.read(emptyId);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        //this test shows us that the controller is letting todo to be null and that is not desirable .
    }

    @Test
    public void testReadTodo_MaxLengthId() {

        String maxId = "A".repeat(2000000000);


        Todo todo = new Todo(maxId, "Title", "Description", false);
        when(todoService.read(maxId)).thenReturn(todo);

        ResponseEntity<Todo> responseEntity = todoController.read(maxId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(todo, responseEntity.getBody());
    }









    @Test
    public void testUpdateTodo() {
        String id = "1";
        boolean completed = true;
        Todo updatedTodo = new Todo("1", "Title", "Description", true);

        when(todoService.update(id, completed)).thenReturn(updatedTodo);

        ResponseEntity<Todo> responseEntity = todoController.update(id, completed);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedTodo, responseEntity.getBody());
    }
    @Test
    public void testUpdateTodo_NullValues() {

        String emptyId = "";
        boolean completed = true;

        ResponseEntity<Todo> responseEntity = todoController.update(emptyId, completed);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
       // this test shows us that the controller is letting todo to be null and that is not desirable .
    }

    @Test
    public void testUpdateTodo_MaxLength() {

        String maxId = "A".repeat(255);
        boolean completed = true;


        Todo updatedTodo = new Todo(maxId, "Title", "Description", completed);
        when(todoService.update(maxId, completed)).thenReturn(updatedTodo);

        ResponseEntity<Todo> responseEntity = todoController.update(maxId, completed);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedTodo, responseEntity.getBody());
    }







    @Test
    public void testDeleteTodo() {
        String id = "1";

        ResponseEntity<?> responseEntity = todoController.delete(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(todoService, times(1)).delete(id);
    }


    @Test
    public void testDeleteTodo_NullId() {
        // Test with null ID
        String nullId = null;

        ResponseEntity<?> responseEntity = todoController.delete(nullId);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        // this test shows us that the controller is letting todo to be null and that is not desirable
    }



    @Test
    public void testListTodos() {
        List<Todo> todos = new ArrayList<>();
        todos.add(new Todo("1", "Title 1", "Description 1", false));
        todos.add(new Todo("2", "Title 2", "Description 2", false));

        when(todoService.list()).thenReturn(todos);

        ResponseEntity<List<Todo>> responseEntity = todoController.list();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(todos, responseEntity.getBody());
    }
    @Test
    public void testListTodos_EmptyList() {
        List<Todo> emptyList = new ArrayList<>();

        // Simulate service layer returning an empty list
        when(todoService.list()).thenReturn(emptyList);

        ResponseEntity<List<Todo>> responseEntity = todoController.list();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().isEmpty());
    }






    @Test
    public void testListCompletedTodos() {
        List<Todo> completedTodos = new ArrayList<>();
        completedTodos.add(new Todo("1", "Title 1", "Description 1", true));
        completedTodos.add(new Todo("2", "Title 2", "Description 2", true));

        when(todoService.listCompleted()).thenReturn(completedTodos);

        ResponseEntity<List<Todo>> responseEntity = todoController.listCompleted();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(completedTodos, responseEntity.getBody());
    }
}
