package dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import reel.ReelSetEvo;

import java.util.Arrays;
import java.util.List;

public class TemplateJsonReelSetsEvo {

    public static void getTemplate(ObjectMapper om) {
        final ReelSetEvo reelSetEvo1 = new ReelSetEvo()
                .setSetName("testName1")
                .setReelSet(
                        Arrays.asList(
                                Arrays.asList(1, 2, 3),
                                Arrays.asList(2, 3, 4),
                                Arrays.asList(4, 5, 6)
                        )
                );

        final ReelSetEvo reelSetEvo2 = new ReelSetEvo()
                .setSetName("testName2")
                .setReelSet(
                        Arrays.asList(
                                Arrays.asList(7, 8, 9),
                                Arrays.asList(8, 9, 10),
                                Arrays.asList(9, 10, 11)
                        )
                );

        final List<ReelSetEvo> listReelSetEvo = Arrays.asList(
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
