package hu.franka.patternmatcher.model.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
public class AlignmentConfig {
    private Map<String, EndConfig> endsAlignment;
    private Map<String, MidConfig> midAlignment;
    private Map<String, MidConfig> bestAlignment;
}
