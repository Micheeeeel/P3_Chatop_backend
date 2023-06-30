package com.chatop.backend.service;

import com.chatop.backend.configuration.ImageProperties;
import com.chatop.backend.exceptions.StorageException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class StorageService {
    private final Path rootLocation;
    private final String baseUrl;

    StorageService(ImageProperties imageProperties){
        this.rootLocation = Paths.get(imageProperties.getImageDir());
        this.baseUrl = imageProperties.getBaseUrl();
    }

    private Path extractRootLocation(String baseUrl) {
        try {
            URL url = new URL(baseUrl);
            return Paths.get(url.toURI());
        } catch (MalformedURLException | URISyntaxException e) {
            throw new IllegalArgumentException("Invalid base URL: " + baseUrl, e);
        }
    }

    // gestion du stockage d'un fichier téléchargé: vérifie, génère un nom de fichier unique, copie le fichier vers l'emplacement de stockage, puis renvoie l'URL complète du fichier stocké
    public String store(MultipartFile file) {
        try {
            // s'assurer que le fichier n'est pas vide
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }
            String originalFileName = file.getOriginalFilename();   // le nom original du fichier est extrait (nom du fichier tel qu'il a été téléchargé par l'utilisateur)
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));   // extrayant l'extension du fichier
            String storedFileName = UUID.randomUUID().toString() + fileExtension;   //combine un identifiant unique généré avec l'extension du fichier

            try (InputStream is = file.getInputStream()) {  // un flux d'entrée est obtenu à partir du fichier
                Files.copy(is, this.rootLocation.resolve(storedFileName));  // copier le flux d'entrée du fichier vers l'emplacement de stockage spécifié dans la config imageProperties
            }

            return baseUrl + storedFileName;    // renvoie l'URL publique à laquelle le fichier peut être accessible
        } catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }
}
