package hu.franka.patternmatcher.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MappingUtilTest {

    private Map<String, StringBuilder> map;

    @BeforeEach
    void setUp() {
        map = new HashMap<>();
    }

    @Test
    void test_PutValue_NewKey() {
        MappingUtil.putValue(map, "key1", "value1");

        assertEquals("value1 ", map.get("key1").toString());
    }

    @Test
    void test_PutValue_ExistingKey() {
        map.put("key1", new StringBuilder("initialValue "));
        MappingUtil.putValue(map, "key1", "appendedValue");

        assertEquals("initialValue appendedValue ", map.get("key1").toString());
    }

    @Test
    void test_PutValue_UnmatchedKey() {
        MappingUtil.putValue(map, MappingUtil.UNMATCHED_KEY, "unmatchedValue");

        assertEquals("unmatchedValue ", map.get(MappingUtil.UNMATCHED_KEY).toString());
    }

    @Test
    void test_PutValue_MultipleValuesForSameKey() {
        MappingUtil.putValue(map, "key2", "firstValue");
        MappingUtil.putValue(map, "key2", "secondValue");

        assertEquals("firstValue secondValue ", map.get("key2").toString());
    }

    @Test
    void test_PutValue_EmptyKey() {
        MappingUtil.putValue(map, "", "emptyKeyValue");

        assertEquals("emptyKeyValue ", map.get("").toString());
    }
}