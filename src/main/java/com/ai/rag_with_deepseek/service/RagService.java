package com.ai.rag_with_deepseek.service;

import dev.langchain4j.data.document.BlankDocumentException;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.image.Image;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ImageContent;
import dev.langchain4j.data.message.TextContent;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
public class RagService {
    private static final long MAX_FILE_SIZE = 3 * 1024 * 1024;
    private static final String UPLOAD_DIR = "src/main/resources/docs";

    private static final Logger log = LoggerFactory.getLogger(RagService.class);

    private EmbeddingStoreIngestor embeddingStoreIngestor;
    private EmbeddingStore<TextSegment> embeddingStore;
    private ChatLanguageModel chatLanguageModel;


    public RagService(EmbeddingStoreIngestor embeddingStoreIngestor, EmbeddingStore<TextSegment> embeddingStore, ChatLanguageModel chatLanguageModel) {
        this.embeddingStoreIngestor = embeddingStoreIngestor;
        this.embeddingStore = embeddingStore;
        this.chatLanguageModel = chatLanguageModel;
    }

    public void saveSegments(Resource resource) throws IOException {
        log.info("Removing all segments ...");
        embeddingStore.removeAll();
        try {
            log.info("Loading document ...");
            Document document = FileSystemDocumentLoader.loadDocument(resource.getFile().toPath());
            log.info("Ingesting document ...");
            embeddingStoreIngestor.ingest(document);
            log.info("Document ingested");
        }catch (BlankDocumentException e) {
            log.error("Document is empty or contains an image");
        }
    }

    /*public void saveMultimodalSegments(Resource resource) throws IOException {
        log.info("Removing all segments ...");
        embeddingStore.removeAll();
        log.info("Loading document ...");
        PDDocument document = PDDocument.load(resource.getFile());
        PDPageTree pdPages = document.getPages();
        PDFTextStripper pdfTextStripper = new PDFTextStripper();
        int index = 0;
        int page = 0;
        DocumentSplitter documentSplitter = DocumentSplitters.recursive(1000, 100);
        List<Document> imagesDocuments = new ArrayList<>();
        for(PDPage pdPage : pdPages) {
            ++page;
            pdfTextStripper.setStartPage(page);
            pdfTextStripper.setEndPage(page);
            PDResources resources = pdPage.getResources();
            List<String> media = new ArrayList<>();
            String textContent = pdfTextStripper.getText(document);
            for(var c : resources.getXObjectNames()) {
                PDXObject pdxObject = resources.getXObject(c);
                if(pdxObject instanceof PDImageXObject image) {
                    ++index;
                    BufferedImage imageImage = image.getImage();
                    String imagePath = "images/page_" + page + "_im_" + index + ".png";
                    FileOutputStream fileOutputStream = new FileOutputStream(imagePath);
                    ImageIO.write(imageImage, "png", fileOutputStream);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    ImageIO.write(imageImage, "png", byteArrayOutputStream);
                    byte[] data = byteArrayOutputStream.toByteArray();
                    String imageBase64 = Base64.getEncoder().encodeToString(data);
                    Image img = Image.builder()
                            .base64Data(imageBase64)
                            .mimeType("image/png")
                            .build();
                    ImageContent imageContent = ImageContent.from(img);
                    media.add(imagePath);
                    UserMessage userMessage = UserMessage.from(
                            TextContent.from("Give me a description of this image"),
                            imageContent
                    );
                    Response<AiMessage> response = chatLanguageModel.generate(userMessage);
                    String imageDescription = response.content().text();
                    System.out.println(imageDescription);
                    textContent = textContent + "\n" + "IMAGE : " + imagePath + "Description of the image :\n" + imageDescription;
                    Metadata metadata = new Metadata();
                    metadata.put("Page", page);
                    metadata.put("media", imagePath);
                    Document doc = new Document(imageDescription, metadata);
                    imagesDocuments.add(doc);
                }
                Metadata metadata = new Metadata();
                metadata.add("Page", page);
                metadata.add("media", media);
                Document doc = new Document(textContent, metadata);
                embeddingStoreIngestor.ingest(doc);
            }
        }
    }*/

    public Resource saveDocument(MultipartFile file) {
        try {
            if (file.getSize() > MAX_FILE_SIZE) {
                throw new IllegalArgumentException("La taille du fichier ne doit pas dépasser 3 Mo");
            }

            File directory = new File(UPLOAD_DIR);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR, fileName);

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return new UrlResource(filePath.toUri());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}