package blitz.gameoflife;

public class RleParser {

    private int rows;
    private int cols;

    public void parseHeader(String header) {
        String[] parts = header.split(",");

        for (String part : parts) {
            String[] keyValue = part.trim().split("=");
            try {
                if (keyValue.length == 2) {
                    if (keyValue[0].trim().equals("x")) {
                        cols = Integer.parseInt(keyValue[1].trim());
                    } else if (keyValue[0].trim().equals("y")) {
                        rows = Integer.parseInt(keyValue[1].trim());
                    }
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid header format: " + header);
            }
        }
    }

    public int[][] decodeData(String dataContent) {
        if (rows == 0 || cols == 0) {
            throw new IllegalStateException("Grid dimensions are not set. Check header parsing.");
        }

        int[][] grid = new int[rows][cols];
        int currentRow = 0;
        int currentCol = 0;
        int count = 0;

        for (int i = 0; i < dataContent.length(); i++) {
            char c = dataContent.charAt(i);

            if (Character.isDigit(c)) {
                count = count * 10 + Character.getNumericValue(c);
            } else if (c == 'b' || c == 'o') {
                int value = (c == 'o') ? 1 : 0;
                count = (count == 0) ? 1 : count;

                for (int j = 0; j < count; j++) {
                    if (currentRow < rows && currentCol < cols) {
                        grid[currentRow][currentCol] = value;
                        currentCol++;
                        if (currentCol > cols) {
                            currentRow++;
                            currentCol = 0;
                        }
                    }
                }
                count = 0;
            } else if (c == '$') {
                currentRow++;
                currentCol = 0;
                count = 0;
            } else if (c == '!') {
                break;
            }
        }
        return grid;
    }

    public int[][] getGrid(String content) {
        String[] lines = content.split("\n");
        StringBuilder dataContent = new StringBuilder();

        for (String line : lines) {
            line = line.trim();
            if (line.startsWith("#") || line.isEmpty()) {
                continue;
            }
            if (line.startsWith("x")) {
                parseHeader(line);
            } else {
                dataContent.append(line);
            }
        }
        return decodeData(dataContent.toString());
    }
}
