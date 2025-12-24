package com.ai.myspring.chats;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    private final ChatClient client;

    public ChatService(ChatClient.Builder builder) {
        this.client = builder.build();
    }

    @Autowired
    private CacheManager manager;

    // Just for confirmation which cache is being used. Default is ConcurrentMapCacheManager
    @PostConstruct
    void log() {
        System.out.println("Configured cache class is : " + manager.getClass());
    }

    // To avoid too many calls using key, get from the cache if it is the same call.
    @Cacheable(value = "ai-chat-response", key = "#prompt")
    public ChatResponse getAiResponse(String prompt) {
        return client
                .prompt()
                .user(prompt)
                .call()
                .chatResponse();
    }
}
