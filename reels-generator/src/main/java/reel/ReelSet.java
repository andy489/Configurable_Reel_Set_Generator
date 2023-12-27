package reel;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ReelSet {
    @JsonAlias({"counts", "cnt", "cnts", "tiles", "reelSetTileCounts"})
    protected List<List<Integer>> tilesCounts;
    
    @JsonAlias({"res"})
    protected List<Restriction> restrictions;

    @Override
    public String toString() {
        return "ReelSet{" +
                "tilesCounts=" + tilesCounts +
                ", restrictions=" + restrictions +
                '}';
    }

    public ReelSet() {
    }

    public ReelSet(List<List<Integer>> tilesCounts, List<Restriction> restrictions) {
        this.tilesCounts = tilesCounts;
        this.restrictions = restrictions;
    }

    public void setTilesCounts(List<List<Integer>> tilesCounts) {
        this.tilesCounts = tilesCounts;
    }

    public void setRestrictions(List<Restriction> restrictions) {
        this.restrictions = restrictions;
    }

    public List<List<Integer>> getTilesCounts() {
        return tilesCounts;
    }

    public List<Restriction> getRestrictions() {
        return restrictions;
    }
}
