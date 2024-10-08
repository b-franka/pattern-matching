package hu.franka.patternmatcher.service;

import lombok.AllArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

@AllArgsConstructor
public class FileStorageService implements StorageService {
    private static final String SEQUENCE_FILE_EXTENSION = ".seq";

    private final Path dirPath;
    private final Map<String, StringBuilder> resourceMap;

    public void write() {
        resourceMap.forEach((key, value) -> {
            String filename = key + SEQUENCE_FILE_EXTENSION;
            try {
                Path fullPath = dirPath.resolve(filename);
                Files.write(fullPath, value.toString().getBytes());
                System.out.println("Saved file: " + fullPath);
            } catch (IOException e) {
                System.out.println("Could not save file " + filename);
            }
        });
    }
}
