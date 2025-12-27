package com.ai.myspring.rag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/data")
public class DataController {

    private final DataService service;

    public DataController(DataService service) {
        this.service = service;
    }

    @GetMapping("/questions")
    public ResponseEntity<KnowledgeItem> askQuestion(@RequestParam("question") String question) {
        KnowledgeItem response = service.getAnswer(question);
        return ResponseEntity.ok(response);
    }
}
