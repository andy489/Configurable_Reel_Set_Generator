package shuffler;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ReaderManager;
import reel.ReelSet;
import reel.ReelSetsCollection;
import reel.Restriction;
import rng.RNG;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class ShuffleWithRestrictions {
    private static final RNG rng = new RNG();
    private static final Integer RETRIES = 8;

    private static LinkedList<Integer> get(final Map<Integer, Integer> tilesCnt, final Restriction restrictions) {
        Integer minStack = restrictions.getMinStack();
        Integer maxStack = restrictions.getMaxStack();
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

            final int CURR_STACK_SZ = rng.getRandInRange(minStack, maxStack + 1);
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

    private static boolean placeStackedTilesWithoutConflicts(
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

    private static void tryToFixConflictsAtHeadAndTail(LinkedList<Integer> res, final int DIST) {
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

    private static boolean validateNoHeadAndTailConflicts(final LinkedList<Integer> res, final int DIST) {
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

    private static void actualizeForbiddenByDistSet(
            Map<Integer, Integer> forbiddenByDist,
            final int REPETITIONS,
            final int TILE_TO_PLACE
    ) {
        for (final Integer key : forbiddenByDist.keySet()) {
            forbiddenByDist.merge(key, REPETITIONS, Integer::sum);
        }

        forbiddenByDist.put(TILE_TO_PLACE, 0);
    }

    private static void updateTilesCountAfterPlacing(
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

    private static void fillMapWithTilesIDsCounts(Map<Integer, Integer> tilesCnt, List<Integer> CURR_TILES_COUNTS) {
        final int SIZE = CURR_TILES_COUNTS.size();

        for (int i = 1; i <= SIZE; i++) {
            tilesCnt.put(i, CURR_TILES_COUNTS.get(i - 1));
        }
    }


    private static void genReel(List<Integer> reelCounts) {
        StringBuilder sb = new StringBuilder("[");

        final int SIZE_1 = reelCounts.size();
        for (int i = 0; i < SIZE_1; i++) {
            final int SIZE_2 = reelCounts.get(i);

            sb.append((i + 1 + ", ").repeat(Math.max(0, SIZE_2)));
        }
        sb.delete(sb.length()-2, sb.length());
        sb.append("],");
        System.out.println(sb);
    }

    private static void genReels(List<List<Integer>> reels) {
        for (List<Integer> r : reels) {
            genReel(r);
        }
        System.out.println();
    }

    public static void main(String... args) {
//        List<List<Integer>> reelSet1 = List.of(
//                List.of(0, 12, 12, 0, 10, 10, 0, 6, 6),
//                List.of(12, 0, 12, 10, 0, 10, 6, 0, 6),
//                List.of(12, 12, 0, 10, 10, 0, 6, 6, 0),
//                List.of(0, 12, 12, 0, 10, 10, 0, 6, 6),
//                List.of(12, 0, 12, 10, 0, 10, 6, 0, 6),
//                List.of(12, 12, 0, 10, 10, 0, 6, 6, 0),
//                List.of(0, 12, 12, 0, 10, 10, 0, 6, 6),
//                List.of(12, 0, 12, 10, 0, 10, 6, 0, 6),
//                List.of(12, 12, 0, 10, 10, 0, 6, 6, 0)
//        );
//
//        Restriction r11 = new Restriction(3, 3, 3);
//        Restriction r12 = new Restriction(1, 1, 2);
//
//        List<List<Integer>> reelSet2 = List.of(
//                List.of(0, 12, 12, 0, 10, 10, 0, 6, 6),
//                List.of(12, 0, 12, 10, 0, 10, 6, 0, 6),
//                List.of(12, 12, 0, 10, 10, 0, 6, 6, 0),
//                List.of(0, 12, 12, 0, 10, 10, 0, 6, 6),
//                List.of(12, 0, 12, 10, 0, 10, 6, 0, 6),
//                List.of(12, 12, 0, 10, 10, 0, 6, 6, 0)
//        );
//
//        Restriction r21 = new Restriction(4, 4, 4);
//        Restriction r22 = new Restriction(2, 2, 4);
//
//        ReelSetsCollection reelSetsCollection = new ReelSetsCollection(
//                List.of(
//                        new ReelSet(reelSet1, List.of(r11, r12)),
//                        new ReelSet(reelSet2, List.of(r21, r22))
//                )
//        );

        String filePath = "reelDefinitions.json";

        ReelSetsCollection reelSetsCollection;
        try {
            reelSetsCollection = ReaderManager.readFile(filePath, new ObjectMapper());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (reelSetsCollection == null) {
            throw new RuntimeException(String.format("Failed to read from file %s", filePath));
        }

        if (reelSetsCollection.isFlat()) {
            List<ReelSet> reelSets = reelSetsCollection.getReelSets();

            int k = 0;
            for (ReelSet reelSet : reelSets) {
                System.out.println("Reel Set #" + k + ":");

                genReels(reelSet.getTilesCounts());
                k++;
            }
            return;
        }

        final int SIZE_1 = reelSetsCollection.getReelSets().size();
        for (int i = 0; i < SIZE_1; i++) {

            ReelSet currReelSet = reelSetsCollection.getReelSets().get(i);
            List<Restriction> currRestrictions = currReelSet.getRestrictions();

            final int SIZE_2 = currReelSet.getTilesCounts().size();
            final int SIZE_3 = currReelSet.getRestrictions().size();

            System.out.println("Reel Set #" + i + ":");
            for (int j = 0; j < SIZE_2; j++) {
                Map<Integer, Integer> tilesCnt = new TreeMap<>();

                fillMapWithTilesIDsCounts(tilesCnt, currReelSet.getTilesCounts().get(j));

                LinkedList<Integer> res = ShuffleWithRestrictions.get(
                        tilesCnt,
                        currRestrictions.get(j % SIZE_3)
                );

                int currTries = 0;
                while (res == null) {
                    res = ShuffleWithRestrictions.get(tilesCnt, currRestrictions.get(j % SIZE_3));
                    currTries++;
                    if (currTries == RETRIES) {
                        break;
                    }
                }

                if (res != null) {
                    System.out.println(res);
                } else {
                    System.out.println("[Failed to shuffle this Reel]");
                }
            }
            System.out.println();
        }
    }
}
