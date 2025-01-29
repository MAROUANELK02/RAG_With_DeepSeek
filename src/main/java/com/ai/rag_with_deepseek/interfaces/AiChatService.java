package com.ai.rag_with_deepseek.interfaces;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.spring.AiService;
import reactor.core.publisher.Flux;

@AiService
public interface AiChatService {

    @SystemMessage("""
            You are an AI Agent, responsible for chatting with users.
            """)
    Flux<String> chat(String message);
}
