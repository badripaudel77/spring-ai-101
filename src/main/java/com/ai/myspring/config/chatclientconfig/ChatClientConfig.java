package com.ai.myspring.config.chatclientconfig;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.google.genai.GoogleGenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    // Just config chat client for desired model, in the future if we have to add one more, introduce another bean and use Qualifier to use that one.
    @Bean
    public ChatClient googleGenAiChatClient(GoogleGenAiChatModel chatModel) {
        return ChatClient.create(chatModel);
    }

}
