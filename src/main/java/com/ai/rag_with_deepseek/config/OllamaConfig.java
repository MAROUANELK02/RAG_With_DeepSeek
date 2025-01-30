package com.ai.rag_with_deepseek.config;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiStreamingChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

import static java.time.Duration.ofSeconds;

@Configuration
public class OllamaConfig {
    private static final String BASE_URL = "http://localhost:11434";
    private static final String MODEL_NAME = "deepseek-r1:1.5b";

    private static final String apiKey = "AIzaSyC1a7MlpTVBWu5lJuAC8GRutiMzIb29cVA";

    //@Bean
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

    //@Bean
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
                .timeout(Duration.ofMinutes(5))
                .build();
    }

    @Bean
    public ChatLanguageModel chatGeminiLanguageModel() {
        return GoogleAiGeminiChatModel.builder()
                .apiKey(apiKey)
                .modelName("gemini-1.5-flash")
                .temperature(0.0)
                .topP(0.95)
                .topK(64)
                .maxOutputTokens(8192)
                .timeout(ofSeconds(60))
                .logRequestsAndResponses(true)
                .build();
    }

    @Bean
    public StreamingChatLanguageModel streamingGeminiChatLanguageModel() {
        return GoogleAiGeminiStreamingChatModel.builder()
                .apiKey(apiKey)
                .modelName("gemini-1.5-flash")
                .temperature(0.0)
                .topP(0.95)
                .topK(64)
                .maxOutputTokens(8192)
                .timeout(ofSeconds(60))
                .logRequestsAndResponses(true)
                .build();
    }
}
