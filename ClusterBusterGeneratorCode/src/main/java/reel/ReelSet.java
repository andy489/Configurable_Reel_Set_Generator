package reel;

import java.util.List;

public class ReelSet {
    protected static final List<List<Integer>> TILES_COUNTS = List.of(
            // 32, 32, 30, 28, 26, 18, 16, 12, 10

//            List.of(0, 32, 30, 0, 26, 18, 0, 12, 10),
//            List.of(32, 0, 30, 28, 0, 18, 16, 0, 10),
//            List.of(32, 32, 0, 28, 26, 0, 16, 12, 0),
//            List.of(0, 32, 30, 0, 26, 18, 0, 12, 10),
//            List.of(32, 0, 30, 28, 0, 18, 16, 0, 10),
//            List.of(32, 32, 0, 28, 26, 0, 16, 12, 0),
//            List.of(0, 32, 30, 0, 26, 18, 0, 12, 10),
//            List.of(32, 0, 30, 28, 0, 18, 16, 0, 10),
//            List.of(32, 32, 0, 28, 26, 0, 16, 12, 0)


            List.of(0, 11, 10, 0, 8, 6, 0, 4, 3),
            List.of(11, 0, 10, 10, 0, 6, 5, 0, 3),
            List.of(11, 11, 0, 10, 8, 0, 5, 4, 0),
            List.of(0, 11, 10, 0, 8, 6, 0, 4, 3),
            List.of(11, 0, 10, 10, 0, 6, 5, 0, 3),
            List.of(11, 11, 0, 10, 8, 0, 5, 4, 0),
            List.of(0, 11, 10, 0, 8, 6, 0, 4, 3),
            List.of(11, 0, 10, 10, 0, 6, 5, 0, 3),
            List.of(11, 11, 0, 10, 8, 0, 5, 4, 0)
    );

    protected static final int MIN_TILES_CNT_WIN = 7;

    protected static final int MIN_STACK_1 = 3;
    protected static final int MAX_STACK_1 = 4;
    protected static final int DIST_1 = 3;

    protected static final int MIN_STACK_2 = 2;
    protected static final int MAX_STACK_2 = 2;
    protected static final int DIST_2 = 4;
}
