package hu.franka.patternmatcher.service.alignment;

import hu.franka.patternmatcher.exception.ApplicationException;
import hu.franka.patternmatcher.model.config.AlignmentConfig;

import java.util.List;
import java.util.Map;

public interface AlignmentProcessor {
    Map<String, StringBuilder> run(List<String> sequences, AlignmentConfig config) throws ApplicationException;
}
