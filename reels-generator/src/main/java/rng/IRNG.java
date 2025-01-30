package rng;

import java.util.List;

public interface IRNG {
    /**
     * @param l left bound of interval - inclusive
     * @param r right bound of interval - exclusive
     * @return random number in range [l, r)
     */
    Integer getRandInRange(Integer l, Integer r);

    Double getDouble(Double l, Double r);

    Integer getWeightedRand(List<Integer> outcomes, List<Double> chances);
}
