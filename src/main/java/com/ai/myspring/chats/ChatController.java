package com.ai.myspring.chats;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chats")
public class ChatController {

    private final ChatService service;

    public ChatController(ChatService service) {
        this.service = service;
    }

    @GetMapping("")
    public ChatResponse getAiResponse(@RequestParam("prompt") String prompt) {
        return service.getAiResponse(prompt);
    }

    @GetMapping("/population")
    public String getPopulationQueryResult(@RequestParam("prompt") String prompt) {
        return service.getPopulationQuery(prompt);
    }

}
