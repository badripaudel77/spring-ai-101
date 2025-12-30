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
                .system("You job is to convert currency from one to the other. You will be " +
                        "given a prompt with country name or currency name or just short code for currency" +
                        " for example, USD for US Dollars, NPR for Nepali rupee. Just analyze the prompt and give the best " +
                        "matching answer without any flaws.")
                .tools(exchangeTools)
                .call()
                .entity(ExchangeRateResponse.class);
    }
}
