package dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import reel.ReelSet;
import reel.ReelSetsCollectionData;
import reel.Restriction;
import wrapper.ConvertWrapper;

import java.util.Arrays;
import java.util.List;

public class TemplateJsonReelDefinitions {
    public static void getTemplate(ObjectMapper om) {
        final String companyName = "company abb"; // "evo" | "cay"
        final String mapName = "game abb";
        final String gameId = "123";

        final String strategy = "shuffle"; // "flat" | "shuffle"
        final String output = "file"; // "file" | "stdout"
        final String resultFilePath = "./result.txt";

        final ConvertWrapper convert = new ConvertWrapper()
                .setConfirm(false)
                .setToCom("company abb")
                .setSrc("./result.txt")
                .setDest("./result.txt");

        final List<List<Integer>> reelSet1 = Arrays.asList(
                Arrays.asList(0, 12, 12, 0, 10, 10, 0, 6, 6),
                Arrays.asList(12, 0, 12, 10, 0, 10, 6, 0, 6),
                Arrays.asList(12, 12, 0, 10, 10, 0, 6, 6, 0),
                Arrays.asList(0, 12, 12, 0, 10, 10, 0, 6, 6),
                Arrays.asList(12, 0, 12, 10, 0, 10, 6, 0, 6),
                Arrays.asList(12, 12, 0, 10, 10, 0, 6, 6, 0),
                Arrays.asList(0, 12, 12, 0, 10, 10, 0, 6, 6),
                Arrays.asList(12, 0, 12, 10, 0, 10, 6, 0, 6),
                Arrays.asList(12, 12, 0, 10, 10, 0, 6, 6, 0)
        );

        final Restriction r11 = new Restriction(3, 3, 3);
        final Restriction r12 = new Restriction(1, 1, 2);

        final List<List<Integer>> reelSet2 = Arrays.asList(
                Arrays.asList(0, 12, 12, 0, 10, 10, 0, 6, 6),
                Arrays.asList(12, 0, 12, 10, 0, 10, 6, 0, 6),
                Arrays.asList(12, 12, 0, 10, 10, 0, 6, 6, 0),
                Arrays.asList(0, 12, 12, 0, 10, 10, 0, 6, 6),
                Arrays.asList(12, 0, 12, 10, 0, 10, 6, 0, 6),
                Arrays.asList(12, 12, 0, 10, 10, 0, 6, 6, 0)
        );

        final Restriction r21 = new Restriction(4, 4, 4);
        final Restriction r22 = new Restriction(2, 2, 4);

        final ReelSet restrictedReelSet1 = new ReelSet(reelSet1, Arrays.asList(r11, r12));
        final ReelSet restrictedReelSet2 = new ReelSet(reelSet2, Arrays.asList(r21, r22));

        final List<ReelSet> reelSets = Arrays.asList(restrictedReelSet1, restrictedReelSet2);

        ReelSetsCollectionData reelSetCol = new ReelSetsCollectionData()
                .setMapName(mapName)
                .setGameId(gameId)
                .setStrategy(strategy)
                .setOutput(output)
                .setResultFilePath(resultFilePath)
                .setReelSets(reelSets)
                .setConvert(convert);
        try {
            System.out.println(om.writerWithDefaultPrettyPrinter().writeValueAsString(reelSetCol));
            System.out.println();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
