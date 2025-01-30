package shuffler;

import reel.Restriction;
import rng.RNG;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class RestrictionsApplier {
    private static final RNG rng = new RNG();

    public static LinkedList<Integer> get(final Map<Integer, Integer> tilesCnt, final Restriction restrictions) {
        List<Integer> stackSizes = restrictions.getStacks();
        List<Double> stackChances = restrictions.getChances();

        Integer distance = restrictions.getDistance();

        LinkedList<Integer> res = new LinkedList<>();

        Map<Integer, Integer> forbiddenByDist = new TreeMap<>();

        int currTriesBeforeTraversing = 0;
        int currTraversalTries = 0;

        while (!tilesCnt.isEmpty()) {
            final int nth = rng.getRandInRange(0, tilesCnt.size());

            final int TILE_TO_PLACE = tilesCnt.keySet().stream()
                    .skip(nth)
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Failed to skip random number of tiles"));

            final int CURR_CNT_TILE = tilesCnt.get(TILE_TO_PLACE);

            if (CURR_CNT_TILE == 0) {
                tilesCnt.remove(TILE_TO_PLACE);
                continue;
            }

            final int CURR_STACK_SZ = rng.getWeightedRand(stackSizes, stackChances);
            final int REPETITIONS = Math.min(CURR_STACK_SZ, CURR_CNT_TILE);

            if (forbiddenByDist.getOrDefault(TILE_TO_PLACE, Integer.MAX_VALUE) < distance) {
                currTriesBeforeTraversing++;

                if (currTriesBeforeTraversing > tilesCnt.size() * 3) {
                    currTraversalTries++;
                    boolean hasPlaced = placeStackedTilesWithoutConflicts(
                            res,
                            TILE_TO_PLACE,
                            Collections.nCopies(REPETITIONS, TILE_TO_PLACE),
                            distance
                    );

                    if (!hasPlaced) {
                        int MAX_TRAVERSAL_TRIES = 3;
                        if (currTraversalTries > MAX_TRAVERSAL_TRIES) {
                            return null;
                        }
                    } else {
                        updateTilesCountAfterPlacing(REPETITIONS, CURR_STACK_SZ, tilesCnt, TILE_TO_PLACE);
                        currTriesBeforeTraversing = 0;
                        currTraversalTries = 0;
                    }
                }
            } else {
                updateTilesCountAfterPlacing(REPETITIONS, CURR_STACK_SZ, tilesCnt, TILE_TO_PLACE);

                res.addAll(Collections.nCopies(REPETITIONS, TILE_TO_PLACE));

                actualizeForbiddenByDistSet(forbiddenByDist, REPETITIONS, TILE_TO_PLACE);

                currTriesBeforeTraversing = 0;
            }
        }

        int tryCnt = 0;
        while (!validateNoHeadAndTailConflicts(res, distance)) {
            tryToFixConflictsAtHeadAndTail(res, distance);
            tryCnt++;

            int MAX_CONFLICT_FIX_RETRY_LIMIT = 1_000;
            if (tryCnt > MAX_CONFLICT_FIX_RETRY_LIMIT) {
                return null;
            }
        }
        return res;
    }

    public static boolean placeStackedTilesWithoutConflicts(
            LinkedList<Integer> res,
            final int CURR_TILE_TO_PLACE,
            final Collection<Integer> toPlace,
            final int DIST
    ) {
        final int SIZE = res.size() - 2 * DIST;

        for (int i = 0; i < SIZE; i++) {
            int k = 0;
            boolean canPlace = true;

            for (; k < DIST; k++) {
                Integer leftHalf = res.get(i + k);
                Integer rightHalf = res.get(i + DIST + k);

                if (leftHalf == CURR_TILE_TO_PLACE || rightHalf == CURR_TILE_TO_PLACE) {
                    canPlace = false;
                    break;
                }
            }

            if (canPlace && !Objects.equals(res.get(i + k - 1), res.get(i + k))) {
                res.addAll(i + k, toPlace);
                return true;
            }
        }
        return false;
    }

    public static void tryToFixConflictsAtHeadAndTail(LinkedList<Integer> res, final int DIST) {
        if (res.isEmpty()) {
            return;
        }

        Collection<Integer> toPlace = new LinkedList<>();

        final int CURR_LAST = res.getLast();
        final int LAST = res.size() - 1;

        for (int i = LAST; i >= 0; i--) {
            if (res.get(i) != CURR_LAST) {
                break;
            }

            toPlace.add(res.pollLast());
        }

        placeStackedTilesWithoutConflicts(res, CURR_LAST, toPlace, DIST);
    }

    public static boolean validateNoHeadAndTailConflicts(final LinkedList<Integer> res, final int DIST) {
        final int SIZE = res.size();
        if (res.isEmpty()) {
            return false;
        }

        for (int i = 0; i < DIST; i++) {
            for (int j = 0; j < DIST - i; j++) {
                if (Objects.equals(res.get(i), res.get(SIZE - 1 - j))) {
                    return false;
                }
            }
        }

        return true;
    }

    public static void actualizeForbiddenByDistSet(
            Map<Integer, Integer> forbiddenByDist,
            final int REPETITIONS,
            final int TILE_TO_PLACE
    ) {
        for (final Integer key : forbiddenByDist.keySet()) {
            forbiddenByDist.merge(key, REPETITIONS, Integer::sum);
        }

        forbiddenByDist.put(TILE_TO_PLACE, 0);
    }

    public static void updateTilesCountAfterPlacing(
            final int REPETITIONS,
            final int CURR_STACK_SZ,
            Map<Integer, Integer> tilesCnt,
            final int TILE_TO_PLACE
    ) {
        if (REPETITIONS < CURR_STACK_SZ) {
            tilesCnt.remove(TILE_TO_PLACE);
        } else {
            tilesCnt.put(TILE_TO_PLACE, tilesCnt.get(TILE_TO_PLACE) - CURR_STACK_SZ);
        }
    }
}
