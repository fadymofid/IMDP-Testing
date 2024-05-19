package com.fcai.SoftwareTesting.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@WebMvcTest(TodoController.class)
public class TodoControllerTest {



        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private TodoService todoService;

        private ObjectMapper objectMapper;

        @BeforeEach
        public void setUp() {
            objectMapper = new ObjectMapper();
        }

        @Test
        public void testCreateTodo() throws Exception {
            TodoCreateRequest request = new TodoCreateRequest("Title", "Description");
            Todo createdTodo = new Todo("1", "Title", "Description", false);


            when(todoService.create(any(TodoCreateRequest.class))).thenReturn(createdTodo);

            String inputJson = objectMapper.writeValueAsString(request);
            MvcResult mvcResult = mockMvc.perform(post("/todo/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(inputJson))
                    .andExpect(status().isOk())
                    .andReturn();

            String responseJson = mvcResult.getResponse().getContentAsString();
            Todo responseTodo = objectMapper.readValue(responseJson, Todo.class);
            assertEquals(createdTodo.getId(), responseTodo.getId());
            assertEquals(createdTodo.getTitle(), responseTodo.getTitle());
            assertEquals(createdTodo.getDescription(), responseTodo.getDescription());
            assertEquals(createdTodo.isCompleted(), responseTodo.isCompleted());
        }
    @Test
    public void testCreateTodo_InvalidTitle() throws Exception {
        TodoCreateRequest request = new TodoCreateRequest("", "Description");
        Todo createdTodo = new Todo("1", "", "Description", false);


        when(todoService.create(any(TodoCreateRequest.class))).thenReturn(createdTodo);
        String inputJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/todo/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
                .andExpect(status().isBadRequest());
        // this function should return bad request because the title is invalid but returns 200 ok
    }

    @Test
    public void testCreateTodo_InvalidDescription() throws Exception {
        TodoCreateRequest request = new TodoCreateRequest("Title", "");
        Todo createdTodo = new Todo("1", "Title", "", false);


        when(todoService.create(any(TodoCreateRequest.class))).thenReturn(createdTodo);
        String inputJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/todo/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
                .andExpect(status().isBadRequest());
        // this function should return bad request because the description is invalid but returns 200 ok
    }
    @Test
    public void testCreateTodo_EmptyTitle() throws Exception {
        TodoCreateRequest request = new TodoCreateRequest("", "Description");

        mockMvc.perform(post("/todo/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
        // this function should return bad request because the title is empty but returns 200 ok
    }

    @Test
    public void testCreateTodo_EmptyDescription() throws Exception {
        TodoCreateRequest request = new TodoCreateRequest("Title", "");

        mockMvc.perform(post("/todo/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
        // this function should return bad request because the description is empty but returns 200 ok
    }

    @Test
    public void testCreateTodo_NullRequest() throws Exception {
        when(todoService.create(null)).thenThrow(IllegalArgumentException.class);

        mockMvc.perform(post("/todo/create")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }








    @Test
    public void testReadTodo() throws Exception {
        String id = "1";
        Todo todo = new Todo("1", "Title", "Description", false);

        when(todoService.read(id)).thenReturn(todo);

        MvcResult mvcResult = mockMvc.perform(get("/todo/read", id)
                        .param("id", id))

                .andExpect(status().isOk())
                .andReturn();

        String responseJson = mvcResult.getResponse().getContentAsString();
        Todo responseTodo = objectMapper.readValue(responseJson, Todo.class);
        assertEquals(todo.getId(), responseTodo.getId());
        assertEquals(todo.getTitle(), responseTodo.getTitle());
        assertEquals(todo.getDescription(), responseTodo.getDescription());
        assertEquals(todo.isCompleted(), responseTodo.isCompleted());
    }




    @Test
    public void testUpdateTodo() throws Exception {
        String id = "1";
        boolean completed = true;
        Todo updatedTodo = new Todo("1", "Title", "Description", true);

        when(todoService.update(id, completed)).thenReturn(updatedTodo);

        MvcResult mvcResult = mockMvc.perform(put("/todo/update", id)
                        .param("id", id)
                        .param("completed", String.valueOf(completed)))
                .andExpect(status().isOk())
                .andReturn();

        String responseJson = mvcResult.getResponse().getContentAsString();
        Todo responseTodo = objectMapper.readValue(responseJson, Todo.class);
        assertEquals(updatedTodo.getId(), responseTodo.getId());
        assertEquals(updatedTodo.getTitle(), responseTodo.getTitle());
        assertEquals(updatedTodo.getDescription(), responseTodo.getDescription());
        assertEquals(updatedTodo.isCompleted(), responseTodo.isCompleted());
    }

    @Test
    public void testUpdateTodo_InvalidCompleted() throws Exception {
        String id = "1";
        String completed = "invalid_completed";

        mockMvc.perform(put("/todo/update")
                        .param("id", id)
                        .param("completed", completed))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void testUpdateTodo_NullCompleted() throws Exception {
        String id = "1";

        mockMvc.perform(put("/todo/update")
                        .param("id", id))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void testUpdateTodo_NullId() throws Exception {
        String id = null;
        boolean completed = true;

        mockMvc.perform(put("/todo/update")
                        .param("completed", String.valueOf(completed)))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void testUpdateTodo_InvalidId() throws Exception {
        String id = "invalid_id";
        boolean completed = true;

        mockMvc.perform(put("/todo/update")
                        .param("id", id)
                        .param("completed", String.valueOf(completed)))
                .andExpect(status().isBadRequest());
        // this function should return bad request because the id is invalid but returns 200 ok
    }




    @Test
    public void testDeleteTodo() throws Exception {
        String id = "1";

        MvcResult mvcResult = mockMvc.perform(delete("/todo/delete")
                        .param("id", id))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = mvcResult.getResponse().getContentAsString();
        assertTrue(responseContent.isEmpty());
    }
    @Test
    public void testDeleteTodo_NullId() throws Exception {
        mockMvc.perform(delete("/todo/delete"))
                .andExpect(status().isBadRequest());
    }







    @Test
    public void testListTodos() throws Exception {
        List<Todo> todos = new ArrayList<>();
        todos.add(new Todo("1", "Title 1", "Description 1", false));
        todos.add(new Todo("2", "Title 2", "Description 2", false));

        when(todoService.list()).thenReturn(todos);

        MvcResult mvcResult = mockMvc.perform(get("/todo/list"))
                .andExpect(status().isOk())
                .andReturn();

        String responseJson = mvcResult.getResponse().getContentAsString();
        List<Todo> responseTodos = objectMapper.readValue(responseJson, objectMapper.getTypeFactory().constructCollectionType(List.class, Todo.class));
        assertEquals(todos.size(), responseTodos.size());
        assertEquals(todos.get(0).getId(), responseTodos.get(0).getId());
        assertEquals(todos.get(1).getId(), responseTodos.get(1).getId());
    }










    @Test
    public void testListCompletedTodos() throws Exception {
        List<Todo> completedTodos = new ArrayList<>();
        completedTodos.add(new Todo("1", "Title 1", "Description 1", true));
        completedTodos.add(new Todo("2", "Title 2", "Description 2", true));

        when(todoService.listCompleted()).thenReturn(completedTodos);

        MvcResult mvcResult = mockMvc.perform(get("/todo/listCompleted"))
                .andExpect(status().isOk())
                .andReturn();

        String responseJson = mvcResult.getResponse().getContentAsString();
        List<Todo> responseTodos = objectMapper.readValue(responseJson, objectMapper.getTypeFactory().constructCollectionType(List.class, Todo.class));
        assertEquals(completedTodos.size(), responseTodos.size());
        assertEquals(completedTodos.get(0).getId(), responseTodos.get(0).getId());
        assertEquals(completedTodos.get(1).getId(), responseTodos.get(1).getId());
    }
    @Test
    public void testListCompletedTodos_EmptyList() throws Exception {
        when(todoService.listCompleted()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/todo/listCompleted"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }











}

