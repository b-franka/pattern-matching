package hu.franka.patternmatcher.model;


import hu.franka.patternmatcher.exception.ApplicationException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

@AllArgsConstructor
public enum Command {

    RUN("run"), HELP("help");
    @Getter
    private final String argument;

    public static Command fromArg(String argument) throws ApplicationException {
        if (StringUtils.isEmpty(argument)) throw new ApplicationException("Command is empty", ExitCode.INVALID_ARGS);
        return Arrays.stream(Command.values()).filter(command -> argument.equals(command.getArgument()))
                .findFirst()
                .orElseThrow(() -> new ApplicationException("Unknown command specified: " + argument, ExitCode.INVALID_ARGS));
    }
}
