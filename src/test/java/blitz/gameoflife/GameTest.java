package blitz.gameoflife;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class GameTest {

    @Test
    public void nextGen() {
        // given
        int[][] initialGrid = {
                {0, 1, 0},
                {0, 1, 0},
                {0, 1, 0}};

        Grid grid = new Grid(initialGrid);

        int[][] expectedNextGen = {
                {0, 0, 0},
                {1, 1, 1},
                {0, 0, 0}};

        // when
        grid.nextGen();

        // then
        assertArrayEquals(expectedNextGen, grid.getField());
    }

    @Test
    public void decodeData() {
        // given
        String rleData = "bob$2ob$b2o!";
        RleParser parser = new RleParser();
        int[][] expectedGrid = {
            {0, 1, 0},
            {1, 1, 0},
            {0, 1, 1},
        };
        parser.parseHeader("x = 3, y = 3");

        // when
        int[][] actualGrid = parser.decodeData(rleData);

        // then
        assertArrayEquals(expectedGrid, actualGrid);
    }
}
