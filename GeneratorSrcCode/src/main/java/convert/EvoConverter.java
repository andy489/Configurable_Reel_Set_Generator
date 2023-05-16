package convert;

import reel.ReelSetEvo;
import reel.ReelSetsCollectionData;

import java.util.List;

public class EvoConverter {
    public static StringBuilder convertToEvo(List<ReelSetEvo> reelSetsEvo) {
        StringBuilder sb = new StringBuilder(System.lineSeparator());

        sb.append("[").append(System.lineSeparator());
        for (ReelSetEvo reelSetEvo : reelSetsEvo) {
            sb
                    .append("\t{")
                    .append(System.lineSeparator())
                    .append("\t\t\"setName\": ")
                    .append(reelSetEvo.getSetName())
                    .append(",")
                    .append(System.lineSeparator())
                    .append("\t\t\"reelSet\": [")
                    .append(System.lineSeparator());

            for (int i = 0; i < reelSetEvo.getReelSet().size(); i++) {
                sb
                        .append("\t\t\t")
                        .append(reelSetEvo.getReelSet().get(i).toString().replaceAll(" ", ""))
                        .append(",")
                        .append(System.lineSeparator());
            }

            sb.delete(sb.length() - System.lineSeparator().length() - ",".length(), sb.length());
            sb.append(System.lineSeparator())
                    .append("\t\t]")
                    .append(System.lineSeparator())
                    .append("\t},")
                    .append(System.lineSeparator());
        }

        sb.delete(sb.length() - System.lineSeparator().length() - ",".length(), sb.length());
        sb.append(System.lineSeparator());

        sb.append("]").append(System.lineSeparator());

        return sb;
    }
}
