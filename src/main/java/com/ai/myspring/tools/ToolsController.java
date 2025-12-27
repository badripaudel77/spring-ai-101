package com.ai.myspring.tools;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tools")
public class ToolsController {

    private final ToolsService service;

    public ToolsController(ToolsService service) {
        this.service = service;
    }

    // http://localhost:8080/api/tools/date?prompt= what day is tomorrow ?
    @GetMapping("/date")
    public String getPromptedDate(@RequestParam String prompt) {
        return service.getPromptedDate(prompt);
    }

}
