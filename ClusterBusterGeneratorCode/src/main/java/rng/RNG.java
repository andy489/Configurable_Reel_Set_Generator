package rng;

import org.apache.commons.math3.random.MersenneTwister;

public class RNG implements IRNG {
    private final MersenneTwister mt;

    public RNG() {
        mt = new MersenneTwister();
    }

    @Override
    public Integer getRandInRange(Integer l, Integer r) {
        return mt.nextInt(r - l) + l;
    }
}
