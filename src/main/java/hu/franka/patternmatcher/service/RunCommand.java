package hu.franka.patternmatcher.service;

import hu.franka.patternmatcher.exception.ApplicationException;
import hu.franka.patternmatcher.model.AlignmentType;
import hu.franka.patternmatcher.model.ExitCode;
import hu.franka.patternmatcher.model.Option;
import hu.franka.patternmatcher.model.config.AlignmentConfig;
import hu.franka.patternmatcher.service.alignment.AlignmentProcessor;
import hu.franka.patternmatcher.service.alignment.AlignmentProcessorFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class RunCommand {

    public void execute() throws ApplicationException {
        var config = getConfig();
        var sequences = getSequences();
        var alignmentProcessor = getAlignmentProcessor();

        var groupingResult = alignmentProcessor.run(sequences, config);

        StorageService storageService = new FileStorageService(getExistingPath(ArgumentParser.getOptionsMap().get(Option.OUTPUT_PATH_PREFIX)), groupingResult);
        storageService.write();
    }

    public Path getExistingPath(final String inputPath) throws ApplicationException {
        var path = Paths.get(inputPath);
        if (!Files.exists(path)) {
            throw new ApplicationException("Cannot find file: " + path, ExitCode.INVALID_INPUT_VALUES);
        }

        return path;
    }

    private AlignmentConfig getConfig() throws ApplicationException {
        var configPath = ArgumentParser.getOptionsMap().get(Option.CONFIG_FILE);
        if (null == configPath) {
            throw new ApplicationException("Config path is not specified.", ExitCode.INVALID_INPUT_VALUES);
        }
        var configurationReader = new ConfigurationReader(getExistingPath(configPath));
        var alignmentConfig = configurationReader.readConfig(AlignmentConfig.class);
        if (null == alignmentConfig) throw new ApplicationException("Invalid config.", ExitCode.INVALID_INPUT_VALUES);

        return alignmentConfig;
    }

    private List<String> getSequences() throws ApplicationException {
        var sequencePath = ArgumentParser.getOptionsMap().get(Option.SEQUENCE_FILE_PATH);
        if (null == sequencePath) {
            throw new ApplicationException("Sequence file path is not specified.", ExitCode.INVALID_INPUT_VALUES);
        }
        var sequenceReader = new SequenceReader(getExistingPath(sequencePath));
        var sequences = sequenceReader.readSequences();

        if (null == sequences || sequences.isEmpty()) throw new ApplicationException("Sequences must be specified.", ExitCode.INVALID_INPUT_VALUES);

        return sequences;
    }

    private AlignmentProcessor getAlignmentProcessor() throws ApplicationException {
        var alignmentType = AlignmentType.fromName(ArgumentParser.getOptionsMap().get(Option.ALIGNMENT));
        return AlignmentProcessorFactory.getAlignmentProcessor(alignmentType);
    }
}
