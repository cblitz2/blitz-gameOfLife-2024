package blitz.gameoflife;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class GridTest {

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
        int[][] actualRelevantPortion = extractRelevantPortion(grid.getField(), 3, 3);
        assertArrayEquals(expectedNextGen, actualRelevantPortion);
    }

    private int[][] extractRelevantPortion(int[][] fullGrid, int rows, int cols) {
        int startRow = (fullGrid.length - rows) / 2;
        int startCol = (fullGrid[0].length - cols) / 2;

        int[][] portion = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            System.arraycopy(fullGrid[startRow + i], startCol, portion[i], 0, cols);
        }
        return portion;
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

    @Test
    public void centeredGrid() {
        // given
        String rleData = "bob$2ob$b2o!";
        RleParser parser = new RleParser();
        parser.parseHeader("x = 3, y = 3");

        int[][] expectedGrid = {
                {0, 1, 0},
                {1, 1, 0},
                {0, 1, 1},
        };

        // when
        int[][] actualGrid = parser.decodeData(rleData);
        Grid grid = new Grid(actualGrid);
        int[][] actualRelevantPortion = extractRelevantPortion(grid.getField(), 3, 3);

        // then
        assertArrayEquals(expectedGrid, actualRelevantPortion);
    }
}
