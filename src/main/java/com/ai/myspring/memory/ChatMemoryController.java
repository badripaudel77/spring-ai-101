package com.ai.myspring.memory;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/memory/chats")
public class ChatMemoryController {

    private final ChatMemoryService chatMemoryService;

    public ChatMemoryController(ChatMemoryService chatMemoryService) {
        this.chatMemoryService = chatMemoryService;
    }

    @GetMapping("")
    public String chatWithMemoryContext(@RequestParam("prompt") String prompt) {
        return chatMemoryService.chatWithContext(prompt);
    }
}
