
package blitz.gameoflife;

import org.apache.commons.io.IOUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

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
                String clipboardContent = Toolkit.getDefaultToolkit()
                        .getSystemClipboard()
                        .getData(DataFlavor.stringFlavor)
                        .toString();
                String content = getData(clipboardContent);
                controller.paste(content);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid clipboard content.");
                ex.printStackTrace();
            }
        });
    }

    public String getData(String clipContent) throws IOException {
        String content;
        if (clipContent.startsWith("http")) {
            URL url = new URL(clipContent);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setInstanceFollowRedirects(true);
            content = IOUtils.toString(connection.getInputStream(), "UTF-8");
        } else if (clipContent.contains("x") && clipContent.contains("y")) {
            content = clipContent;
        } else if (Files.exists(Paths.get(clipContent))) {
            content = IOUtils.toString(new FileInputStream(new File(clipContent)), "UTF-8");
        } else {
            throw new IOException("Clipboard content is not valid RLE data, URL, or file path.");
        }
        return content;
    }

    private int[][] createEmptyGrid(int rows, int cols) {
        return new int[rows][cols];
    }
}
