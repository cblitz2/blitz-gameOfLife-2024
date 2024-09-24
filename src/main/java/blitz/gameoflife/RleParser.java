package blitz.gameoflife;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class RleParser {
    private int rows;
    private int cols;

    private String readFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("#") || line.isEmpty()) {
                    continue;
                }
                if (line.startsWith("x")) {
                    parseHeader(line);
                } else {
                    content.append(line);
                }
            }
        }
        return content.toString();
    }

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
                count = count * 10 + Character.getNumericValue(c);}
            else if (c == 'b') {
                if (count == 0) {
                    count = 1;
                }
                for (int j = 0; j < count; j++) {
                    if (currentRow >= rows) {
                        break;
                    }
                    grid[currentRow][currentCol] = 0;
                    currentCol++;
                    if (currentCol >= cols) {
                        currentRow++;
                        currentCol = 0;
                    }
                }
                count = 0; }
            else if (c == 'o') {
                if (count == 0) {
                    count = 1;
                }
                for (int j = 0; j < count; j++) {
                    if (currentRow >= rows) {
                        break;
                    }
                    grid[currentRow][currentCol] = 1;
                    currentCol++;
                    if (currentCol >= cols) {
                        currentRow++;
                        currentCol = 0;
                    }
                }
                count = 0; }
            else if (c == '$') {
                currentRow++;
                currentCol = 0;
                if (currentRow >= rows) {
                    break;
                }}
            else if (c == '!') {
                break;
            }
        }
        return grid;
    }

    public int[][] loadGridFromRleFile(String filePath) throws IOException {
        String content = readFile(filePath);
        return decodeData(content);
    }
}
