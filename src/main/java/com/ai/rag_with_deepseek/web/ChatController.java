package com.ai.rag_with_deepseek.web;

import com.ai.rag_with_deepseek.interfaces.AiChatService;
import com.ai.rag_with_deepseek.service.RagService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.io.IOException;

@RestController
@RequestMapping("/chat")
@CrossOrigin(origins = "*")
public class ChatController {
    private static final Logger log = LoggerFactory.getLogger(ChatController.class);
    private AiChatService chatService;
    private RagService ragService;

    public ChatController(AiChatService chatService, RagService ragService) {
        this.chatService = chatService;
        this.ragService = ragService;
    }

    @PostConstruct
    public void init() throws IOException {
        log.info("Saving segments ...");
        ragService.saveSegments();
        log.info("Segments saved");
    }

    @GetMapping
    public Flux<String> rag(@RequestParam(defaultValue = "Hello") String message) {
        return chatService.chat(message);
    }

}
