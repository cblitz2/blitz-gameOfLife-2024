
package blitz.gameoflife;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.*;
import java.io.IOException;

public class GameFrame extends JFrame {
    private JButton playButton;
    private JButton pauseButton;
    private JButton clearButton;
    private JButton pasteButton;
    private Grid grid;

    public GameFrame() {

        setSize(800, 600);
        setTitle("Conway's Game of Life");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.grid = new Grid(createEmptyGrid(100, 100));

        GameComponent gameComponent = new GameComponent(grid);
        add(gameComponent);

        Timer timer = new Timer(1000, e -> {
            grid.nextGen();
            repaint(); });
        timer.start();

        gameComponent.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int numRows = grid.getField().length;
                int numCols = grid.getField()[0].length;

                int cellWidth = gameComponent.getWidth() / numCols;
                int cellHeight = gameComponent.getHeight() / numRows;
                int cellSize = Math.min(cellWidth, cellHeight);

                int xOffset = (gameComponent.getWidth() - numCols * cellSize) / 2;
                int yOffset = (gameComponent.getHeight() - numRows * cellSize) / 2;

                int x = (e.getX() - xOffset) / cellSize;
                int y = (e.getY() - yOffset) / cellSize;

                int[][] field = grid.getField();
                if (x >= 0 && x < field[0].length && y >= 0 && y < field.length) {
                    if (field[y][x] == 1) {
                        field[y][x] = 0;
                    } else {
                        field[y][x] = 1;
                    }
                    repaint();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });

        playButton = new JButton("Play");
        pauseButton = new JButton("Pause");
        clearButton = new JButton("Clear");
        pasteButton = new JButton("Paste");

        JPanel panel = new JPanel();
        panel.add(playButton);
        panel.add(pauseButton);
        panel.add(clearButton);
        panel.add(pasteButton);
        add(panel, BorderLayout.SOUTH);

        playButton.addActionListener(e -> {
            timer.start();
            playButton.setEnabled(false);
            pauseButton.setEnabled(true);
        });

        pauseButton.addActionListener(e -> {
            timer.stop();
            playButton.setEnabled(true);
            pauseButton.setEnabled(false);
        });

        clearButton.addActionListener(e -> {
            timer.stop();
            grid.clearGrid(grid.getField());
            repaint();
        });

        pasteButton.addActionListener(e -> {
            RleParser parser = new RleParser();
            try {
                int[][] newGridData = parser.getGridFromClipboard();
                grid.setField(newGridData);
                repaint();
            } catch (IOException | UnsupportedFlavorException ex) {
                JOptionPane.showMessageDialog(this, "Invalid clipboard content or format");
            }
        });
    }
    private int[][] createEmptyGrid(int rows, int cols) {
        return new int[rows][cols];
    }
}
