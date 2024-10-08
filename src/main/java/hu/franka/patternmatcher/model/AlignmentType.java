package hu.franka.patternmatcher.model;

import hu.franka.patternmatcher.exception.ApplicationException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

@AllArgsConstructor
public enum AlignmentType {

    BEST("bestAlignment"), END("endsAlignment"), MID("midAlignment");

    @Getter
    private final String name;

    public static AlignmentType fromName(String name) throws ApplicationException {
        if (StringUtils.isEmpty(name)) throw new ApplicationException("Empty alignment type given.", ExitCode.INVALID_INPUT_VALUES);
        return Arrays.stream(AlignmentType.values()).filter(type -> name.equals(type.getName()))
                .findFirst()
                .orElseThrow(() -> new ApplicationException("Unknown alignment type specified: " + name, ExitCode.INVALID_INPUT_VALUES));
    }

}
