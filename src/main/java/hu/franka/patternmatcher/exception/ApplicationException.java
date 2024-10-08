package hu.franka.patternmatcher.exception;

import hu.franka.patternmatcher.model.ExitCode;
import lombok.Getter;

public class ApplicationException extends Exception {

    @Getter
    private final ExitCode exitCode;

    public ApplicationException(String message, Throwable cause, ExitCode exitCode) {
        super(message, cause);
        this.exitCode = exitCode;
    }

    public ApplicationException(String message, ExitCode exitCode) {
        super(message);
        this.exitCode = exitCode;
    }
}
