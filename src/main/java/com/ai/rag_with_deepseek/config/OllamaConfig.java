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
    private String baseUrl = "http://localhost:11434";
    private String modelName = "deepseek-r1:8b";

    @Bean
    public ChatLanguageModel chatLanguageModel() {
        return OllamaChatModel.builder()
                .baseUrl(baseUrl)
                .temperature(0.0)
                .logRequests(true)
                .logResponses(true)
                .timeout(Duration.ofMinutes(3))
                .modelName(modelName)
                .build();
    }

    @Bean
    public StreamingChatLanguageModel streamingChatLanguageModel() {
        return OllamaStreamingChatModel.builder()
                .baseUrl(baseUrl)
                .temperature(0.0)
                .logRequests(true)
                .logResponses(true)
                .timeout(Duration.ofMinutes(3))
                .modelName(modelName)
                .build();
    }

    @Bean
    public EmbeddingModel embeddingModel() {
        return OllamaEmbeddingModel.builder()
                .baseUrl(baseUrl)
                .modelName(modelName)
                .build();
    }
}
