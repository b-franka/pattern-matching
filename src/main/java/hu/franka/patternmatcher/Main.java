package hu.franka.patternmatcher;

import hu.franka.patternmatcher.exception.ApplicationException;
import hu.franka.patternmatcher.model.Command;
import hu.franka.patternmatcher.model.ExitCode;
import hu.franka.patternmatcher.service.ArgumentParser;
import hu.franka.patternmatcher.service.RunCommand;
import hu.franka.patternmatcher.util.UsagePrinter;

public class Main {

    public static void main(String[] args) {
        ArgumentParser argumentParser;
        try {
            argumentParser = new ArgumentParser(args);

            if (Command.HELP.equals(argumentParser.getCommand())) {
                UsagePrinter.printUsage();
            } else {
                var runcommand = new RunCommand();
                runcommand.execute();
            }
        } catch (ApplicationException e) {
            System.err.println(e.getMessage());

            if (ExitCode.INVALID_ARGS.equals(e.getExitCode())) {
                System.err.println();
                UsagePrinter.printUsage();
            }

            System.exit(e.getExitCode().getCode());
            return;
        }

        System.exit(ExitCode.OK.getCode());
    }
}
