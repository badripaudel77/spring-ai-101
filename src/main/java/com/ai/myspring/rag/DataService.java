package com.ai.myspring.rag;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.VectorStoreRetriever;
import org.springframework.stereotype.Service;

import java.util.List;

// VectorStoreRetriever is used for retrieval info from the vector store.

@Service
public class DataService {

    private final ChatClient chatClient;
    private final VectorStoreRetriever retriever;

    public DataService(VectorStore vectorStore, ChatClient.Builder builder, VectorStoreRetriever retriever) {
        this.retriever = retriever;
        // add vector store to the chat context, so that chats are answered based on the vector store's content.
        this.chatClient = builder
                .defaultAdvisors(QuestionAnswerAdvisor.builder(vectorStore).build())
                .build();
    }

    public KnowledgeItem getAnswer(String question) {
        return chatClient
                .prompt()
                .user(question)
                .call()
                .entity(KnowledgeItem.class);

    }

    public List<Document> findSimilarDocumentsWithFilters(String query, String country) {
        SearchRequest request = SearchRequest.builder()
                .query(query)
                .topK(5)
                .filterExpression("country == '" + country + "'")
                .build();

        return retriever.similaritySearch(request);
    }

}
