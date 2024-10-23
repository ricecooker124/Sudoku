package se.kth.simonala.sudoku.model;

import java.util.List;

public class ModelFacade {

    private SudokuGame model;
    private SudokuUtilities.SudokuLevel currentDifficulty;

    public ModelFacade(SudokuUtilities.SudokuLevel level) {
        this.model = new SudokuGame(level);
        this.currentDifficulty = level;
    }

    public SudokuGame getCurrentGame() {
        return model;
    }

    public void setCurrentGame(SudokuGame game) {
        this.model = game;
    }

    public void generateNewGame(SudokuUtilities.SudokuLevel level) {
        this.currentDifficulty = level;
    }

    public void restartGame() {
        if (currentDifficulty != null) {
            this.model = new SudokuGame(currentDifficulty);
        }
    }

    public void assignNewLevel(String level) {
        if (level.compareTo("Easy")==0) {
            this.model = new SudokuGame(SudokuUtilities.SudokuLevel.EASY);
        } else if (level.compareTo("Medium")==0) {
            this.model = new SudokuGame(SudokuUtilities.SudokuLevel.MEDIUM);
        } else {
            this.model = new SudokuGame(SudokuUtilities.SudokuLevel.HARD);
        }
    }

    public int[][] getBoard() {
        return model.getBoardData();
    }

    public Tile getTile(int row, int col) {
        return model.getTile(row, col);
    }

    public boolean addNewTile(int row, int col, int value) {
        return model.addTile(row, col, value);
    }

    public boolean clearCell(int row, int col) {
        return model.clearCell(row, col);
    }

    public void resetBoard() {
        model.resetBoard();
    }

    public boolean isDone() {
        return model.isDone();
    }

    public int[][] getBoardData() {
        return model.getBoardData();
    }

    public int[][] getSolution() {
        return model.getSolutionData();
    }


    public List<int[]> check() {
        return model.check();
    }

    public void getHint() {
        model.getHint();
    }
}
