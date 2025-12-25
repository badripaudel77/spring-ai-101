package com.ai.myspring.memory;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;

@Service
public class ChatMemoryService {

    private final ChatClient chatClient;


    public ChatMemoryService(ChatClient.Builder chatClient, ChatMemory chatMemory) {
        this.chatClient = chatClient
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build()) // In Memory ChatMemory for conversation.
                .build();
    }

    public String chatWithContext(String prompt) {
        return chatClient.prompt(prompt)
                .call()
                .content();
    }

}
