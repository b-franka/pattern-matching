package hu.franka.patternmatcher.service;

import hu.franka.patternmatcher.exception.ApplicationException;
import hu.franka.patternmatcher.model.Command;
import hu.franka.patternmatcher.model.ExitCode;
import hu.franka.patternmatcher.model.Option;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ArgumentParserTest {

    @BeforeEach
    void clearOptionsMap() {
        ArgumentParser.getOptionsMap().clear();
    }

    @Test
    void test_ValidCommandAndOptions() throws ApplicationException {
        String[] args = {"run", "-s", "sequence.seq", "-c", "config.conf", "-o", "output", "-a", "bestAlignment"};

        ArgumentParser parser = new ArgumentParser(args);

        assertEquals(Command.RUN, parser.getCommand());
        Map<Option, String> options = ArgumentParser.getOptionsMap();
        assertEquals(4, options.size());
        assertEquals("sequence.seq", options.get(Option.SEQUENCE_FILE_PATH));
        assertEquals("config.conf", options.get(Option.CONFIG_FILE));
        assertEquals("output", options.get(Option.OUTPUT_PATH_PREFIX));
        assertEquals("bestAlignment", options.get(Option.ALIGNMENT));
    }

    @Test
    void test_CommandNotSpecified() {
        String[] args = {};

        ApplicationException exception = assertThrows(ApplicationException.class, () -> new ArgumentParser(args));
        assertEquals("Command not specified", exception.getMessage());
        assertEquals(ExitCode.INVALID_ARGS, exception.getExitCode());
    }

    @Test
    void test_UnknownCommand() {
        String[] args = {"unknownCommand"};

        ApplicationException exception = assertThrows(ApplicationException.class, () -> new ArgumentParser(args));
        assertEquals("Unknown command specified: unknownCommand", exception.getMessage());
        assertEquals(ExitCode.INVALID_ARGS, exception.getExitCode());
    }

    @Test
    void test_InvalidOption() {
        String[] args = {"run", "-z"};

        ApplicationException exception = assertThrows(ApplicationException.class, () -> new ArgumentParser(args));
        assertEquals("Unknown option specified: -z", exception.getMessage());
        assertEquals(ExitCode.INVALID_ARGS, exception.getExitCode());
    }

    @Test
    void test_OptionWithoutValue() throws ApplicationException {
        String[] args = {"run", "-s", "sequence.txt", "-o"};

        ArgumentParser parser = new ArgumentParser(args);

        assertEquals(Command.RUN, parser.getCommand());
        Map<Option, String> options = ArgumentParser.getOptionsMap();
        assertEquals(2, options.size());
        assertEquals("sequence.txt", options.get(Option.SEQUENCE_FILE_PATH));
        assertNull(options.get(Option.OUTPUT_PATH_PREFIX));
    }

    @Test
    void test_InvalidArgumentNotStartingWithHyphen() {
        String[] args = {"run", "nohyphen"};

        ApplicationException exception = assertThrows(ApplicationException.class, () -> new ArgumentParser(args));
        assertEquals("Invalid argument specified: nohyphen", exception.getMessage());
        assertEquals(ExitCode.INVALID_ARGS, exception.getExitCode());
    }

}