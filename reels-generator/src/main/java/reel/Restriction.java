package reel;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Restriction {
    @JsonAlias({"stack", "stackSizes", "stackSizes"})
    protected List<Integer> stacks;

    @JsonAlias({"chance", "stackChances", "stacksChances"})
    protected List<Double> chances;

    @JsonAlias({"dist", "minDistance", "minStackDistance"})
    protected Integer distance;

    @Override
    public String toString() {
        return "Restriction{" +
                "stacks=" + stacks +
                ", chances=" + chances +
                ", distance=" + distance +
                '}';
    }

    public Restriction() {
    }

    public Restriction(List<Integer> stacks, List<Double> chances, Integer distance) {
        this.stacks = stacks;
        this.chances = chances;
        this.distance = distance;
    }

    public List<Integer> getStacks() {
        return stacks;
    }

    public Restriction setStacks(List<Integer> stacks) {
        this.stacks = stacks;
        return this;
    }

    public List<Double> getChances() {
        return chances;
    }

    public Restriction setChances(List<Double> chances) {
        this.chances = chances;
        return this;
    }

    public Integer getDistance() {
        return distance;
    }

    public Restriction setDistance(Integer distance) {
        this.distance = distance;
        return this;
    }
}
