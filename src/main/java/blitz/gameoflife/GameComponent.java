package blitz.gameoflife;

import javax.swing.*;
import java.awt.*;

public class GameComponent extends JComponent {

    private Grid grid;
    private int cellSize;

    public GameComponent(Grid grid, int cellSize) {
        this.grid = grid;
        this.cellSize = cellSize;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        int[][] field = grid.getField();
        int numRows = field.length;
        int numCols = field[0].length;

        for (int y = 0; y < numRows; y++) {
            for (int x = 0; x < field[y].length; x++) {
                if (field[y][x] == 1) {
                    g.setColor(Color.BLACK);
                    g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
                }
            }
        }

        g.setColor(Color.GRAY);
        for (int y = 0; y <= numRows; y++) {
            g.drawLine(0, y * cellSize, numCols * cellSize, y * cellSize);
        }
        for (int x = 0; x <= numCols; x++) {
            g.drawLine(x * cellSize, 0, x * cellSize, numRows * cellSize);
        }
    }
}
