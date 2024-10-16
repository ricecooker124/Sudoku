package se.kth.simonala.sudoku.view;

import se.kth.simonala.sudoku.model.ModelFacade;

import java.util.List;

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
            model.addNewTile(row, col, value);
            view.getGridView().refreshBoard(this);
        }
        if (actionTaken) {
            view.getGridView().refreshBoard(this);
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

    public List<int[]> handleCheck() {
        return model.check();
    }

    public void handleHint() {
        model.getHint();
        view.getGridView().refreshBoard(this);
    }
}
