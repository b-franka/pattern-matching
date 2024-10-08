package hu.franka.patternmatcher.service;

import hu.franka.patternmatcher.exception.ApplicationException;
import hu.franka.patternmatcher.model.ExitCode;
import lombok.AllArgsConstructor;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@AllArgsConstructor
public class SequenceReader {

    private final Path filePath;

    public List<String> readSequences() throws ApplicationException {
        try {
            return Files.readAllLines(filePath);
        } catch (Exception e) {
            throw new ApplicationException("Could not read file: " + filePath, e, ExitCode.FUNCTIONAL_ERROR);
        }
    }
}
