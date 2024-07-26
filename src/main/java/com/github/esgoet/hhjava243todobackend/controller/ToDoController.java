package com.github.esgoet.hhjava243todobackend.controller;

import com.github.esgoet.hhjava243todobackend.model.ToDo;
import com.github.esgoet.hhjava243todobackend.service.ToDoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todo")
@RequiredArgsConstructor
public class ToDoController {
    private final ToDoService service;

    @GetMapping
    public List<ToDo> getToDos() {
        return service.findToDos();
    }

    @PostMapping
    public ToDo saveToDo(@RequestBody ToDo toDo) {
        return service.saveToDo(toDo);
    }

}
