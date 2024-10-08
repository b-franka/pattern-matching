package hu.franka.patternmatcher.service;

import hu.franka.patternmatcher.exception.ApplicationException;
import hu.franka.patternmatcher.model.ExitCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

class SequenceReaderTest {

    public static final String SEQUENCE_FILE_DIR = "sequenceFile";
    public static final String SAMPLE_SEQ_FILE_NAME = "sample.seq";
    private SequenceReader sequenceReader;
    private Path filePath;

    @BeforeEach
    void setUp() throws Exception {
        URI uri = ClassLoader.getSystemResource(SEQUENCE_FILE_DIR).toURI();
        String mainPath = Paths.get(uri).toString();
        filePath = Paths.get(mainPath, SAMPLE_SEQ_FILE_NAME);

        sequenceReader = new SequenceReader(filePath);
    }

    @Test
    void test_ReadSequences() throws Exception {
        List<String> result = sequenceReader.readSequences();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(List.of("ACTCACGACCACTAACTAGCAATACGATCG", "CAGTAAGCGATCAGACAGTACAGACGTACA", "ACTCACGACCACTAACTGGCAATACGATCG", "AGACAACATCAGATCGCAAGACGACAGATA"), result);
    }

    @Test
    void test_ReadSequences_ThrowsApplicationExceptionOnIOException() {
        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {

            mockedFiles.when(() -> Files.readAllLines(filePath)).thenThrow(new RuntimeException("I/O Error"));

            ApplicationException exception = assertThrows(ApplicationException.class, sequenceReader::readSequences);

            assertTrue(exception.getMessage().contains("Could not read file"));
            assertEquals(ExitCode.FUNCTIONAL_ERROR, exception.getExitCode());
        }
    }
}