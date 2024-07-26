package com.github.esgoet.hhjava243todobackend.service;

import com.github.esgoet.hhjava243todobackend.model.NewToDoDto;
import com.github.esgoet.hhjava243todobackend.model.Status;
import com.github.esgoet.hhjava243todobackend.model.ToDo;
import com.github.esgoet.hhjava243todobackend.repository.ToDoRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ToDoService {
    private final ToDoRepo repository;
    private final IdService idService;

    public List<ToDo> findToDos() {
        return repository.findAll();
    }

    public ToDo findToDo(String id) {
        return repository.findById(id).orElse(null);
    }

    public ToDo saveToDo(NewToDoDto toDo) {
        ToDo tobeSaved = new ToDo(idService.generateId(), toDo.description(), Status.OPEN);
        return  repository.save(tobeSaved);
    }

    public ToDo updateToDo(String id, ToDo updatedToDo) {
        if (findToDo(id) != null) {
            return repository.save(updatedToDo);
        } else {
            return null;
        }
    }

    public void deleteToDo(String id) {
        repository.deleteById(id);
    }
}
