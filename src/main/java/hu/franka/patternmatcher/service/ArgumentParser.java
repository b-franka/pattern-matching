package hu.franka.patternmatcher.service;

import hu.franka.patternmatcher.exception.ApplicationException;
import hu.franka.patternmatcher.model.Command;
import hu.franka.patternmatcher.model.ExitCode;
import hu.franka.patternmatcher.model.Option;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class ArgumentParser {

    @Getter
    private static final Map<Option, String> optionsMap = new HashMap<>();
    @Getter
    private final Command command;

    public ArgumentParser(String[] args) throws ApplicationException {
        if ((null == args) || (args.length < 1)) {
            throw new ApplicationException("Command not specified", ExitCode.INVALID_ARGS);
        }
        command = Command.fromArg(args[0]);

        for (int i = 1; i < args.length; i++) {
            String argValue = null;
            if (!args[i].startsWith("-")) {
                throw new ApplicationException("Invalid argument specified: " + args[i], ExitCode.INVALID_ARGS);
            }

            String argName = args[i].substring(1);
            if (args.length > (i + 1) && !args[i + 1].startsWith("-")) {
                argValue = args[i + 1];
                i++;
            }
            optionsMap.put(1 == argName.length() ? Option.fromShortName(argName.charAt(0)) : Option.fromName(argName), argValue);
        }
    }
}
