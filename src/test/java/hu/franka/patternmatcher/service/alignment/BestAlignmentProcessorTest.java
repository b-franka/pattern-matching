package hu.franka.patternmatcher.service.alignment;

import hu.franka.patternmatcher.exception.ApplicationException;
import hu.franka.patternmatcher.model.config.AlignmentConfig;
import hu.franka.patternmatcher.model.config.MidConfig;
import hu.franka.patternmatcher.util.MappingUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BestAlignmentProcessorTest {

    private BestAlignmentProcessor processor;
    private AlignmentConfig config;

    @BeforeEach
    void setUp() {
        processor = new BestAlignmentProcessor();
        config = new AlignmentConfig();
    }

    @Test
    void test_ValidSequencesWithMatchingInfix() throws ApplicationException {
        List<String> sequences = List.of("ACTCACGACCACTAACTAGCAATACGATCG", "CAGTAAGCGATCAGACAGTACAGACGTACA", "ACTCACGACCACTAACTGGCAATACGATCG", "AGACAACATCAGATCGCAAGACGACAGATA");

        Map<String, MidConfig> midConfig = new HashMap<>();
        MidConfig midConfig1 = new MidConfig();
        midConfig1.setInfix("CTATCTAGCAAT");

        midConfig.put("group1", midConfig1);

        config.setBestAlignment(midConfig);

        Map<String, StringBuilder> result = processor.run(sequences, config);

        assertEquals(2, result.size());
        assertTrue(result.containsKey("group1"));
        assertTrue(result.containsKey(MappingUtil.UNMATCHED_KEY));

        assertEquals("ACTCACGACCACTAACTAGCAATACGATCG ", result.get("group1").toString());
        assertEquals("CAGTAAGCGATCAGACAGTACAGACGTACA ACTCACGACCACTAACTGGCAATACGATCG AGACAACATCAGATCGCAAGACGACAGATA ", result.get(MappingUtil.UNMATCHED_KEY).toString());
    }
}