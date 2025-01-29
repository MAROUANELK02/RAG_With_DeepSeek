package com.ai.rag_with_deepseek.service;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RagService {

    private static final Logger log = LoggerFactory.getLogger(RagService.class);
    @Value("classpath:docs/assurance.pdf")
    private Resource resource;

    private EmbeddingStoreIngestor embeddingStoreIngestor;

    public RagService(EmbeddingStoreIngestor embeddingStoreIngestor) {
        this.embeddingStoreIngestor = embeddingStoreIngestor;
    }

    public void saveSegments() throws IOException {
        Document document = FileSystemDocumentLoader.loadDocument(resource.getFile().toPath());
        embeddingStoreIngestor.ingest(document);
    }

}
