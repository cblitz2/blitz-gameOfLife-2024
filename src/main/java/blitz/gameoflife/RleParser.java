package blitz.gameoflife;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.commons.io.IOUtils;

public class RleParser {

    private int rows;
    private int cols;

    public void parseHeader(String header) {
        String[] parts = header.split(",");

        for (String part : parts) {
            String[] keyValue = part.trim().split("=");

            if (keyValue.length == 2) {
                if (keyValue[0].trim().equals("x")) {
                    cols = Integer.parseInt(keyValue[1].trim());
                } else if (keyValue[0].trim().equals("y")) {
                    rows = Integer.parseInt(keyValue[1].trim());
                }
            }
        }
    }

    public int[][] decodeData(String content) {
        int[][] grid = new int[rows][cols];
        int currentRow = 0;
        int currentCol = 0;
        int count = 0;

        for (int i = 0; i < content.length(); i++) {
            char c = content.charAt(i);

            if (Character.isDigit(c)) {
                count = count * 10 + Character.getNumericValue(c);
            } else if (c == 'b') {
                if (count == 0) {
                    count = 1;
                }
                for (int j = 0; j < count; j++) {
                    if (currentRow < rows && currentCol < cols) {
                        grid[currentRow][currentCol] = 0;
                        currentCol++;
                        if (currentCol >= cols) {
                            currentRow++;
                            currentCol = 0;
                        }
                    }
                }
                count = 0;
            } else if (c == 'o') {
                if (count == 0) {
                    count = 1;
                }
                for (int j = 0; j < count; j++) {
                    if (currentRow < rows && currentCol < cols) {
                        grid[currentRow][currentCol] = 1;
                        currentCol++;
                        if (currentCol >= cols) {
                            currentRow++;
                            currentCol = 0;
                        }
                    }
                }
                count = 0;
            } else if (c == '$') {
                if (currentCol != 0) {
                    currentRow++;
                    currentCol = 0;
                }
            } else if (c == '!') {
                break;
            }
        }
        return grid;
    }

    public int[][] getGridFromClipboard() throws IOException, UnsupportedFlavorException {
        String content = getFromClipboard();
        String[] lines = content.split("\n");

        for (String line : lines) {
            line = line.trim();
            if (line.startsWith("#") || line.isEmpty()) {
                continue;
            }
            if (line.startsWith("x")) {
                parseHeader(line);
                break;
            }
        }
        return decodeData(content);
    }

    public String getFromClipboard() throws IOException, UnsupportedFlavorException {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        String clipContent = (String) clipboard.getData(DataFlavor.stringFlavor);
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
}
