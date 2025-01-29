package com.ai.rag_with_deepseek.interfaces;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.spring.AiService;
import reactor.core.publisher.Flux;

@AiService
public interface AiChatService {

    @SystemMessage("""
            You are an AI Agent, responsible for chatting with users.
            Insure that you respond with the same language as the user.
            """)
    Flux<String> chat(String message);

}
