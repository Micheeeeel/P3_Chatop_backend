package com.chatop.backend.service;

import com.chatop.backend.exceptions.StorageException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class StorageService {
    private final Path rootLocation = Paths.get("upload");

    StorageService(){
        if (!Files.exists(rootLocation)) {
            try {
                Files.createDirectories(rootLocation);
            } catch (IOException e) {
                throw new StorageException("Could not create storage directory.", e);
            }
        }
    }

    public String store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }
            String originalFileName = file.getOriginalFilename();
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String storedFileName = UUID.randomUUID().toString() + fileExtension;

            try (InputStream is = file.getInputStream()) {
                Files.copy(is, this.rootLocation.resolve(storedFileName));
            }

            return storedFileName;
        } catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }
}
