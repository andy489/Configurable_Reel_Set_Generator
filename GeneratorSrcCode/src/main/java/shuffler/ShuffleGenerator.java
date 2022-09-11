package shuffler;

import reel.ReelSet;
import reel.ReelSetsCollection;
import reel.Restriction;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ShuffleGenerator {
    private static final Integer RETRIES = 8;

    public static void generateStackedReelsWithRestrictions(ReelSetsCollection reelSetsCollection, StringBuilder sb){
        final int SIZE_1 = reelSetsCollection.getReelSets().size();
        for (int i = 0; i < SIZE_1; i++) {
            ReelSet currReelSet = reelSetsCollection.getReelSets().get(i);
            List<Restriction> currRestrictions = currReelSet.getRestrictions();

            final int SIZE_2 = currReelSet.getTilesCounts().size();
            final int SIZE_3 = currReelSet.getRestrictions().size();

            sb.append("Reel Set #").append(i).append(":").append(System.lineSeparator());
            for (int j = 0; j < SIZE_2; j++) {
                Map<Integer, Integer> tilesCnt = new TreeMap<>();

                fillMapWithTilesIDsCounts(tilesCnt, currReelSet.getTilesCounts().get(j));

                LinkedList<Integer> res = RestrictionsApplier.get(
                        tilesCnt,
                        currRestrictions.get(j % SIZE_3)
                );

                int currTries = 0;
                while (res == null) {
                    res = RestrictionsApplier.get(tilesCnt, currRestrictions.get(j % SIZE_3));
                    currTries++;
                    if (currTries == RETRIES) {
                        break;
                    }
                }

                if (res != null) {
                    sb.append(res).append(",").append(System.lineSeparator());
                } else {
                    sb.append("[Failed to shuffle this Reel]").append(System.lineSeparator());
                }
            }
            sb.append(System.lineSeparator());
        }
    }
    private static void fillMapWithTilesIDsCounts(Map<Integer, Integer> tilesCnt, List<Integer> CURR_TILES_COUNTS) {
        final int SIZE = CURR_TILES_COUNTS.size();

        for (int i = 1; i <= SIZE; i++) {
            tilesCnt.put(i, CURR_TILES_COUNTS.get(i - 1));
        }
    }
}
