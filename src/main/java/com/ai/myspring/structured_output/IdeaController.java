package com.ai.myspring.structured_output;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ideas")
public class IdeaController {

    private final IdeaService service;

    public IdeaController(IdeaService service) {
        this.service = service;
    }

    @GetMapping("")
    public Idea getIdea(@RequestParam("prompt") String prompt) {
        return service.getIdea(prompt);
    }

}
