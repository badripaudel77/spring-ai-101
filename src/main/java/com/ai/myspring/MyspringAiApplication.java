package com.ai.myspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MyspringAiApplication {

    static void main(String[] args) {
        SpringApplication.run(MyspringAiApplication.class, args);
    }

}
