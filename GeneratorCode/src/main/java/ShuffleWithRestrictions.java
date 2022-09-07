import com.fasterxml.jackson.databind.ObjectMapper;
import io.ReaderManager;
import io.WriterManager;
import reel.ReelSetsCollection;
import shuffler.FlatGenerator;
import shuffler.ShuffleGenerator;

import java.io.IOException;
import java.util.function.Predicate;

public class ShuffleWithRestrictions {

    private static final String cmdName = " gen.jar";

    private static final String usageMsg =
            "Usage: %n %s <src> <dest> %n" +
                    "or" +
                    "%n %s <dst> %n" +
                    "or" +
                    "%n %s %n" +
                    "Default source file is %s%n";
    private static final String defaultSourcePath = "./reelDefinitions.json";

    public static void main(String... args) {
        int argc = args.length;

        Predicate<String> chkArg = x -> x.equalsIgnoreCase("-info") ||
                x.equalsIgnoreCase("-i") ||
                x.equalsIgnoreCase("-usage") ||
                x.equalsIgnoreCase("-u") ||
                x.equalsIgnoreCase("-manual") ||
                x.equalsIgnoreCase("-man");

        if (argc == 1 && chkArg.test(args[0])) {
            System.out.printf(usageMsg, cmdName, cmdName, cmdName, defaultSourcePath);
            return;
        }

        String srcPath = defaultSourcePath;
        String destPath = null;

        if (args.length == 1) {
            destPath = args[0];
        } else if (args.length == 2) {
            srcPath = args[0];
            destPath = args[1];
        } else if (args.length > 2) {
            System.out.println("Too much arguments");
            System.out.printf(usageMsg, cmdName, cmdName, cmdName, defaultSourcePath);
            return;
        }

        ObjectMapper mapper = new ObjectMapper();

        // TemplateJson.getTemplate(mapper);

        ReelSetsCollection reelSetsCollection;
        try {
            reelSetsCollection = ReaderManager.readFile(srcPath, mapper);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (reelSetsCollection == null) {
            throw new RuntimeException(String.format("Invalid content in file %s", srcPath));
        }

        StringBuilder sb = new StringBuilder();

        if (reelSetsCollection.isFlat()) {
            FlatGenerator.generateFlatReels(reelSetsCollection, sb);
            return;
        }

        ShuffleGenerator.generateStackedReelsWithRestrictions(reelSetsCollection, sb);

        if (destPath != null) {
            WriterManager.writeResults(destPath, sb);
        } else {
            System.out.println(sb);
        }
    }
}
