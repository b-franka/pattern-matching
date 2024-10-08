package hu.franka.patternmatcher.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.franka.patternmatcher.exception.ApplicationException;
import hu.franka.patternmatcher.model.ExitCode;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@AllArgsConstructor
public class ConfigurationReader {

    private final Path filePath;

    public <T> T readConfig(Class<T> clazz) throws ApplicationException {
        try {
        String jsonString = Files.readString(filePath);

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonString, clazz);

        } catch (Exception e) {
            throw new ApplicationException("Could not read file: " + filePath, e, ExitCode.FUNCTIONAL_ERROR);
        }
    }

}
