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

        for (int y = 0; y < grid.getField().length; y++) {
            for (int x = 0; x < grid.getField()[y].length; x++) {
                if (grid.getField()[y][x] == 1) {
                    g.setColor(Color.BLACK);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);

                g.setColor(Color.GRAY);
                g.drawRect(x * cellSize, y * cellSize, cellSize, cellSize);
            }
        }
    }
}
