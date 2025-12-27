package com.ai.myspring.rag;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class DataLoader {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("classpath:/data/company_policies.pdf")
    private Resource pdfFileResource;

    private final VectorStore vectorStore;

    public DataLoader(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @PostConstruct
    public void loadData() throws IOException {
        logger.info("Loading the file resources {} of the size {} into the vector store", pdfFileResource, pdfFileResource.contentLength());

        // 1. READ: Extract text from PDF
        PagePdfDocumentReader pdfReader = new PagePdfDocumentReader(pdfFileResource);

        // 2. TRANSFORM: Split into chunks
        // This is crucial so the AI doesn't get overwhelmed by long paged documents
        TokenTextSplitter splitter = new TokenTextSplitter();
        List<Document> chunks = splitter.split(pdfReader.read());

        // 3. LOAD: Convert to vectors and save to your VectorStore
        List<Document> chunksWithMetadata = chunks
                .stream()
                .map(chunk -> new Document(
                        chunk.getFormattedContent(),
                        Map.of("country", "World"))
                )
                .toList();
        vectorStore.add(chunksWithMetadata);
        logger.info("File {} loaded into the vector store successfully, name : {}", pdfFileResource, vectorStore.getName());
    }

}
