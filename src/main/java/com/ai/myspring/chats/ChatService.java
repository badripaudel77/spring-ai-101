package com.ai.myspring.chats;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ChatService {

    @Qualifier("googleGenAiChatClient")
    private final ChatClient client;

    private final CacheManager manager;

    public ChatService(ChatClient.Builder builder, CacheManager cacheManager) {
        this.client = builder.build();
        this.manager = cacheManager;
    }

    // Just for confirmation which cache is being used. Default is ConcurrentMapCacheManager
    @PostConstruct
    void log() {
        log.info("Configured cache class is : {}", manager.getClass());
    }

    // To avoid too many calls using key, get from the cache if it is the same call.
    @Cacheable(value = "ai-chat-all-questions", key = "#prompt")
    public ChatResponse getAiResponse(String prompt) {
        return client
                .prompt()
                .user(prompt)
                .call()
                .chatResponse();
    }

    // System message, to restrict certain types of questions.
    @Cacheable(value = "ai-chat-specific-questions", key = "#prompt")
    public String getPopulationQuery(String prompt) {
        String instruction = """
                    You only have to answer related to population of the world including any country, continent. 
                    Any query and question besides this should be discarded. 
                    If you're asked anything else, just response : "You can't ask question other than the ones related to population"
                """;
        return client
                .prompt()
                .user(prompt)
                .system(instruction)
                .call()
                .content();
    }
}
