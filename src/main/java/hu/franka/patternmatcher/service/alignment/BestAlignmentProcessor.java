package hu.franka.patternmatcher.service.alignment;

import hu.franka.patternmatcher.exception.ApplicationException;
import hu.franka.patternmatcher.model.ExitCode;
import hu.franka.patternmatcher.model.config.AlignmentConfig;
import hu.franka.patternmatcher.util.MappingUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BestAlignmentProcessor implements AlignmentProcessor {
    @Override
    public Map<String, StringBuilder> run(List<String> sequences, AlignmentConfig config) throws ApplicationException {
        var bestAlignmentConfig = config.getBestAlignment();
        if (bestAlignmentConfig == null || bestAlignmentConfig.isEmpty()) {
            throw new ApplicationException("Invalid config.", ExitCode.INVALID_INPUT_VALUES);
        }

        Map<String, StringBuilder> result = new HashMap<>();
        var unmatchedKey = MappingUtil.UNMATCHED_KEY;

        bestAlignmentConfig.forEach((key, value) -> {
            int max = 0;
            String maxSeq = null;
            for (String sequence : sequences) {
                int length = calculateLongestMatchingLength(value.getInfix(), sequence);
                if (length > max) {
                    max = length;
                    maxSeq = sequence;
                } else {
                    MappingUtil.putValue(result, unmatchedKey, sequence);
                }
            }
            MappingUtil.putValue(result, key, maxSeq);
        });
        return result;
    }

    private int calculateLongestMatchingLength(String pattern, String sequence) {
        int m = pattern.length();
        int n = sequence.length();

        int[] prev = new int[n + 1];
        int[] curr = new int[n + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (pattern.charAt(i - 1) == sequence.charAt(j - 1)) {
                    curr[j] = prev[j - 1] + 1;
                } else {
                    curr[j] = Math.max(prev[j], curr[j - 1]);
                }
            }
            int[] temp = prev;
            prev = curr;
            curr = temp;
        }

        return prev[n];
    }

}
