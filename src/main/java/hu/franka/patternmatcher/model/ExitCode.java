package hu.franka.patternmatcher.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ExitCode {

    OK(0), FUNCTIONAL_ERROR(1), INVALID_ARGS(2), INVALID_INPUT_VALUES(3);

    @Getter
    private int code;
}
