package com.ai.myspring.tools;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CustomerService {
    private final ChatClient client;

    public CustomerService(ChatClient.Builder builder) {
        this.client = builder.build();
    }

    public Customer getCustomerById(String prompt) {
        Customer response = client
                .prompt(prompt)
                .tools(new CustomerTools())
                .toolContext(Map.of("tenantId", "event_logic"))
                .call()
                .entity(Customer.class);
        System.out.println("entity : " + response);
        return response;
    }
}
