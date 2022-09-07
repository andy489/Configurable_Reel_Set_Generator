package reel;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
public class ReelSetsCollection {

    @JsonAlias({"reels", "sets"})

    private List<ReelSet> reelSets;
    @JsonAlias({"plane", "sequential"})

    private boolean isFlat;

    public void setReelSets(List<ReelSet> reelSets) {
        this.reelSets = reelSets;
    }

    public boolean isFlat() {
        return isFlat;
    }

    public void setFlat(boolean flat) {
        this.isFlat = flat;
    }

    @Override
    public String toString() {
        return "ReelSetsCollection{" +
                "reelSets=" + reelSets +
                '}';
    }

    public ReelSetsCollection() {
        super();
        reelSets = new ArrayList<>();
        isFlat = true;
    }

    public ReelSetsCollection(List<ReelSet> reelSets, boolean isFlat) {
        this.reelSets = reelSets;
        this.isFlat = isFlat;
    }

    public List<ReelSet> getReelSets() {
        return reelSets;
    }
}
