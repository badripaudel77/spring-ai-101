# Retrieval-Augmented Generation (RAG)

## What is RAG?

Retrieval-Augmented Generation (RAG) is an architecture that combines:
- **Information Retrieval** (searching relevant documents)
- **Text Generation** (LLMs producing answers)

Instead of relying only on the model’s training data, RAG allows the model to:
- Fetch **up-to-date**
- **Domain-specific**
- **Private**
  information at query time.

This significantly reduces hallucinations and improves factual accuracy.

---

## Why RAG is Needed

LLMs have limitations:
- Training data is **static**
- Cannot access private data
- May hallucinate confident but incorrect answers

RAG solves this by grounding the model’s responses in **retrieved context**.

---

## High-Level RAG Flow

1. User asks a question
2. Question is converted into an **embedding**
3. Embedding is used to search a **Vector Database**
4. Most relevant documents are retrieved
5. Retrieved text is injected into the prompt
6. LLM generates an answer **based on retrieved context**

## Setup : with gemini, RAG needs some stupid setup if it keeps saying no default credential found.
Setting on windows: etx GOOGLE_APPLICATION_CREDENTIALS "C:\Users\Valid_Path\filename.json"
filename.json needs to be generated from the google cloud : https://console.cloud.google.com/ and need to use that project id in the 
application.properties (or yaml) file