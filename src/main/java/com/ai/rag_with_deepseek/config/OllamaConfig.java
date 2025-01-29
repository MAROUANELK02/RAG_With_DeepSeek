package com.ai.rag_with_deepseek.config;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class OllamaConfig {
    private static final String BASE_URL = "http://localhost:11434";
    private static final String MODEL_NAME = "deepseek-r1:1.5b";

    @Bean
    public ChatLanguageModel chatLanguageModel() {
        return OllamaChatModel.builder()
                .baseUrl(BASE_URL)
                .temperature(0.0)
                .logRequests(true)
                .logResponses(true)
                .timeout(Duration.ofMinutes(3))
                .modelName(MODEL_NAME)
                .build();
    }

    @Bean
    public StreamingChatLanguageModel streamingChatLanguageModel() {
        return OllamaStreamingChatModel.builder()
                .baseUrl(BASE_URL)
                .temperature(0.0)
                .logRequests(true)
                .logResponses(true)
                .timeout(Duration.ofMinutes(3))
                .modelName(MODEL_NAME)
                .build();
    }

    @Bean
    public EmbeddingModel embeddingModel() {
        return OllamaEmbeddingModel.builder()
                .baseUrl(BASE_URL)
                .modelName(MODEL_NAME)
                .build();
    }
}
