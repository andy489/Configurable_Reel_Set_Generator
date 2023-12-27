package reel;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Restriction {
    @JsonAlias({"min"})

    protected Integer minStack;
    @JsonAlias({"max"})

    protected Integer maxStack;
    @JsonAlias({"dist"})

    protected Integer distance;

    @Override
    public String toString() {
        return "Restriction{" +
                "minStack=" + minStack +
                ", maxStack=" + maxStack +
                ", distance=" + distance +
                '}';
    }

    public Restriction() {
    }

    public Restriction(Integer minStack, Integer maxStack, Integer distance) {
        this.minStack = minStack;
        this.maxStack = maxStack;
        this.distance = distance;
    }

    public void setMinStack(Integer minStack) {
        this.minStack = minStack;
    }

    public void setMaxStack(Integer maxStack) {
        this.maxStack = maxStack;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Integer getMinStack() {
        return minStack;
    }

    public Integer getMaxStack() {
        return maxStack;
    }

    public Integer getDistance() {
        return distance;
    }
}
