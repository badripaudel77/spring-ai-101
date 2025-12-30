package com.ai.myspring.tools.exchange;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rates")
public class ExchangeController {
    private final ExchangeService service;

    public ExchangeController(ExchangeService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ExchangeRateResponse> exchangeCurrency(@RequestParam("prompt") String prompt) {
        ExchangeRateResponse exchange = service.exchange(prompt);
        return ResponseEntity.ok(exchange);
    }
}
