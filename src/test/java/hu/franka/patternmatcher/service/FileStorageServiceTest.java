package hu.franka.patternmatcher.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FileStorageServiceTest {

    @TempDir
    Path tempDir;

    private Map<String, StringBuilder> resourceMap;
    private FileStorageService storageService;

    @BeforeEach
    void setUp() {
        resourceMap = new HashMap<>();
        resourceMap.put("sequence1", new StringBuilder("ACTG"));
        resourceMap.put("sequence2", new StringBuilder("CGTA"));
        storageService = new FileStorageService(tempDir, resourceMap);
    }

    @Test
    void test_WriteFilesSuccessfully() throws IOException {
        storageService.write();

        Path file1 = tempDir.resolve("sequence1.seq");
        Path file2 = tempDir.resolve("sequence2.seq");

        assertTrue(Files.exists(file1), "File sequence1.seq should be created.");
        assertTrue(Files.exists(file2), "File sequence2.seq should be created.");

        String content1 = Files.readString(file1);
        String content2 = Files.readString(file2);

        assertEquals("ACTG", content1, "The content of sequence1.seq is incorrect.");
        assertEquals("CGTA", content2, "The content of sequence2.seq is incorrect.");
    }

    @Test
    void test_CorrectFilenameGeneration() {
        storageService.write();

        Path file1 = tempDir.resolve("sequence1.seq");
        Path file2 = tempDir.resolve("sequence2.seq");

        assertTrue(Files.exists(file1), "File sequence1.seq should be created.");
        assertTrue(Files.exists(file2), "File sequence2.seq should be created.");
    }
}