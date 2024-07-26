package com.github.esgoet.hhjava243todobackend.service;

import com.github.esgoet.hhjava243todobackend.model.NewToDoDto;
import com.github.esgoet.hhjava243todobackend.model.Status;
import com.github.esgoet.hhjava243todobackend.model.ToDo;
import com.github.esgoet.hhjava243todobackend.repository.ToDoRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ToDoService {
    private final ToDoRepo repository;
    private final IdService idService;
    private final OpenAiService openAiService;



    public List<ToDo> findToDos() {
        return repository.findAll();
    }

    public ToDo findToDo(String id) {
        return repository.findById(id).orElseThrow(() -> new NoSuchElementException("No ToDo item with id " + id + " present."));
    }

    public ToDo saveToDo(NewToDoDto toDo) {
        String revisedDesc = openAiService.reviseInput(toDo.description());
        ToDo tobeSaved = new ToDo(idService.generateId(), revisedDesc, Status.OPEN);
        return repository.save(tobeSaved);
    }

    public ToDo updateToDo(String id, ToDo updatedToDo) {
        if (repository.findById(id).isPresent()) {
            return repository.save(updatedToDo);
        } else {
            throw new NoSuchElementException("No ToDo item with id " + id + " present.");
        }
    }

    public void deleteToDo(String id) {
        repository.deleteById(id);
    }
}
