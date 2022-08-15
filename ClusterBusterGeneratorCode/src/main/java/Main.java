import reel.ReelSet;
import shuffle.ShuffleWithRestrictions;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Main extends ReelSet{
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

        ShuffleWithRestrictions shuffler1 = new ShuffleWithRestrictions(
                MIN_STACK_1,
                MAX_STACK_1,
                DIST_1
        );

        ShuffleWithRestrictions shuffler2 = new ShuffleWithRestrictions(
                MIN_STACK_2,
                MAX_STACK_2,
                DIST_2
        );

        int m = 0;

        for (final List<Integer> CURR_TILES_CNT : TILES_COUNTS) {
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
