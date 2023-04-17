package shuffler;

import reel.ReelSet;
import reel.ReelSetsCollectionData;

import java.util.List;

public class FlatGenerator {
    private static void genReel(List<Integer> reelCounts, StringBuilder sb) {
        sb.append("\t[");

        final int SIZE_1 = reelCounts.size();

        for (int i = 0; i < SIZE_1; i++) {
            final int SIZE_2 = reelCounts.get(i);

            sb.append((i + 1 + ",")
                    .repeat(Math.max(0, SIZE_2)));
        }

        sb.delete(sb.length() - 1, sb.length())
                .append("],")
                .append(System.lineSeparator());
    }

    private static void genReels(List<List<Integer>> reels, StringBuilder sb) {

        sb.append("\t\t")
                .append("\"reelSet\": [")
                .append(System.lineSeparator());

        for (List<Integer> r : reels) {

            sb.append("\t\t");

            genReel(r, sb);
        }

        sb.delete(sb.length() - 2, sb.length())
                .append(System.lineSeparator())
                .append("\t\t")
                .append("]")
                .append(System.lineSeparator());
    }

    public static void generateFlatReels(ReelSetsCollectionData reelSetsCollection, StringBuilder sb) {
        List<ReelSet> reelSets = reelSetsCollection.getReelSets();

        sb.append(System.lineSeparator())
                .append("[")
                .append(System.lineSeparator());

        int k = 0;
        for (ReelSet reelSet : reelSets) {

            sb.append("\t").append("{")
                    .append(System.lineSeparator())
                    .append("\t\t")
                    .append("\"setName\": \"ReelSet#")
                    .append(k)
                    .append("\",")
                    .append(System.lineSeparator());

            genReels(reelSet.getTilesCounts(), sb);

            sb.append("\t")
                    .append("},")
                    .append(System.lineSeparator());

            k++;
        }

        sb.delete(sb.length() - 2, sb.length())
                .append(System.lineSeparator())
                .append("]");
    }
}
