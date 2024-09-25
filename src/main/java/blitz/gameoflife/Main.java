package blitz.gameoflife;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        RleParser rleParser = new RleParser();
        try {
            Path p = Paths.get(ClassLoader.getSystemResource("pattern.rle").toURI());
            File file = p.toFile();

            int[][] gridData = rleParser.loadGridFromRleFile(file.getAbsolutePath());

            Grid grid = new Grid(gridData);
            GameFrame gameFrame = new GameFrame(grid);
            gameFrame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}