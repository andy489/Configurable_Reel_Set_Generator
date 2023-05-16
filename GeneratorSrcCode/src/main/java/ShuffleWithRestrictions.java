import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import convert.CayConverter;
import convert.CountConverter;
import convert.EvoConverter;
import exception.NoSuchCompanyFormatException;
import exception.NotSupportedOutputMediaType;
import io.ReaderManager;
import io.WriterManager;
import reel.ReelSetEvo;
import reel.ReelSetsCollectionData;
import shuffler.FlatGenerator;
import shuffler.ShuffleGenerator;
import wrapper.ConvertWrapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ShuffleWithRestrictions {

    private static final String cmdName = "gen.jar";

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
            System.out.println("Arguments must be at most 2");
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

        final ConvertWrapper convert = reelSetsCollectionData.getConvert();

        if (!convert.isConfirm()) {
            StringBuilder sbEvo = new StringBuilder();

            if (reelSetsCollectionData.getStrategy().equals("flat")) {
                FlatGenerator.generateFlatReels(reelSetsCollectionData, sbEvo); // FLAT GEN
            } else {
                ShuffleGenerator.generateStackedReelsWithRestrictions(reelSetsCollectionData, sbEvo); // SHUFFLE GEN
            }

            if (reelSetsCollectionData.getOutput().equals("stdout")) {
                System.out.println(sbEvo);
            } else if (reelSetsCollectionData.getOutput().equals("file")) {
                WriterManager.writeResults(reelSetsCollectionData.getResultFilePath(), sbEvo);
            } else {
                throw new NotSupportedOutputMediaType("Invalid media type output. Please review reelDefinitions.json");
            }
        } else {
            if (reelSetsCollectionData.getConvert().getToCom().equals("count") ||
                    reelSetsCollectionData.getConvert().getToCom().equals("evo")) {
                List<ReelSetEvo> reelSetEvoList = new ArrayList<>();

                try (BufferedReader br = new BufferedReader(new FileReader(reelSetsCollectionData.getConvert().getSrc()))) {
                    String line = br.readLine();

                    ReelSetEvo currReelSet = null;
                    String currSetName = null;
                    while (line != null) {

                        String[] tokens = line.split(",");
                        if (tokens.length != 4) {
                            break;
//                            throw new NoSuchCompanyFormatException("Unrecognizable company format. Please review reelDefinitions.json");
                        }

                        if (currSetName == null || !currSetName.equals(tokens[0])) {
                            currSetName = tokens[0];
                            currReelSet = new ReelSetEvo().setSetName(currSetName)
                                    .setReelSet(new ArrayList<>());

                            reelSetEvoList.add(currReelSet);
                            for (int i = 0; i < 10; i++) {
                                currReelSet.getReelSet().add(new ArrayList<>());
                            }
                        }

                        currReelSet.getReelSet().get(Integer.parseInt(tokens[1].replaceAll("\"", ""))).add(Integer.parseInt(tokens[3].replaceAll("\"", "")) % 100);
                        line = br.readLine();

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // remove empty reels
                for (ReelSetEvo reelSetEvo : reelSetEvoList) {
                    reelSetEvo.setReelSet(reelSetEvo.getReelSet().stream().filter(r -> !r.isEmpty()).toList());
                }

                StringBuilder sb = null;
                if(reelSetsCollectionData.getConvert().getToCom().equals("count")) {
                    sb = CountConverter.convertToTileCounts(reelSetEvoList);
                } else {
                    sb = EvoConverter.convertToEvo(reelSetEvoList);
                }

                if (reelSetsCollectionData.getOutput().equals("stdout")) {
                    System.out.println(sb);
                } else if (reelSetsCollectionData.getOutput().equals("file")) {
                    WriterManager.writeResults(reelSetsCollectionData.getConvert().getDest(), sb);
                } else {
                    throw new NotSupportedOutputMediaType("Invalid media type output. Please review reelDefinitions.json");
                }
            } else if (reelSetsCollectionData.getConvert().getToCom().equals("cay")) {
                // WriterManager.writeResults(reelSetsCollectionData.getResultFilePath(), sbEvo);
                TypeReference<List<ReelSetEvo>> typeRef = new TypeReference<>() {
                };

                List<ReelSetEvo> reelSetsEvo = null;
                String convertSrc = reelSetsCollectionData.getConvert().getSrc();

                try {
                    reelSetsEvo = om.readValue(new File(convertSrc), typeRef);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                StringBuilder sbCay = CayConverter.convertToCay(reelSetsCollectionData, reelSetsEvo);

                if (reelSetsCollectionData.getOutput().equals("stdout")) {
                    System.out.println(sbCay);
                } else if (reelSetsCollectionData.getOutput().equals("file")) {
                    WriterManager.writeResults(reelSetsCollectionData.getConvert().getDest(), sbCay);
                } else {
                    throw new NotSupportedOutputMediaType("Invalid media type output. Please review reelDefinitions.json");
                }
            } else {
                throw new NotSupportedOutputMediaType("Invalid conversion type. Please review reelDefinitions.json");
            }
        }
    }

}
