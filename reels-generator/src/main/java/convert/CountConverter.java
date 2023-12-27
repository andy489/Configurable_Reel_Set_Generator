package convert;

import reel.ReelSetEvo;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class CountConverter {
    public static StringBuilder convertToTileCounts(List<ReelSetEvo> reelSetsEvo) {

        StringBuilder sbCount = new StringBuilder("[").append(System.lineSeparator());

        for (ReelSetEvo rse : reelSetsEvo) {
            int largestTileId = 0;
            List<TreeMap<Integer, Integer>> tileCountMapList = new ArrayList<>();

            for (int i = 0; i < rse.getReelSet().size(); i++) {
                tileCountMapList.add(new TreeMap<>());
                for (int j = 0; j < rse.getReelSet().get(i).size(); j++) {
                    tileCountMapList.get(i).merge(rse.getReelSet().get(i).get(j), 1, Integer::sum);
                }

                largestTileId = Math.max(largestTileId, tileCountMapList.get(i).lastKey());
            }

            String setName = rse.getSetName().replaceAll("^\"", "").replaceAll("$\"", "");

            sbCount.append("\t{").append(System.lineSeparator())
                    .append("\t\t\"setName\": \"")
                    .append(setName)
                    .append("\",").append(System.lineSeparator())
                    .append("\t\t\"reelSetTileCounts\": [").append(System.lineSeparator());

            for (int i = 0; i < rse.getReelSet().size(); i++) {
                TreeMap<Integer, Integer> currTileCountsMap = tileCountMapList.get(i);
                sbCount.append("\t\t\t[");
                for (int t = 1; t <= largestTileId; t++) {
                    if (currTileCountsMap.containsKey(t)) {
                        sbCount.append(currTileCountsMap.get(t)).append(", ");
                    } else {
                        sbCount.append(0).append(", ");

                    }
                }
                sbCount.delete(sbCount.length() - ", ".length(), sbCount.length())
                        .append("],")
                        .append(System.lineSeparator());

                largestTileId = Math.max(largestTileId, tileCountMapList.get(i).lastKey());
            }
            sbCount.delete(sbCount.length() - System.lineSeparator().length() - ",".length(), sbCount.length());
            sbCount.append(System.lineSeparator());
            sbCount.append("\t\t]").append(System.lineSeparator());
            sbCount.append("\t},").append(System.lineSeparator());
        }
        sbCount.delete(sbCount.length() - System.lineSeparator().length() - ",".length(), sbCount.length());
        sbCount.append(System.lineSeparator());
        sbCount.append("]").append(System.lineSeparator());
        return sbCount;
    }
}
