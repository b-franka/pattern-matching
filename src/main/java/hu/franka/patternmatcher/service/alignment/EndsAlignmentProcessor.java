package hu.franka.patternmatcher.service.alignment;

import hu.franka.patternmatcher.exception.ApplicationException;
import hu.franka.patternmatcher.model.ExitCode;
import hu.franka.patternmatcher.model.config.AlignmentConfig;
import hu.franka.patternmatcher.util.MappingUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EndsAlignmentProcessor implements AlignmentProcessor {
    @Override
    public Map<String, StringBuilder> run(List<String> sequences, AlignmentConfig config) throws ApplicationException {
        var endsAlignmentConfig = config.getEndsAlignment();

        if (null == endsAlignmentConfig || endsAlignmentConfig.isEmpty()) throw new ApplicationException("Invalid config.", ExitCode.INVALID_INPUT_VALUES);

        Map<String, StringBuilder> result = new HashMap<>();
        sequences.forEach(seq -> {
            boolean isMatched = false;

            for (var entry : endsAlignmentConfig.entrySet()) {
                var value = entry.getValue();
                String key = entry.getKey();

                if (matchesPattern(value.getPrefix(), value.getPostfix(), seq)) {
                    MappingUtil.putValue(result, key, seq);
                    isMatched = true;
                }
            }

            if (!isMatched) {
                MappingUtil.putValue(result, MappingUtil.UNMATCHED_KEY, seq);
            }
        });

        return result;
    }

    private boolean matchesPattern(String prefix, String postfix, String sequence) {
        return sequence.startsWith(prefix) && sequence.endsWith(postfix);
    }
}
