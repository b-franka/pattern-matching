package hu.franka.patternmatcher.model;

import hu.franka.patternmatcher.exception.ApplicationException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

@AllArgsConstructor
public enum Option {

    SEQUENCE_FILE_PATH("sequence", 's'), CONFIG_FILE("config", 'c'), OUTPUT_PATH_PREFIX("outputPathPrefix", 'o'), ALIGNMENT("alignment", 'a');

    @Getter
    private final String name;
    @Getter
    private final char shortName;

    public static Option fromName(String name) throws ApplicationException {
        if (StringUtils.isEmpty(name)) throw new ApplicationException("Empty option given.", ExitCode.INVALID_ARGS);
        return Arrays.stream(Option.values()).filter(option -> name.equals(option.getName()))
                .findFirst()
                .orElseThrow(() -> new ApplicationException("Unknown option specified: " + name, ExitCode.INVALID_ARGS));
    }

    public static Option fromShortName(char shortName) throws ApplicationException {
        return Arrays.stream(Option.values()).filter(option -> shortName == option.getShortName())
                .findFirst()
                .orElseThrow(() -> new ApplicationException("Unknown option specified: -" + shortName, ExitCode.INVALID_ARGS));
    }
}
