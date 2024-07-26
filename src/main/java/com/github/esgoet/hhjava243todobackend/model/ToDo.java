package com.github.esgoet.hhjava243todobackend.model;

import lombok.With;
import org.springframework.data.mongodb.core.mapping.Document;

@With
@Document("todo")
public record ToDo(
        String description,
        String id,
        Status status
) {
}
