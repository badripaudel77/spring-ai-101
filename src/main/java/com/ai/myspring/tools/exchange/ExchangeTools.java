package com.ai.myspring.tools.exchange;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ExchangeTools {
    private final RestClient restClient;

    public ExchangeTools() {
        this.restClient = RestClient
                .builder()
                .baseUrl("https://hexarate.paikama.co")// change https to http when testing locally.
                .build();
    }

    @Tool(description = "Retrieve the latest exchange rate from one currency to the another.")
    public ExchangeRateResponse getExchangeRate(String from, String to) {
        // call the api
        return restClient
                .get()
                .uri("/api/rates/{from}/{to}/latest", from, to)
                .retrieve()
                .body(ExchangeRateResponse.class);
    }
}
