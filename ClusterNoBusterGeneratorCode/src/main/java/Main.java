import reel.ReelSet;
import shuffle.ShuffleWithRestrictions;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Main {
    private static void fillMapWithTilesIDsCounts(
            Map<Integer, Integer> tilesCnt,
            List<Integer> CURR_TILES_COUNTS
    ) {
        final int SIZE = CURR_TILES_COUNTS.size();

        for (int i = 1; i <= SIZE; i++) {
            tilesCnt.put(i, CURR_TILES_COUNTS.get(i - 1));
        }
    }

    public static void main(String... args) {
        final int MIN_TILES_CNT_WIN = 5;

        ShuffleWithRestrictions shuffler1 = new ShuffleWithRestrictions(
                3,
                3,
                3
        );

        ShuffleWithRestrictions shuffler2 = new ShuffleWithRestrictions(
                1,
                1,
                2
        );

//        ShuffleWithRestrictions shuffler1 = new ShuffleWithRestrictions(
//                4,
//                4,
//                2
//        );
//
//        ShuffleWithRestrictions shuffler2 = new ShuffleWithRestrictions(
//                2,
//                2,
//                4
//        );

        int m = 0;

        for (final List<Integer> CURR_TILES_CNT : ReelSet.TILES_COUNTS) {
            Map<Integer, Integer> tilesCnt = new TreeMap<>();

            fillMapWithTilesIDsCounts(tilesCnt, CURR_TILES_CNT);

            LinkedList<Integer> res;

            if (m++ % 2 == 0) {
                res = shuffler1.getShuffledStackedReelWithDistance(tilesCnt);

                if (res == null) {
                    throw new IllegalStateException("Failed to shuffle");
                }
            } else {
                res = shuffler2.getShuffledStackedReelWithDistance(tilesCnt);
                if (res == null) {
                    throw new IllegalStateException("Failed to shuffle");
                }
            }

            System.out.println(res);
        }
    }
}
