package reel;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public class ReelSetsCollectionData {

    @JsonAlias({"company", "org"})
    private String com;

    @JsonAlias({"gameName"})
    private String mapName;

    @JsonAlias({"id"})
    private String gameId;

    @JsonAlias({"type"})
    private String strategy;

    @JsonAlias({"print", "result", "res"})
    private String output;

    @JsonAlias({"tar", "target"})

    private String resultFilePath;

    @JsonAlias({"reels", "sets"})
    private List<ReelSet> reelSets;

    private Boolean convert;

    private String convertSrc;

    private String convertDest;


    public String getCom() {
        return com;
    }

    public ReelSetsCollectionData setCom(String com) {
        this.com = com;
        return this;
    }

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

    public List<ReelSet> getReelSets() {
        return reelSets;
    }

    public ReelSetsCollectionData setReelSets(List<ReelSet> reelSets) {
        this.reelSets = reelSets;
        return this;
    }

    public Boolean getConvert() {
        return convert;
    }

    public ReelSetsCollectionData setConvert(Boolean convert) {
        this.convert = convert;
        return this;
    }

    public String getConvertSrc() {
        return convertSrc;
    }

    public ReelSetsCollectionData setConvertSrc(String convertSrc) {
        this.convertSrc = convertSrc;
        return this;
    }

    public String getConvertDest() {
        return convertDest;
    }

    public ReelSetsCollectionData setConvertDest(String convertDest) {
        this.convertDest = convertDest;
        return this;
    }

    @Override
    public String toString() {
        return "ReelSetsCollectionData{" +
                "com='" + com + '\'' +
                ", mapName='" + mapName + '\'' +
                ", gameId='" + gameId + '\'' +
                ", strategy='" + strategy + '\'' +
                ", output='" + output + '\'' +
                ", resultFilePath='" + resultFilePath + '\'' +
                ", reelSets=" + reelSets +
                ", convert='" + convert + '\'' +
                ", convertSrc='" + convertSrc + '\'' +
                ", convertDest='" + convertDest + '\'' +
                '}';
    }
}
