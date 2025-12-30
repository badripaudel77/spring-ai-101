package com.ai.myspring.tools.exchange;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class ExchangeService {
    private final ChatClient client;
    private final ExchangeTools exchangeTools;

    public ExchangeService(ChatClient.Builder builder, ExchangeTools exchangeTools) {
        // set .defaultTools(exchangeTools) if need to reuse in all tools in this class.
        client = builder.build();
        this.exchangeTools = exchangeTools;
    }

    public ExchangeRateResponse exchange(String prompt) {
        return client
                .prompt(prompt)
                .tools(exchangeTools)
                .call()
                .entity(ExchangeRateResponse.class);
    }
}
