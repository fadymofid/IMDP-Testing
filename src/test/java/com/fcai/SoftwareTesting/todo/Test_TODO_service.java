package com.fcai.SoftwareTesting.todo;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class Test_TODO_service {
    private TodoService todoService;

    @Before
    public void setUp() {
        todoService = new TodoServiceImpl();
    }

    @Test
    public void createTodo() {

        TodoCreateRequest todoCreateRequest = new TodoCreateRequest("Title", "Description");

        Todo newTodo = todoService.create(todoCreateRequest);

        assertEquals(1, todoService.list().size());

        assertEquals("Title", newTodo.getTitle());

        assertEquals("Description", newTodo.getDescription());

        assertFalse(newTodo.isCompleted());
    }
    @Test
    public void createTodo_MaximumLengthDescription() {
        String longDescription = "a".repeat(1024);
        TodoCreateRequest todoCreateRequest = new TodoCreateRequest("Title", longDescription);
        Todo newTodo = todoService.create(todoCreateRequest);
        assertNotNull(newTodo);
        assertEquals(longDescription, newTodo.getDescription());
    }

    @Test
    public void createTodo_MaximumLengthTitle() {
        String longTitle = "a".repeat(1024);
        TodoCreateRequest todoCreateRequest = new TodoCreateRequest(longTitle, "Description");
        Todo newTodo = todoService.create(todoCreateRequest);
        assertNotNull(newTodo);
        assertEquals(longTitle, newTodo.getTitle());
    }




    @Test
    public void readTodo() {

        TodoCreateRequest todoCreateRequest = new TodoCreateRequest("Title", "Description");

        Todo createdTodo = todoService.create(todoCreateRequest);


        Todo readTodo = todoService.read(createdTodo.getId());


        assertNotNull(readTodo);

        assertEquals(createdTodo.getId(), readTodo.getId());

        assertEquals("Title", readTodo.getTitle());

        assertEquals("Description", readTodo.getDescription());

        assertFalse(readTodo.isCompleted());
    }
    @Test
    public void updateTodo() {

        TodoCreateRequest todoCreateRequest = new TodoCreateRequest("Title", "Description");

        Todo createdTodo = todoService.create(todoCreateRequest);

        Todo updatedTodo = todoService.update(createdTodo.getId(), true);

        assertNotNull(updatedTodo);

        assertEquals(createdTodo.getId(), updatedTodo.getId());

        assertTrue(updatedTodo.isCompleted());
    }
    @Test
    public void deleteTodo() {

        TodoCreateRequest todoCreateRequest = new TodoCreateRequest("Title", "Description");

        Todo createdTodo = todoService.create(todoCreateRequest);


        todoService.delete(createdTodo.getId());


        assertEquals(0, todoService.list().size());
    }

    @Test
    public void listTodos() {

        todoService.create(new TodoCreateRequest("Title 1", "Description 1"));
        todoService.create(new TodoCreateRequest("Title 2", "Description 2"));

        List<Todo> todos = todoService.list();

        assertNotNull(todos);

        assertEquals(2, todos.size());
        for (int i = 0; i < todos.size(); i++) {
            Todo todo = todos.get(i);
            assertEquals("Title " + (i + 1), todo.getTitle());
            assertEquals("Description " + (i + 1), todo.getDescription());
            assertFalse(todo.isCompleted());
        }
    }

    @Test
    public void listCompletedTodos() {

        todoService.create(new TodoCreateRequest("Title 1", "Description 1"));
        todoService.create(new TodoCreateRequest("Title 2", "Description 2"));
        Todo completedTodo = todoService.create(new TodoCreateRequest("Title 3", "Description 3"));

        todoService.update(completedTodo.getId(), true);

        List<Todo> completedTodos = todoService.listCompleted();

        assertNotNull(completedTodos);

        assertEquals(1, completedTodos.size());
    }
}
