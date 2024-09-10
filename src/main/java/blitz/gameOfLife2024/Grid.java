package blitz.gameOfLife2024;

public class Grid {
    private int width;
    private int height;
    private int[][] field;

    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
        this.field = new int[height][width];
    }

    public int[][] nextGen(int grid[][]) {

        int height = grid.length;
        int width = grid[0].length;

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
                            neighbors += grid[neighborH][neighborW];
                        }
                    }
                }

                // rules of the game
                if (grid[h][w] == 1) {
                    if (neighbors < 2 || neighbors > 3) {
                        next[h][w] = 0; }
                    else {
                        next[h][w] = 1; }
                } else {
                    if (neighbors == 3) {
                        next[h][w] = 1;
                    }
                    else {
                        next[h][w] = 0;
                    }

                }
            }
        }
        return next;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int y = 0; y < field.length; y++) {
            for (int x = 0; x < field[y].length; x++) {
                builder.append(field[y][x] == 1);
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
