**About:**
Spring AI 101, Spring AI all concepts. Integrating AI and Java using LLM and Spring AI.

Spring AI is an application framework for AI engineering. 
Its goal is to apply to the AI domain Spring ecosystem design principles such 
as portability and modular design and promote using POJOs as the building blocks of an application to the AI domain.

**NOTE:** In this repo, folder is created for each concept. For example, **tools** folder contains the concepts
illustration for tooling in spring AI to interact with AI and data.

**For running application :** You need to pass key from environment variable under the name : **GOOGLE_API_KEY=YOUR_KEY**

---
Topics:
---

**1. ChatClient:**
##### What is ChatClient?
`ChatClient` is the main interface used to interact with a Large Language Model (LLM) such as OpenAI or Gemini.  
It provides a clean and structured way to send user prompts to the AI and receive responses.
By default, it is stateless.
---

### What does it do?
- Sends user input (prompt) to the AI model
- Executes the AI request
- Returns the generated response
---

Example :
```java 
ChatResponse response = chatClient
        .prompt()
        .user("Explain AI for a non-technical person.")
        .call()
        .chatResponse();
```
For mapping reponse to java model, .entity() can be used. 
```java
ChatResponse response = chatClient
        .prompt()
        .user("Explain AI for a non-technical person.")
        .call()
        .entity(ClassName.class);
```

Since, by default it is stateless, it forgets the previous conversation.
For example if I prompt before as : My Name is Badri" and  in the later prompt,
I ask, what is my name ? It won't say my name because it does not have access to data.

To make it context aware, need to add advisors so that the chat client is aware of the memory.
For example: 
```java
chatClient
        .defaultAdvisors(memoryAdvisor)
        .build();
```

**2.Tools**
---
### 🛠️ Tools in ChatClient (Function Calling)

### What is a Tool?
A **Tool** is a Java method that the AI model can call automatically when it determines that the method is needed to answer a user’s prompt.  
This is similar to *function calling*, where the LLM decides **when** and **how** to invoke backend logic.

Tools allow the AI to:
- Fetch real data
- Apply business rules
- Respect tenant / security context
- Return strongly typed objects

---

### Defining a Tool
```java
@Tool(description = "Retrieve Customer Information by given Id")
public Customer getCustomerInfo(Long id, ToolContext context) {
    return getCustomers()
            .stream()
            .filter(customer ->
                    Objects.equals(customer.getId(), id)
                    && context.getContext().get("tenantId").equals("event_logic"))
            .findFirst()
            .orElseThrow(() ->
                    new RuntimeException("No customer found with ID: " + id));
}
```
Here,
- @Tool exposes the method to the AI
- The LLM can pass parameters (id)
- ToolContext carries runtime optional metadata (e.g. tenant, user, role)

To use the defined tool, it as be added in tools method as : 
```java
public Customer getCustomerById(String prompt) {
    Customer response = client
            .prompt(prompt)
            .tools(new CustomerTools())
            .toolContext(Map.of("tenantId", "event_logic"))
            .call()
            .entity(Customer.class);

    return response;
}
```

**3.Retrieval Augmented Generation (RAG)**
---
## What is RAG?
**Retrieval-Augmented Generation (RAG)** is an architecture that combines:

- **Information Retrieval** (searching relevant documents)
- **Text Generation** (LLMs generating answers)

Instead of relying only on the model’s training data, RAG allows the LLM to use **external knowledge** at query time.  
This makes responses more **accurate, grounded, and up-to-date**. For example, to search
companies policies, we can provide documents of all the policies so that LLM can search and respond.

---

## Why RAG is Needed ?
Large Language Models have limitations:

- Training data is **static**
- No access to **private or domain-specific data**
- Can **hallucinate** confident but incorrect answers

RAG addresses these issues by grounding responses in **retrieved documents**, reducing hallucinations and improving factual correctness.

---

## High-Level RAG Flow

1. User asks a question
2. The question is converted into an **embedding**
3. The embedding is searched against a **Vector Store**
4. The most relevant documents are retrieved
5. Retrieved content is injected into the prompt
6. The LLM generates an answer **based on this context**

## 📦 Loading Data into Vector Store (RAG Ingestion)

Below is a simple example showing how a PDF file is **read, split, enriched with metadata, and stored in a Vector Store** for RAG.
### 1. Load & Index Documents
```java
// 1. READ: Extract text from PDF
PagePdfDocumentReader pdfReader = new PagePdfDocumentReader(pdfFileResource);
// 2. TRANSFORM: Split into smaller chunks
TokenTextSplitter splitter = new TokenTextSplitter();
List<Document> chunks = splitter.split(pdfReader.read());
// 3. LOAD: Add metadata and store as embeddings
List<Document> chunksWithMetadata = chunks.stream()
        .map(chunk -> new Document(
                chunk.getFormattedContent(),
                Map.of("country", "World")   // metadata
        )).toList();
vectorStore.add(chunksWithMetadata);
```

### 2. For RAG Querying
To enable Retrieval-Augmented Generation, attach the vector store to the ChatClient using a QuestionAnswerAdvisor.
```java
chatClient = builder
        .defaultAdvisors(QuestionAnswerAdvisor.builder(vectorStore).build())
        .build();
```

---

## Setup Notes (Gemini + RAG)
When using **Gemini** with RAG, you may encounter (I did):
> `No default credentials found`

### Fix (Windows)
Set the Google credentials explicitly:
```bash
setx GOOGLE_APPLICATION_CREDENTIALS "C:\Users\Valid_Path\filename.json"
```