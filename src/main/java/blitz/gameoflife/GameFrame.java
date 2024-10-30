
package blitz.gameoflife;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameFrame extends JFrame {
    private final GameController controller;
    private final Grid grid;

    public GameFrame() {

        setSize(800, 600);
        setTitle("Conway's Game of Life");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        grid = new Grid(createEmptyGrid(100, 100));
        GameComponent gameComponent = new GameComponent(grid);
        controller = new GameController(grid, gameComponent);
        add(gameComponent);

        setLayout(new BorderLayout());
        add(gameComponent, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        addButtons(buttonPanel);
        add(buttonPanel, BorderLayout.SOUTH);

        gameComponent.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                controller.toggleCell(e.getX(), e.getY());
            }
        });

        gameComponent.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.toggleCell(e.getX(), e.getY());
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }

    private void addButtons(JPanel panel) {
        JButton playButton = new JButton("Play");
        JButton pauseButton = new JButton("Pause");
        JButton clearButton = new JButton("Clear");
        JButton pasteButton = new JButton("Paste");

        panel.add(playButton);
        panel.add(pauseButton);
        panel.add(clearButton);
        panel.add(pasteButton);
        add(panel, BorderLayout.SOUTH);

        playButton.addActionListener(e -> controller.startTimer());
        pauseButton.addActionListener(e -> controller.stopTimer());

        clearButton.addActionListener(e -> {
            controller.stopTimer();
            grid.clearGrid(grid.getField());
            repaint();
        });

        pasteButton.addActionListener(e -> {
            try {
                RleParser parser = new RleParser();
                String clipboardContent = parser.getFromClipboard();
                controller.paste(clipboardContent);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid clipboard content.");
            }
        });
    }

    private int[][] createEmptyGrid(int rows, int cols) {
        return new int[rows][cols];
    }
}
