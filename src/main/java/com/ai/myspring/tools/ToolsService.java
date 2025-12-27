package com.ai.myspring.tools;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class ToolsService {

    private final ChatClient client;

    public ToolsService(ChatClient.Builder client) {
        this.client = client
                // .defaultTools(new MyDateTimeTool())
                .build();
    }

    public String getPromptedDate(String prompt) {
        return client
                .prompt(prompt)
                .tools(new MyDateTimeTool()) // provide tools so it can have context
                .call()
                .content();
    }
}
