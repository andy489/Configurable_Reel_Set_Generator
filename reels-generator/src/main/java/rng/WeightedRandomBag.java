package rng;

import java.util.Map;
import java.util.TreeMap;

public class WeightedRandomBag<E> implements IWeightedRandomBag<E> {

    private final TreeMap<Double, E> weightedItemsBag;
    private final RNG rng;
    private double total;

    {
        rng = new RNG();
        total = 0.0;
        weightedItemsBag = new TreeMap<>();
    }

    public WeightedRandomBag(Map<E, Double> items) {

        for (Map.Entry<E, Double> entry : items.entrySet()) {
            E key = entry.getKey();
            Double val = entry.getValue();

            if (val <= 0.0d) {
                continue;
            }

            total += val;
            weightedItemsBag.put(total, key);
        }
    }

    @Override
    public E peek() {
        if (total == 0) {
            return null;
        }

        double rand = rng.getDouble(0.0d, total);
        return weightedItemsBag.higherEntry(rand).getValue();
    }

    @Override
    public E pull() {
        if (total == 0) {
            return null;
        }

        double rand = rng.getDouble(0.0d, total);
        Map.Entry<Double, E> pulledEntry = weightedItemsBag.higherEntry(rand);
        Double pulledKey = pulledEntry.getKey();

        weightedItemsBag.remove(pulledKey);
        Double toRemoveFromTotal = weightedItemsBag.lowerKey(pulledKey);

        if (toRemoveFromTotal != null) {
            total -= toRemoveFromTotal;
        } else {
            total -= pulledKey;
        }

        return pulledEntry.getValue();
    }
}
