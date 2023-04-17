package dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import reel.ReelSetEvo;

import java.util.List;

public class TemplateJsonReelSetsEvo {

    public static void getTemplate(ObjectMapper om) {
        final ReelSetEvo reelSetEvo1 = new ReelSetEvo()
                .setSetName("testName1")
                .setReelSet(
                        List.of(
                                List.of(1, 2, 3),
                                List.of(2, 3, 4),
                                List.of(4, 5, 6)
                        )
                );

        final ReelSetEvo reelSetEvo2 = new ReelSetEvo()
                .setSetName("testName2")
                .setReelSet(
                        List.of(
                                List.of(7, 8, 9),
                                List.of(8, 9, 10),
                                List.of(9, 10, 11)
                        )
                );

        final List<ReelSetEvo> listReelSetEvo = List.of(
                reelSetEvo1,
                reelSetEvo2
        );

        try {
            System.out.println(om.writerWithDefaultPrettyPrinter().writeValueAsString(listReelSetEvo));
            System.out.println();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
