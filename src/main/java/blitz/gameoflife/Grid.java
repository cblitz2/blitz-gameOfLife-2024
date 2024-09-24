package blitz.gameoflife;

import java.util.Arrays;

public class Grid {

    private int width;
    private int height;
    private int[][] field;


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
        this.field = newField;
        this.height = newField.length;
        this.width = newField[0].length;
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
