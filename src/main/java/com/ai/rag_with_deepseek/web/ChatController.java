package com.ai.rag_with_deepseek.web;

import com.ai.rag_with_deepseek.interfaces.AiChatService;
import com.ai.rag_with_deepseek.interfaces.Assistant;
import com.ai.rag_with_deepseek.service.RagService;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
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
    private StreamingChatLanguageModel chatModel;
    private RagService ragService;
    private ChatMemoryProvider chatMemoryProvider;
    private InMemoryEmbeddingStore<TextSegment> embeddingStore;

    public ChatController(AiChatService chatService, StreamingChatLanguageModel chatModel, RagService ragService, ChatMemoryProvider chatMemoryProvider, InMemoryEmbeddingStore<TextSegment> embeddingStore) {
        this.chatService = chatService;
        this.chatModel = chatModel;
        this.ragService = ragService;
        this.chatMemoryProvider = chatMemoryProvider;
        this.embeddingStore = embeddingStore;
    }

    @GetMapping("/chat")
    public Flux<String> chat(@RequestParam(defaultValue = "Hello") String message) {
        return chatService.chat(message);
    }

    @GetMapping
    public String rag(@RequestParam(defaultValue = "Hello") String message) throws IOException {
        log.info("Saving segments ...");
        ragService.saveSegments();
        log.info("Segments saved");
        Assistant assistant = AiServices.builder(Assistant.class)
                .streamingChatLanguageModel(chatModel)
                .chatMemoryProvider(chatMemoryProvider)
                .contentRetriever(EmbeddingStoreContentRetriever.from(embeddingStore))
                .build();
        return assistant.chat(message);
    }
}
