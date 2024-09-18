package blitz.gameoflife;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameFrame extends JFrame {

    private Grid grid;
    private int cellSize = 20;
    private JButton playButton;
    private JButton pauseButton;
    private JButton clearButton;

    public GameFrame() {
        grid = new Grid(70, 40);

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
            grid.clearGrid(grid.getField());
            repaint();
        });

    }
}
