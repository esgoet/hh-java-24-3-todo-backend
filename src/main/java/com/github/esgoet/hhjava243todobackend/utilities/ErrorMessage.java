package com.github.esgoet.hhjava243todobackend.utilities;

import java.time.LocalDateTime;

public record ErrorMessage(
        String message,
        LocalDateTime timestamp,
        int statusCode
) {
}
