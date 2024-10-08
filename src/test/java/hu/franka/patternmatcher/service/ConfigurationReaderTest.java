package hu.franka.patternmatcher.service;

import hu.franka.patternmatcher.exception.ApplicationException;
import hu.franka.patternmatcher.model.config.AlignmentConfig;
import hu.franka.patternmatcher.model.config.EndConfig;
import hu.franka.patternmatcher.model.config.MidConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ConfigurationReaderTest {

    public static final String CONFIG_FILE_DIR = "configFile";
    public static final String SAMPLE_CONF_FILE_NAME = "sample.conf";
    private ConfigurationReader configurationReader;
    private Path filePath;

    @BeforeEach
    void setUp() throws Exception {
        URI uri = ClassLoader.getSystemResource(CONFIG_FILE_DIR).toURI();
        String mainPath = Paths.get(uri).toString();
        filePath = Paths.get(mainPath, SAMPLE_CONF_FILE_NAME);

        configurationReader = new ConfigurationReader(filePath);
    }

    @Test
    void test_ReadConfigSuccessfully() throws ApplicationException {
        AlignmentConfig config = configurationReader.readConfig(AlignmentConfig.class);

        assertNotNull(config);
        Map<String, EndConfig> endsAlignmentConfigMap = config.getEndsAlignment();
        assertNotNull(endsAlignmentConfigMap);

        assertEquals("ACTCACG", endsAlignmentConfigMap.get("group1").getPrefix());
        assertEquals("ACGATCG", endsAlignmentConfigMap.get("group1").getPostfix());

        assertEquals("CAGTAAG", endsAlignmentConfigMap.get("group2").getPrefix());
        assertEquals("ACGTACA", endsAlignmentConfigMap.get("group2").getPostfix());

        Map<String, MidConfig> midAlignmentConfigMap = config.getMidAlignment();
        assertNotNull(midAlignmentConfigMap);

        assertEquals("CACTAACT", midAlignmentConfigMap.get("group1").getInfix());

        assertEquals("CAGACAGT", midAlignmentConfigMap.get("group2").getInfix());

        Map<String, MidConfig> bestAlignmentConfigMap = config.getBestAlignment();
        assertNotNull(bestAlignmentConfigMap);

        assertEquals("CTATCTAGCAAT", bestAlignmentConfigMap.get("group1").getInfix());
    }
}