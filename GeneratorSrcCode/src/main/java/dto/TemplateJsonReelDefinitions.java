package dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import reel.ReelSet;
import reel.ReelSetsCollectionData;
import reel.Restriction;

import java.util.List;

public class TemplateJsonReelDefinitions {
    public static void getTemplate(ObjectMapper om) {
        final String companyName = "cay"; // "evo" | "cay"
        final String mapName = "GAME_CODE";
        final String gameId = "123";

        final String strategyType = "shuffle"; // "flat" | "shuffle"
        final String output = "file"; // "file" | "stdout"
        final String sourceFilePath = "./reelDefinitions.json";
        final String resultFilePath = "./result.txt";

        final Boolean convert = false;
        final String convertSrc = "./result.txt";
        final String convertDest = "./result.txt";

        final List<List<Integer>> reelSet1 = List.of(
                List.of(0, 12, 12, 0, 10, 10, 0, 6, 6),
                List.of(12, 0, 12, 10, 0, 10, 6, 0, 6),
                List.of(12, 12, 0, 10, 10, 0, 6, 6, 0),
                List.of(0, 12, 12, 0, 10, 10, 0, 6, 6),
                List.of(12, 0, 12, 10, 0, 10, 6, 0, 6),
                List.of(12, 12, 0, 10, 10, 0, 6, 6, 0),
                List.of(0, 12, 12, 0, 10, 10, 0, 6, 6),
                List.of(12, 0, 12, 10, 0, 10, 6, 0, 6),
                List.of(12, 12, 0, 10, 10, 0, 6, 6, 0)
        );

        final Restriction r11 = new Restriction(3, 3, 3);
        final Restriction r12 = new Restriction(1, 1, 2);

        final List<List<Integer>> reelSet2 = List.of(
                List.of(0, 12, 12, 0, 10, 10, 0, 6, 6),
                List.of(12, 0, 12, 10, 0, 10, 6, 0, 6),
                List.of(12, 12, 0, 10, 10, 0, 6, 6, 0),
                List.of(0, 12, 12, 0, 10, 10, 0, 6, 6),
                List.of(12, 0, 12, 10, 0, 10, 6, 0, 6),
                List.of(12, 12, 0, 10, 10, 0, 6, 6, 0)
        );

        final Restriction r21 = new Restriction(4, 4, 4);
        final Restriction r22 = new Restriction(2, 2, 4);

        final ReelSet restrictedReelSet1 = new ReelSet(reelSet1, List.of(r11, r12));
        final ReelSet restrictedReelSet2 = new ReelSet(reelSet2, List.of(r21, r22));

        final List<ReelSet> reelSets = List.of(restrictedReelSet1, restrictedReelSet2);

        ReelSetsCollectionData reelSetCol = new ReelSetsCollectionData()
                .setCom(companyName)
                .setMapName(mapName)
                .setGameId(gameId)
                .setOutput(output)
                .setResultFilePath(resultFilePath)
                .setStrategy(strategyType)
                .setReelSets(reelSets)
                .setConvert(convert)
                .setConvertSrc(convertSrc)
                .setConvertDest(convertDest);

        try {
            System.out.println(om.writerWithDefaultPrettyPrinter().writeValueAsString(reelSetCol));
            System.out.println();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
