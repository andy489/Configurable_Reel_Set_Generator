package rng;

import org.apache.commons.math3.random.MersenneTwister;

public class RNG implements IRNG {
    private final MersenneTwister mt;

    public RNG() {
        mt = new MersenneTwister();
    }

    @Override
    public int getRandInRange(int l, int r) {
        return mt.nextInt(r - l) + l;
    }
}
