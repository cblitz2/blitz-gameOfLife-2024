package blitz.gameoflife;

import java.util.Arrays;

public class Grid {

    private int width;
    private int height;
    private int[][] field;
    private static final int MIN_SIZE = 100;


    public Grid(int[][] initialField) {
        setField(initialField);
    }

    public void nextGen() {

        int[][] next = new int[height][width];

        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {

                int neighbors = 0;

                // count neighbors
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {

                        int neighborH = h + i;
                        int neighborW = w + j;

                        if ((i != 0 || j != 0) && neighborH >= 0
                                && neighborH < height && neighborW >= 0
                                && neighborW < width) {
                            if (field[neighborH][neighborW] == 1) {
                                neighbors++;
                            }
                        }
                    }
                }

                // rules of the game
                if (field[h][w] == 1 && (neighbors == 2 || neighbors == 3)) {
                    next[h][w] = 1;
                } else if (neighbors == 3) {
                    next[h][w] = 1;
                }
            }
        }
        field = next;
    }

    public void setField(int[][] newField) {
        int patternHeight = newField.length;
        int patternWidth = newField[0].length;

        int gridHeight = Math.max(MIN_SIZE, patternHeight);
        int gridWidth = Math.max(MIN_SIZE, patternWidth);

        int[][] newGrid = new int[gridHeight][gridWidth];

        int startRow = (gridHeight - patternHeight) / 2;
        int startCol = (gridWidth - patternWidth) / 2;

        for (int y = 0; y < patternHeight; y++) {
            for (int x = 0; x < patternWidth; x++) {
                newGrid[startRow + y][startCol + x] = newField[y][x];
            }
        }

        this.field = newGrid;
        this.height = newGrid.length;
        this.width = newGrid[0].length;
    }

    public int[][] getField() {
        return field;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int y = 0; y < field.length; y++) {
            for (int x = 0; x < field[y].length; x++) {
                builder.append(field[y][x]);
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    public void clearGrid(int[][] grid) {
        for (int y = 0; y < grid.length; y++) {
            Arrays.fill(grid[y], 0);
        }
    }
}