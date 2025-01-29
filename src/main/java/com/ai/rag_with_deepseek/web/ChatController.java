package com.ai.rag_with_deepseek.web;

import com.ai.rag_with_deepseek.interfaces.AiChatService;
import com.ai.rag_with_deepseek.service.RagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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

    @GetMapping
    public Flux<String> rag(@RequestParam(defaultValue = "Hello") String message) {
        return chatService.chat(message);
    }

    @PostMapping("/load")
    public Flux<String> load(@RequestParam(defaultValue = "What is the content of the document?") String message,
                             @RequestParam("file") MultipartFile file) throws IOException {
        if(file == null || file.isEmpty()) {
            log.info("File is empty");
            return chatService.chat(message);
        }
        log.info("Saving document ...");
        Resource resource = ragService.saveDocument(file);
        log.info("Document saved");
        log.info("/////////////////////////");
        log.info("Saving segments ...");
        ragService.saveSegments(resource);
        log.info("Segments saved");
        return chatService.chat(message);
    }
}
