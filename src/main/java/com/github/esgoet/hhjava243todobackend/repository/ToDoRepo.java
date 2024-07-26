package com.github.esgoet.hhjava243todobackend.repository;

import com.github.esgoet.hhjava243todobackend.model.ToDo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ToDoRepo extends MongoRepository<ToDo, String> {
}
