package convert;

import reel.ReelSetEvo;
import reel.ReelSetsCollectionData;

import java.util.List;

public class CayConverter {
    public static StringBuilder convertToCay(ReelSetsCollectionData reelSetsCollection, List<ReelSetEvo> reelSetsEvo) {
        StringBuilder sb = new StringBuilder(System.lineSeparator());

        final String mapName = reelSetsCollection.getMapName();
        final String gameId = reelSetsCollection.getGameId();

        for (int i = 0; i< reelSetsEvo.size(); i++) {

            final int SET_SIZE = reelSetsEvo.get(i).getReelSet().size();
            for (int j = 0; j < SET_SIZE; j++) {

                final int REEL_SIZE = reelSetsEvo.get(i).getReelSet().get(j).size();
                for (int k = 0; k < REEL_SIZE; k++) {

                    Integer currentTile = reelSetsEvo.get(i).getReelSet().get(j).get(k);

                    sb.append("\"").append(reelSetsEvo.get(i).getSetName()).append("\",")
                            .append("\"").append(j).append("\",")
                            .append("\"").append(k).append("\",")
                            .append("\"").append(gameId)
                            .append(currentTile < 10 ? "0" + currentTile : currentTile)
                            .append("\"")
                            .append(System.lineSeparator());
                }
            }
        }

        return sb;
    }
}
