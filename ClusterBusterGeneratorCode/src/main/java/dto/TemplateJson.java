package dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import reel.ReelSet;
import reel.ReelSetsCollection;
import reel.Restriction;

import java.util.List;

public class TemplateJson {
    public static void getTemplate(ObjectMapper mapper) {
        List<List<Integer>> reelSet1 = List.of(
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

        Restriction r11 = new Restriction(3, 3, 3);
        Restriction r12 = new Restriction(1, 1, 2);

        List<List<Integer>> reelSet2 = List.of(
                List.of(0, 12, 12, 0, 10, 10, 0, 6, 6),
                List.of(12, 0, 12, 10, 0, 10, 6, 0, 6),
                List.of(12, 12, 0, 10, 10, 0, 6, 6, 0),
                List.of(0, 12, 12, 0, 10, 10, 0, 6, 6),
                List.of(12, 0, 12, 10, 0, 10, 6, 0, 6),
                List.of(12, 12, 0, 10, 10, 0, 6, 6, 0)
        );

        Restriction r21 = new Restriction(4, 4, 4);
        Restriction r22 = new Restriction(2, 2, 4);

        ReelSet restrictedReelSet1 = new ReelSet(reelSet1, List.of(r11, r12));
        ReelSet restrictedReelSet2 = new ReelSet(reelSet1, List.of(r11, r12));

        ReelSetsCollection reelSetCol = new ReelSetsCollection(
                List.of(restrictedReelSet1, restrictedReelSet2), false
        );


        try {
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(reelSetCol));
            System.out.println();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
