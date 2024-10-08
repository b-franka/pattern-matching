package hu.franka.patternmatcher.service.alignment;

import static org.junit.jupiter.api.Assertions.*;

import hu.franka.patternmatcher.exception.ApplicationException;
import hu.franka.patternmatcher.model.config.AlignmentConfig;
import hu.franka.patternmatcher.model.config.EndConfig;
import hu.franka.patternmatcher.util.MappingUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


class EndsAlignmentProcessorTest {

    private EndsAlignmentProcessor processor;
    private AlignmentConfig config;

    @BeforeEach
    void setUp() {
        processor = new EndsAlignmentProcessor();
        config = new AlignmentConfig();
    }

    @Test
    void test_ValidSequencesWithMatchingPrefixAndPostfix() throws ApplicationException {
        List<String> sequences = List.of("ACTCACGACCACTAACTAGCAATACGATCG", "CAGTAAGCGATCAGACAGTACAGACGTACA", "ACTCACGACCACTAACTGGCAATACGATCG", "AGACAACATCAGATCGCAAGACGACAGATA");

        Map<String, EndConfig> endsConfig = new HashMap<>();
        EndConfig endConfig1 = new EndConfig();
        endConfig1.setPrefix("ACTCACG");
        endConfig1.setPostfix("ACGATCG");

        EndConfig endConfig2 = new EndConfig();
        endConfig2.setPrefix("CAGTAAG");
        endConfig2.setPostfix("ACGTACA");

        endsConfig.put("group1", endConfig1);
        endsConfig.put("group2", endConfig2);
        config.setEndsAlignment(endsConfig);

        Map<String, StringBuilder> result = processor.run(sequences, config);

        assertEquals(3, result.size());
        assertTrue(result.containsKey("group1"));
        assertTrue(result.containsKey("group2"));

        assertEquals("ACTCACGACCACTAACTAGCAATACGATCG ACTCACGACCACTAACTGGCAATACGATCG ", result.get("group1").toString());
        assertEquals("CAGTAAGCGATCAGACAGTACAGACGTACA ", result.get("group2").toString());
        assertEquals("AGACAACATCAGATCGCAAGACGACAGATA ", result.get(MappingUtil.UNMATCHED_KEY).toString());
    }

    @Test
    void test_UnmatchedSequences() throws ApplicationException {
        List<String> sequences = List.of("ACTCACGACCACTAACTAGCAATACTATCG", "CAGTAAGCGATCAGACAGTACAGACGTACA", "ACTCACGACCACTAACTGGCAATACGATCG");

        Map<String, EndConfig> endsConfig = new HashMap<>();
        EndConfig endConfig = new EndConfig();
        endConfig.setPrefix("abc");
        endConfig.setPostfix("xyz");

        endsConfig.put("group1", endConfig);
        config.setEndsAlignment(endsConfig);

        Map<String, StringBuilder> result = processor.run(sequences, config);

        assertEquals(1, result.size());
        assertTrue(result.containsKey(MappingUtil.UNMATCHED_KEY));
        assertEquals("ACTCACGACCACTAACTAGCAATACTATCG CAGTAAGCGATCAGACAGTACAGACGTACA ACTCACGACCACTAACTGGCAATACGATCG ", result.get(MappingUtil.UNMATCHED_KEY).toString());
    }

    @Test
    void test_EmptyAlignmentConfig() {
        List<String> sequences = List.of("CAGTAAGCGATCAGACAGTACAGACGTACA", "ACTCACGACCACTAACTAGCAATACGATCG");
        config.setEndsAlignment(Collections.emptyMap());
        assertThrows(ApplicationException.class, () -> processor.run(sequences, config));
    }

    @Test
    void test_NullAlignmentConfig() {
        List<String> sequences = List.of("CAGTAAGCGATCAGACAGTACAGACGTACA", "ACTCACGACCACTAACTAGCAATACGATCG");
        config.setEndsAlignment(null);
        assertThrows(ApplicationException.class, () -> processor.run(sequences, config));
    }
}