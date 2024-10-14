package se.kth.simonala.sudoku.view;

import se.kth.simonala.sudoku.model.ModelFacade;
import se.kth.simonala.sudoku.model.SudokuGame;
import se.kth.simonala.sudoku.model.Tile;

public class Controller {

    private ModelFacade model;
    private SudokuView view;

    public Controller(ModelFacade model, SudokuView view) {
        this.model = model;
        this.view = view;
    }

    public void handleNewNumber(int row, int col, int value) {
        model.updateBoard(row, col, value);

    }

    public void handleClear() {
        model.resetBoard();
    }

    public boolean handleCheck() {
        return model.check();

    }

    public void handleHint() {
        model.getHint();
    }

    public Tile getTile(int row, int col) {
        return model.getTile(row, col);
    }

    public int[][] getBoardData() {
        return model.getBoardData();
    }

    public int[][] getSolution() {
        return model.getSolution();
    }
}
