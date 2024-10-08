package hu.franka.patternmatcher.service.alignment;

import hu.franka.patternmatcher.model.AlignmentType;

public class AlignmentProcessorFactory {

    public static AlignmentProcessor getAlignmentProcessor(AlignmentType alignmentType) {
        return switch (alignmentType) {
            case BEST -> new BestAlignmentProcessor();
            case END -> new EndsAlignmentProcessor();
            case MID -> new MidAlignmentProcessor();
        };
    }
}
