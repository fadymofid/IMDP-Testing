package com.fcai.SoftwareTesting.todo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TodoTest {
    Todo todo = new Todo("1", "Title", "Description", false);

    @Test
    void constructorWithParameters() {

        Todo todo1 = new Todo("2", "Title 1", "Description 1", false);

        assertEquals("2", todo1.getId());
        assertEquals("Title 1", todo1.getTitle());
        assertEquals("Description 1", todo1.getDescription());
        assertFalse(todo1.isCompleted());
    }

    @Test
    void defaultConstructor() {
        // Create a Todo instance using the default constructor
        Todo todo1 = new Todo();

        // Verify that the fields are initialized to default values
        assertNull(todo1.getId());
        assertNull(todo1.getTitle());
        assertNull(todo1.getDescription());
        assertFalse(todo1.isCompleted());
    }



    @Test
    void getId() {
        assertEquals("1",todo.getId());
    }

    @Test
    void getTitle() {
        assertEquals("Title",todo.getTitle());
    }

    @Test
    void getDescription() {
        assertEquals("Description",todo.getDescription());
    }

    @Test
    void isCompleted() {
        assertFalse(todo.isCompleted());
    }

    @Test
    void setId() {
        todo.setId("3");
        assertEquals("3",todo.getId());
    }

    @Test
    void setTitle() {
        todo.setTitle("fady");
        assertEquals("fady",todo.getTitle());
    }

    @Test
    void setDescription() {
        todo.setDescription("fady");
        assertEquals("fady",todo.getDescription());
    }

    @Test
    void setCompleted() {
        todo.setCompleted(true);
        assertTrue(todo.isCompleted());
    }
}