package blitz.gameoflife;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class GameTest {

    @Test
    public void nextGen() {
        // given
        Grid grid = new Grid(3, 3);
        int[][] initialGrid = {
                {0, 1, 0},
                {0, 1, 0},
                {0, 1, 0}};

        grid.setField(initialGrid);

        int[][] expectedNextGen = {
                {0, 0, 0},
                {1, 1, 1},
                {0, 0, 0}};
        // when
        grid.nextGen();

        // then
        assertArrayEquals(expectedNextGen, grid.getField());
    }
}
