package hu.franka.patternmatcher.util;

public class UsagePrinter {
    public static void printUsage() {
        System.out.println("Commands:");
        System.out.println(" run   - run the pattern matching process");
        System.out.println("    Options for run command:");
        System.out.println("        -sequence, -s           - absolute path to the sequencing data file");
        System.out.println("        -config, -c             - absolute path to the configuration file");
        System.out.println("        -outputPathPrefix, -o   - path prefix to the output files");
        System.out.println("        -alignment, -a          - name of the alignment (one of endsAlignment or midAlignment or bestAlignment)");
        System.out.println(" help   - print usage");
    }
}
