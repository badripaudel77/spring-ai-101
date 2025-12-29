package com.ai.myspring.tools;

import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.annotation.Tool;
import java.util.List;
import java.util.Objects;

public class CustomerTools {

    @Tool(description = "Retrieve Customer Information by given Id ")
    public Customer getCustomerInfo(Long id, ToolContext context) {
        return getCustomers().
                stream()
                .filter(customer -> Objects.equals(customer.getId(), id) && context.getContext().get("tenantId").equals("event_logic"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No customer found with ID: " + id));
    }

    List<Customer> getCustomers() {
        return List.of(
                new Customer(1L, "Badri Paudel"),
                new Customer(2L, "Hari Paudel"),
                new Customer(3L, "John Doe"),
                new Customer(4L, "Smith Brad")
        );
    }

}
