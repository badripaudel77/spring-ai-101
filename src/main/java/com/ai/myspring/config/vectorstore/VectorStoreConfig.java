package com.ai.myspring.config.vectorstore;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.google.genai.GoogleGenAiEmbeddingConnectionDetails;
import org.springframework.ai.google.genai.text.GoogleGenAiTextEmbeddingModel;
import org.springframework.ai.google.genai.text.GoogleGenAiTextEmbeddingOptions;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VectorStoreConfig {

    @Value("${spring.ai.google.genai.api-key}")
    String apiKey;

    @Value("${spring.ai.google.genai.embedding.project-id}")
    String projectId;

    @Value("${spring.ai.google.genai.embedding.text.options.model:text-embedding-004}")
    String model;

    @Bean
    public VectorStore vectorStore(EmbeddingModel embeddingModel) {
        return SimpleVectorStore
                .builder(embeddingModel)
                .build();
    }

    // I was trying to bypass the error "Caused by: java.io.IOException:
    // Your default credentials were not found. To set up Application Default Credentials for your environment,
    // see https://cloud.google.com/docs/authentication/external/set-up-adc."
    // So, excluded autoconfiguration (main class) and created bean with connection details
    //
     @Bean
    public EmbeddingModel embeddingModel(GoogleGenAiEmbeddingConnectionDetails details) {
        // This uses the direct API handshake, ignoring "Project ID" and "ADC"
        GoogleGenAiTextEmbeddingOptions options = GoogleGenAiTextEmbeddingOptions
                .builder()
                .model(model)
                .taskType(GoogleGenAiTextEmbeddingOptions.TaskType.RETRIEVAL_DOCUMENT)
                .build();

        return new GoogleGenAiTextEmbeddingModel(details, options);
    }

    @Bean
    public GoogleGenAiEmbeddingConnectionDetails googleGenAiEmbeddingConnectionDetails() {
        return GoogleGenAiEmbeddingConnectionDetails
                .builder()
                .apiKey(apiKey)
                .projectId(projectId)
                .build();
    }
}
