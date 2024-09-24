package blitz.gameoflife;

public class Main {
    public static void main(String[] args) {
        RleParser rleParser = new RleParser();

        try {
            int[][] gridData = rleParser.loadGridFromRleFile("/Users/chanablitz/Downloads/all/3c10piwave.rle");

            Grid grid = new Grid(gridData);
            GameFrame gameFrame = new GameFrame(grid);
            gameFrame.setVisible(true);

        }
        catch (Exception e) {
            System.err.println("Error loading RLE file: " + e.getMessage());
        }
    }
}
