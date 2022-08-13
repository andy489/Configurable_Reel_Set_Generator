package shuffle;

import rng.RNG;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class ShuffleWithRestrictions {
    private final RNG rng = new RNG();

    private final int MIN_STACK;
    private final int MAX_STACK;

    private final int DIST;

    private static final int MAX_CONFLICT_FIX_RETRY_LIMIT = 1_000;
    private static final int MAX_TRAVERSAL_TRIES = 3;

    public ShuffleWithRestrictions(
            final int MIN_STACK,
            final int MAX_STACK,
            final int DIST
    ) {
        this.MIN_STACK = MIN_STACK;
        this.MAX_STACK = MAX_STACK;
        this.DIST = DIST;
    }

    public LinkedList<Integer> getShuffledStackedReelWithDistance(
            Map<Integer, Integer> tilesCnt
    ) {
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

            final int CURR_STACK_SZ = rng.getRandInRange(MIN_STACK, MAX_STACK + 1);
            final int REPETITIONS = Math.min(CURR_STACK_SZ, CURR_CNT_TILE);

            if (forbiddenByDist.getOrDefault(TILE_TO_PLACE, Integer.MAX_VALUE) < DIST) {
                currTriesBeforeTraversing++;

                if (currTriesBeforeTraversing > tilesCnt.size() * 3) {
                    currTraversalTries++;
                    boolean hasPlaced = placeStackedTilesWithoutConflicts(
                            res,
                            TILE_TO_PLACE,
                            Collections.nCopies(REPETITIONS, TILE_TO_PLACE),
                            DIST
                    );

                    if (!hasPlaced) {
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
        while (!validateNoHeadAndTailConflicts(res, DIST)) {
            tryToFixConflictsAtHeadAndTail(res, DIST);
            tryCnt++;

            if (tryCnt > MAX_CONFLICT_FIX_RETRY_LIMIT) {
                return null;
            }
        }
        return res;
    }

    private boolean placeStackedTilesWithoutConflicts(
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

    private void tryToFixConflictsAtHeadAndTail(
            LinkedList<Integer> res,
            final int DIST
    ) {
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

    private boolean validateNoHeadAndTailConflicts(
            final LinkedList<Integer> res,
            final int DIST
    ) {
        final int SIZE = res.size();

        for (int i = 0; i < DIST; i++) {
            for (int j = 0; j < DIST - i; j++) {
                if (Objects.equals(res.get(i), res.get(SIZE - 1 - j))) {
                    return false;
                }
            }
        }

        return true;
    }

    private void actualizeForbiddenByDistSet(
            Map<Integer, Integer> forbiddenByDist,
            final int REPETITIONS,
            final int TILE_TO_PLACE
    ) {
        for (final Integer key : forbiddenByDist.keySet()) {
            forbiddenByDist.merge(key, REPETITIONS, Integer::sum);
        }

        forbiddenByDist.put(TILE_TO_PLACE, 0);
    }

    private void updateTilesCountAfterPlacing(
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
