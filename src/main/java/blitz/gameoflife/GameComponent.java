package blitz.gameoflife;

import javax.swing.*;
import java.awt.*;

public class GameComponent extends JComponent {

    private Grid grid;

    public GameComponent(Grid grid) {
        this.grid = grid;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int[][] field = grid.getField();

        if (field == null || field.length == 0 || field[0].length == 0) {
            return;
        }

        int numRows = field.length;
        int numCols = field[0].length;

        int cellWidth = getWidth() / numCols;
        int cellHeight = getHeight() / numRows;

        int cellSize = Math.min(cellWidth, cellHeight);

        int xoffset = (getWidth() - (numCols * cellSize)) / 2;
        int yoffset = (getHeight() - (numRows * cellSize)) / 2;

        for (int y = 0; y < numRows; y++) {
            for (int x = 0; x < numCols; x++) {
                if (field[y][x] == 1) {
                    g.setColor(Color.BLACK);
                    g.fillRect(xoffset + x * cellSize, yoffset + y * cellSize, cellSize, cellSize);
                }
            }
        }

        g.setColor(Color.GRAY);
        for (int y = 0; y <= numRows; y++) {
            g.drawLine(xoffset, yoffset + y * cellSize, xoffset + numCols * cellSize, yoffset + y * cellSize);
        }
        for (int x = 0; x <= numCols; x++) {
            g.drawLine(xoffset + x * cellSize, yoffset, xoffset + x * cellSize, yoffset + numRows * cellSize);
        }
    }

}