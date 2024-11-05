package blitz.gameoflife;

import javax.swing.*;

public class GameController {

    private final Grid model;
    private final GameComponent view;

    private javax.swing.Timer timer;

    public GameController(Grid model, GameComponent view) {
        this.model = model;
        this.view = view;
    }

    public void startTimer() {
        if (timer == null) {
            timer = new Timer(1000, e -> {
                model.nextGen();
                view.repaint();
            });
        }
        timer.start();
    }

    public void stopTimer() {
        if (timer != null) {
            timer.stop();
        }
    }

    public void paste(String input) {
        try {
            RleParser parser = new RleParser();
            int [][] parsedGrid = parser.getGrid(input);
            model.setField(parsedGrid);
            view.repaint();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void toggleCell(int screenX, int screenY) {
        int col = screenX  / view.getCellSize();
        int row = screenY / view.getCellSize();

        if (col < model.getWidth() && row < model.getHeight()) {
            int newState = model.getCell(col, row) == 1 ? 0 : 1;
            model.setCell(col, row, newState);
            view.repaint();
        }
    }
}
