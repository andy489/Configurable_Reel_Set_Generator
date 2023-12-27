package reel;

import com.fasterxml.jackson.annotation.JsonAlias;
import wrapper.ConvertWrapper;

import java.util.List;

public class ReelSetsCollectionData {
    @JsonAlias({"gameName", "gameAbb", "abb", "abbreviation"})
    private String mapName;

    @JsonAlias({"id"})
    private String gameId;

    @JsonAlias({"type"})
    private String strategy;

    @JsonAlias({"print", "result", "res"})
    private String output;

    @JsonAlias({"tar", "target"})
    private String resultFilePath;
    @JsonAlias({"convertWrapper"})
    private ConvertWrapper convert;

    @JsonAlias({"reels", "sets"})
    private List<ReelSet> reelSets;

    public String getMapName() {
        return mapName;
    }

    public ReelSetsCollectionData setMapName(String mapName) {
        this.mapName = mapName;
        return this;
    }

    public String getGameId() {
        return gameId;
    }

    public ReelSetsCollectionData setGameId(String gameId) {
        this.gameId = gameId;
        return this;
    }

    public String getStrategy() {
        return strategy;
    }

    public ReelSetsCollectionData setStrategy(String strategy) {
        this.strategy = strategy;
        return this;
    }

    public String getOutput() {
        return output;
    }

    public ReelSetsCollectionData setOutput(String output) {
        this.output = output;
        return this;
    }

    public String getResultFilePath() {
        return resultFilePath;
    }

    public ReelSetsCollectionData setResultFilePath(String resultFilePath) {
        this.resultFilePath = resultFilePath;
        return this;
    }

    public ConvertWrapper getConvert() {
        return convert;
    }

    public ReelSetsCollectionData setConvert(ConvertWrapper convert) {
        this.convert = convert;
        return this;
    }

    public List<ReelSet> getReelSets() {
        return reelSets;
    }

    public ReelSetsCollectionData setReelSets(List<ReelSet> reelSets) {
        this.reelSets = reelSets;
        return this;
    }

    @Override
    public String toString() {
        return "ReelSetsCollectionData{" +
                "mapName='" + mapName + '\'' +
                ", gameId='" + gameId + '\'' +
                ", strategy='" + strategy + '\'' +
                ", output='" + output + '\'' +
                ", resultFilePath='" + resultFilePath + '\'' +
                ", convert=" + convert +
                ", reelSets=" + reelSets +
                '}';
    }
}
