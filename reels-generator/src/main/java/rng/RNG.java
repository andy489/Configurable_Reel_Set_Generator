package rng;

import org.apache.commons.math3.random.MersenneTwister;

import java.util.HashMap;
import java.util.List;

public class RNG implements IRNG {
    private final MersenneTwister mt;

    public RNG() {
        mt = new MersenneTwister();
    }

    @Override
    public Integer getRandInRange(Integer l, Integer r) {
        return mt.nextInt(r - l) + l;
    }

    @Override
    public Double getDouble(Double l, Double r) {
        return l + (r - l) * mt.nextDouble();
    }

    @Override
    public Integer getWeightedRand(List<Integer> outcomes, List<Double> chances) {
        if (outcomes.size() != chances.size()) {
            throw new IllegalArgumentException("Number of outcomes and chances do not match");
        }

        HashMap<Integer, Double> itemChanceMap = new HashMap<>();

        for (int i = 0; i < chances.size(); i++) {
            itemChanceMap.put(outcomes.get(i), chances.get(i));
        }

        WeightedRandomBag<Integer> integerWeightedRandomBag = new WeightedRandomBag<>(itemChanceMap);

        return integerWeightedRandomBag.peek();
    }
}
