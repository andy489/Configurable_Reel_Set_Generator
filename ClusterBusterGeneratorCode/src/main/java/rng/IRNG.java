package rng;

public interface IRNG {
    /**
     * @param l left bound of interval - inclusive
     * @param r right bound of interval - exclusive
     * @return random number in range [l, r)
     */
    int getRandInRange(int l, int r);
}
