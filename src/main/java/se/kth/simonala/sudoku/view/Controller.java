package se.kth.simonala.sudoku.view;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import se.kth.simonala.sudoku.model.FileHandler;
import se.kth.simonala.sudoku.model.ModelFacade;
import java.io.File;
import java.io.IOException;
import java.util.List;


public class Controller {

    private ModelFacade model;
    private SudokuView view;

    public Controller(ModelFacade model, SudokuView view) {
        this.model = model;
        this.view = view;
    }

    public void handleRestartGame() {
        model.restartGame();
        view.updateGrid(model.getBoard());
        view.getGridView().refreshBoard(this);
    }

    public void handleNewDifficulty(String level) {
        model.assignNewLevel(level);
        view.updateGrid(model.getBoard());
        view.getGridView().refreshBoard(this);
    }

    public int getTileValue(int row, int col) {
        return model.getTile(row, col).getValue();
    }

    public boolean handleNewNumber(int row, int col, int value) {
        boolean actionTaken = false;
        if (value == 0) {
            actionTaken = handleClear(row, col);
        } else {
            actionTaken = model.addNewTile(row, col, value);
            view.getGridView().refreshBoard(this);
        }
        if (actionTaken) {
            view.getGridView().refreshBoard(this);
            handleGameDone();
        }
        return actionTaken;
    }

    public boolean isTileEditable(int row, int col) {
        return model.getTile(row, col).isEditable();
    }

    public boolean isTileFilled(int row, int col) {
        return model.getTile(row, col).isFilled();
    }

    public boolean handleClear(int row, int col) {
        boolean cleared = model.clearCell(row, col);
        if (cleared) {
            view.getGridView().refreshBoard(this);
        }
        return cleared;
    }

    public void handleReset() {
        model.resetBoard();
        view.getGridView().refreshBoard(this);
    }

    public int[][] getBoardData() {
        return model.getBoardData();
    }

    public int[][] getSolution() {
        return model.getSolution();
    }

    public void handleCheck() {
        List<int[]> wrongTiles = model.check();
        view.getGridView().highlightIncorrectCells(wrongTiles);
    }

    public void handleHint() {
        model.getHint();
        view.getGridView().refreshBoard(this);
        handleGameDone();
    }

    public void handleShowRules() {
        AlertView.alertShowRules();
    }

    public void handleExit() {
        System.exit(0);
    }

    public void handleLoadGame() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open saved Sudoku puzzle");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Sudoku Files (*.sudoku)", "*.sudoku"));
        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {
            try {
                model.setCurrentGame(FileHandler.loadFromFile(file));
                view.getGridView().refreshBoard(this);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    public void handleSaveGame() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Sudoku game");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Sudoku Files (*.sudoku)", "*.sudoku"));
        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            if (!file.getPath().endsWith(".sudoku")) {
                file = new File(file.getPath() + ".sudoku");
            }
            try {
                FileHandler.saveToFile(file, model.getCurrentGame());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.exit(0);

    }

    private void handleGameDone() {
        if (model.isDone()) {
            AlertView.alertGameDone();
        }
    }
}
