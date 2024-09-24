package blitz.gameoflife;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
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
        String rleData = "b2o$bobo$2o!";
        RleParser parser = new RleParser();
        int[][] expectedGrid = {
                {0, 1, 1, 0},
                {1, 0, 0, 1},
                {0, 1, 1, 0},
        };
        parser.parseHeader("x = 4, y = 3");

        // when
        int[][] actualGrid = parser.decodeData(rleData);

        System.out.println("Actual Grid:");
        for (int[] row : actualGrid) {
            System.out.println(Arrays.toString(row));
        }

        // then
        assertArrayEquals(expectedGrid, actualGrid);
    }
}
