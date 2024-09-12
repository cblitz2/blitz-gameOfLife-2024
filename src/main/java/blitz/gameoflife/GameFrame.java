package blitz.gameoflife;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class GameFrame extends JFrame {

    private Grid grid;
    private int cellSize = 20;
    private JButton playButton;
    private JButton pauseButton;
    private JButton clearButton;

    public GameFrame() {
        grid = new Grid(40, 40);

        setSize(800, 600);
        setTitle("Conway's Game of Life");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Timer timer = new Timer(1000, e -> {
            grid.nextGen();
            repaint(); });
        timer.start();

        GameComponent gameComponent = new GameComponent(grid, cellSize);
        add(gameComponent);

        gameComponent.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX() / cellSize;
                int y = e.getY() / cellSize;

                if (x >= 0 && x < grid.getField()[0].length
                        && y >= 0 && y < grid.getField().length) {
                    if (grid.getField()[y][x] == 1) {
                        grid.getField()[y][x] = 0;
                    } else {
                        grid.getField()[y][x] = 1;
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

        JPanel panel = new JPanel();
        panel.add(playButton);
        panel.add(pauseButton);
        panel.add(clearButton);
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
            clearGrid(grid.getField());
            repaint();
        });

    }

    private void clearGrid(int[][] grid) {
        for (int y = 0; y < grid.length; y++) {
            Arrays.fill(grid[y], 0);
        }
    }
}
