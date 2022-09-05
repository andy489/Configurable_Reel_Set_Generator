package shuffler;

import reel.ReelSet;
import reel.ReelSetsCollection;

import java.util.List;

public class FlatGenerator {
    private static void genReel(List<Integer> reelCounts, StringBuilder sb) {
        sb.append("[");

        final int SIZE_1 = reelCounts.size();
        for (int i = 0; i < SIZE_1; i++) {
            final int SIZE_2 = reelCounts.get(i);

            sb.append((i + 1 + ", ").repeat(Math.max(0, SIZE_2)));
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append("],").append(System.lineSeparator());
    }

    private static void genReels(List<List<Integer>> reels, StringBuilder sb) {
        for (List<Integer> r : reels) {
            genReel(r, sb);
        }
        sb.append(System.lineSeparator());
    }

    public static void generateFlatReels(ReelSetsCollection reelSetsCollection, StringBuilder sb){
        List<ReelSet> reelSets = reelSetsCollection.getReelSets();

        int k = 0;
        for (ReelSet reelSet : reelSets) {
            sb.append("Reel Set #").append(k).append(":").append(System.lineSeparator());

            genReels(reelSet.getTilesCounts(), sb);
            k++;
        }

        System.out.println(sb);
    }
}
