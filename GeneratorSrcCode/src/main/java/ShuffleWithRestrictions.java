import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.ReaderManager;
import io.WriterManager;
import reel.ReelSetEvo;
import reel.ReelSetsCollectionData;
import shuffler.FlatGenerator;
import shuffler.ShuffleGenerator;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

public class ShuffleWithRestrictions {

    private static final String cmdName = " gen.jar";

    private static final String usageMsg =
            "\u001B[34mUsage: %n \u001B[32m%s \u001B[33m<src> <dest> %n" +
                    "\u001B[0mor" +
                    "%n \u001B[32m%s \u001B[33m<dst> %n" +
                    "\u001B[0mor" +
                    "%n \u001B[32m%s %n" +
                    "\u001B[0mDefault source file is \u001B[33m%s\u001B[0m%n";
    private static final String defaultSourcePath = "./reelDefinitions.json";
    private static final String defaultDestPath = "./reelDefinitions.json";

    public static void main(String... args) {
        final int argc = args.length;

        final Predicate<String> chkArg = x -> x.equalsIgnoreCase("-info") ||
                x.equalsIgnoreCase("-i") ||
                x.equalsIgnoreCase("-usage") ||
                x.equalsIgnoreCase("-u") ||
                x.equalsIgnoreCase("-manual") ||
                x.equalsIgnoreCase("-man");

        if (argc == 1 && chkArg.test(args[0])) {
            System.out.printf(usageMsg, cmdName, cmdName, cmdName, defaultSourcePath);
            return;
        }

        String srcFilePath = defaultSourcePath;
        String targetFilePath = defaultDestPath;

        if (args.length == 1) {
            targetFilePath = args[0];
        } else if (args.length == 2) {
            srcFilePath = args[0];
            targetFilePath = args[1];
        } else if (args.length > 2) {
            System.out.println("Too much arguments");
            System.out.printf(usageMsg, cmdName, cmdName, cmdName, defaultSourcePath);
            return;
        }

        ObjectMapper om = new ObjectMapper();

        // TemplateJsonReelDefinitions.getTemplate(om);

        ReelSetsCollectionData reelSetsCollectionData;

        try {
            reelSetsCollectionData = ReaderManager.readFile(srcFilePath, om);
        } catch (IOException e) {
            System.err.println("Check src file name, location or content.");
            throw new RuntimeException(e);
        }

        if (reelSetsCollectionData == null) {
            throw new RuntimeException(String.format("Invalid content in file %s", srcFilePath));
        }

        final Boolean convert = reelSetsCollectionData.getConvert();

        if (!convert && reelSetsCollectionData.getOutput().equals("file")) {
            if (reelSetsCollectionData.getResultFilePath() != null) {
                targetFilePath = reelSetsCollectionData.getResultFilePath();
            }
        }

        StringBuilder sb = new StringBuilder();

        // Fill sb
        if (!convert) {
            if (reelSetsCollectionData.getStrategy().equals("flat")) {
                FlatGenerator.generateFlatReels(reelSetsCollectionData, sb); // FLAT GEN
            } else {
                ShuffleGenerator.generateStackedReelsWithRestrictions(reelSetsCollectionData, sb); // SHUFFLE GEN
            }
        }

        if (!convert) {
            if (reelSetsCollectionData.getCom().equals("evo")) {

                if (reelSetsCollectionData.getOutput().equals("stdout")) {
                    System.out.println(sb);
                } else {
                    WriterManager.writeResults(targetFilePath, sb);
                }
            } else {
                TypeReference<List<ReelSetEvo>> typeRef = new TypeReference<>() {
                };
                List<ReelSetEvo> reelSetsEvo = null;
                try {
                    reelSetsEvo = om.readValue(sb.toString(), typeRef);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                StringBuilder sb2 = null;
                // System.out.println(reelSetsEvo);

                sb2 = convertToCay(reelSetsCollectionData, reelSetsEvo);

                if (reelSetsCollectionData.getOutput().equals("stdout")) {
                    System.out.println(sb2);
                } else {
                    WriterManager.writeResults(targetFilePath, sb2);
                }
            }
        } else {
            TypeReference<List<ReelSetEvo>> typeRef = new TypeReference<>() {
            };
            List<ReelSetEvo> reelSetsEvo = null;
            String convertSrc = reelSetsCollectionData.getConvertSrc();
            try {
                reelSetsEvo = om.readValue(new File(convertSrc), typeRef);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            StringBuilder sb2 = null;
            // System.out.println(reelSetsEvo);

            sb2 = convertToCay(reelSetsCollectionData, reelSetsEvo);

            WriterManager.writeResults(reelSetsCollectionData.getConvertDest(), sb2);

        }
    }


    private static StringBuilder convertToCay(ReelSetsCollectionData reelSetsCollection, List<ReelSetEvo> reelSetsEvo) {
        StringBuilder sb2 = new StringBuilder(System.lineSeparator());

        final String mapName = reelSetsCollection.getMapName();
        final String gameId = reelSetsCollection.getGameId();

        final int SETS_COUNT = reelSetsEvo.size();

        for (int i = 0; i < SETS_COUNT; i++) {

            final int SET_SIZE = reelSetsEvo.get(i).getReelSet().size();
            for (int j = 0; j < SET_SIZE; j++) {

                final int REEL_SIZE = reelSetsEvo.get(i).getReelSet().get(j).size();
                for (int k = 0; k < REEL_SIZE; k++) {

                    Integer currentTile = reelSetsEvo.get(i).getReelSet().get(j).get(k);

                    sb2.append("\"").append(mapName).append("\",")
                            .append("\"").append(j).append("\",")
                            .append("\"").append(k).append("\",")
                            .append("\"").append(gameId)
                            .append(currentTile < 10 ? "0" + currentTile : currentTile)
                            .append("\"")
                            .append(System.lineSeparator());
                }
            }
        }

        return sb2;
    }
}
