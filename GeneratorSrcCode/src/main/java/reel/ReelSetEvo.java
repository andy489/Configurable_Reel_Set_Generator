package reel;

import java.util.List;

public class ReelSetEvo {

    private String setName;
    private List<List<Integer>> reelSet;

    public String getSetName() {
        return setName;
    }

    public ReelSetEvo setSetName(String setName) {
        this.setName = setName;
        return this;
    }

    public List<List<Integer>> getReelSet() {
        return reelSet;
    }

    public ReelSetEvo setReelSet(List<List<Integer>> reelSet) {
        this.reelSet = reelSet;
        return this;
    }

    @Override
    public String toString() {
        return "ReelSetEvo{" +
                "setName='" + setName + '\'' +
                ", reelSet=" + reelSet +
                '}';
    }
}
