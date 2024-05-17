package com.fcai.SoftwareTesting.todo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TodoCreateRequestTest {
    TodoCreateRequest createRequest = new TodoCreateRequest("title","description");

    @Test
    void constructorWithParameters() {

        TodoCreateRequest request = new TodoCreateRequest("Title1", "Description1");


        assertEquals("Title1", request.getTitle());
        assertEquals("Description1", request.getDescription());
    }

    @Test
    void defaultConstructor() {

        TodoCreateRequest request = new TodoCreateRequest();

        assertNull(request.getTitle());
        assertNull(request.getDescription());
    }



    @Test
    void getTitle() {
        assertEquals("title",createRequest.getTitle());
    }

    @Test
    void getDescription() {
        assertEquals("description",createRequest.getDescription());
    }

    @Test
    void setTitle() {
        createRequest.setTitle("fady");
        assertEquals("fady",createRequest.getTitle());
    }

    @Test
    void setDescription() {
        createRequest.setDescription("fady");
        assertEquals("fady",createRequest.getDescription());
    }
}