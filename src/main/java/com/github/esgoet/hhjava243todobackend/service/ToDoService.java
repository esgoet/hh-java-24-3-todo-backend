package com.github.esgoet.hhjava243todobackend.service;

import com.github.esgoet.hhjava243todobackend.model.Status;
import com.github.esgoet.hhjava243todobackend.model.ToDo;
import com.github.esgoet.hhjava243todobackend.repository.ToDoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ToDoService {
    private final ToDoRepository repository;
    private final IdService idService;

    public List<ToDo> findToDos() {
        return repository.findAll();
    }

    public ToDo findToDo(String id) {
        return repository.findById(id).orElse(null);
    }

    public ToDo saveToDo(ToDo toDo) {
        ToDo tobeSaved = toDo.withId(idService.generateId());
        return  repository.save(tobeSaved);
    }

    public ToDo updateToDo(String id) {
        ToDo toBeUpdated = findToDo(id);

        if (toBeUpdated != null) {
            ToDo updated;
            if (toBeUpdated.status() == Status.OPEN) {
                updated = toBeUpdated.withStatus(Status.IN_PROGRESS);
            } else {
                updated = toBeUpdated.withStatus(Status.DONE);
            }
            repository.delete(toBeUpdated);
            repository.save(updated);
            return updated;
        }
        return null;
    }
}
