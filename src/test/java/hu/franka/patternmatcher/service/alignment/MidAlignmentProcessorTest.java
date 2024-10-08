package hu.franka.patternmatcher.service.alignment;

import static org.junit.jupiter.api.Assertions.*;

import hu.franka.patternmatcher.exception.ApplicationException;
import hu.franka.patternmatcher.model.config.AlignmentConfig;
import hu.franka.patternmatcher.model.config.MidConfig;
import hu.franka.patternmatcher.util.MappingUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


class MidAlignmentProcessorTest {

    private MidAlignmentProcessor processor;
    private AlignmentConfig config;

    @BeforeEach
    void setUp() {
        processor = new MidAlignmentProcessor();
        config = new AlignmentConfig();
    }

    @Test
    void test_ValidSequencesWithMatchingInfix() throws ApplicationException {
        List<String> sequences = List.of("ACTCACGACCACTAACTAGCAATACGATCG", "CAGTAAGCGATCAGACAGTACAGACGTACA", "ACTCACGACCACTAACTGGCAATACGATCG", "AGACAACATCAGATCGCAAGACGACAGATA");

        Map<String, MidConfig> midConfig = new HashMap<>();
        MidConfig midConfig1 = new MidConfig();
        midConfig1.setInfix("CACTAACT");

        MidConfig midConfig2 = new MidConfig();
        midConfig2.setInfix("CAGACAGT");

        midConfig.put("group1", midConfig1);
        midConfig.put("group2", midConfig2);
        config.setMidAlignment(midConfig);

        Map<String, StringBuilder> result = processor.run(sequences, config);

        assertEquals(3, result.size());
        assertTrue(result.containsKey("group1"));
        assertTrue(result.containsKey("group2"));
        assertTrue(result.containsKey(MappingUtil.UNMATCHED_KEY));

        assertEquals("ACTCACGACCACTAACTAGCAATACGATCG ACTCACGACCACTAACTGGCAATACGATCG ", result.get("group1").toString());
        assertEquals("CAGTAAGCGATCAGACAGTACAGACGTACA ", result.get("group2").toString());
        assertEquals("AGACAACATCAGATCGCAAGACGACAGATA ", result.get(MappingUtil.UNMATCHED_KEY).toString());
    }

    @Test
    void test_SequencesWithoutMatchingInfix() throws ApplicationException {
        List<String> sequences = List.of("CAGTAAGCGATCAGACAGTACAGACGTACA", "ACTCACGACCACTAACTAGCAATACGATCG");

        Map<String, MidConfig> midConfig = new HashMap<>();
        MidConfig midConfig1 = new MidConfig();
        midConfig1.setInfix("infix");

        midConfig.put("group1", midConfig1);
        config.setMidAlignment(midConfig);

        Map<String, StringBuilder> result = processor.run(sequences, config);

        assertEquals(1, result.size());
        assertTrue(result.containsKey(MappingUtil.UNMATCHED_KEY));

        assertEquals("CAGTAAGCGATCAGACAGTACAGACGTACA ACTCACGACCACTAACTAGCAATACGATCG ", result.get(MappingUtil.UNMATCHED_KEY).toString());
    }

    @Test
    void test_EmptyMidAlignmentConfig() {
        List<String> sequences = List.of("CAGTAAGCGATCAGACAGTACAGACGTACA", "ACTCACGACCACTAACTAGCAATACGATCG");
        config.setMidAlignment(Collections.emptyMap());
        assertThrows(ApplicationException.class, () -> processor.run(sequences, config));
    }

    @Test
    void test_NullMidAlignmentConfig() {
        List<String> sequences = List.of("CAGTAAGCGATCAGACAGTACAGACGTACA", "ACTCACGACCACTAACTAGCAATACGATCG");
        config.setMidAlignment(null);
        assertThrows(ApplicationException.class, () -> processor.run(sequences, config));
    }
}
