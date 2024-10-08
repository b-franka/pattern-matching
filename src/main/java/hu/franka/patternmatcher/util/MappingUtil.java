package hu.franka.patternmatcher.util;

import java.util.Map;

public class MappingUtil {
    public static final String UNMATCHED_KEY = "unmatched";

    public static void putValue(Map<String, StringBuilder> map, String key, String valueInput) {
        map.computeIfAbsent(key, k -> new StringBuilder())
                .append(valueInput)
                .append(' ');
    }
}
