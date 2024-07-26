package com.github.esgoet.hhjava243todobackend.controller;

import com.github.esgoet.hhjava243todobackend.model.Status;
import com.github.esgoet.hhjava243todobackend.model.ToDo;
import com.github.esgoet.hhjava243todobackend.repository.ToDoRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ToDoIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ToDoRepo toDoRepo;

    @Test
    void getToDosTest() throws Exception {
        //WHEN
        mockMvc.perform(get("/api/todo"))
                //THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""
                    []
                    """));
    }

    @Test
    @DirtiesContext
    void getToDoTest_whenToDoExist_thenReturnToDo() throws Exception {
        //GIVEN
        toDoRepo.save(new ToDo("1","Clean", Status.OPEN));

        //WHEN
        mockMvc.perform(get("/api/todo/1"))
                //THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""
                    {
                      "id":"1",
                      "description":"Clean",
                      "status":"OPEN"
                    }
                    """));
    }

    @Test
    void getToDoTest_whenNoToDoExist_thenReturnNull() throws Exception {
        //WHEN
        mockMvc.perform(get("/api/todo/1"))
                //THEN
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    @DirtiesContext
    void saveToDoTest() throws Exception {
        //WHEN
        mockMvc.perform(post("/api/todo")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "description": "Tidy Up"
                    }
                    """))
                //THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""
                    {
                      "description": "Tidy Up",
                      "status": "OPEN"
                    }
                    """))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void updateToDoTest_whenToDoExists() throws Exception {
        //GIVEN
        toDoRepo.save(new ToDo("1","Clean", Status.OPEN));

        //WHEN
        mockMvc.perform(put("/api/todo/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "id": "1",
                      "description": "Clean",
                      "status": "IN_PROGRESS"
                    }
                    """))
                //THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""
                    {
                      "id": "1",
                      "description": "Clean",
                      "status": "IN_PROGRESS"
                    }
                    """));
    }

    @Test
    void updateToDoTest_whenNoToDoExists() throws Exception {
        //WHEN
        mockMvc.perform(put("/api/todo/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                      "id": "1",
                      "description": "Clean",
                      "status": "IN_PROGRESS"
                    }
                    """))
                //THEN
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    void deleteToDo() throws Exception {
        //GIVEN
        toDoRepo.save(new ToDo("1","Clean", Status.OPEN));

        //WHEN
        mockMvc.perform(delete("/api/todo/1"))
                //THEN
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/todo/1"))
                //THEN
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }
}