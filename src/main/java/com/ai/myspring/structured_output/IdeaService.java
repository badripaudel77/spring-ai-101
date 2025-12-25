package com.ai.myspring.structured_output;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class IdeaService {

    @Qualifier("googleGenAiChatClient")
    private final ChatClient client;

    public IdeaService(ChatClient.Builder builder) {
        this.client = builder.build();
    }

    // structured output : map output to Idea class.
    @Cacheable(value = "idea-generation-questions", key = "#query")
    public Idea getIdea(String query) {
        log.info("Getting Idea on the query : {}", query);
        return client
                .prompt()
                .user(query)
                .call()
                .entity(Idea.class);
    }
}
