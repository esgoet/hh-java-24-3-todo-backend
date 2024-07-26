package com.github.esgoet.hhjava243todobackend.model;

import java.util.List;

public record OpenAiResponse(
        List<OpenAiChoice> choices
) {
    public String getAnswer() {
        return choices.getFirst().message().content();
    }
}
