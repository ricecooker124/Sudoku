package se.kth.simonala.sudoku.model;

public class ModelFacade {

    private SudokuGame model;
    private SudokuUtilities.SudokuLevel currentDifficulty;

    public ModelFacade(SudokuUtilities.SudokuLevel level) {
        this.model = new SudokuGame(level);
        this.currentDifficulty = level;
    }

    public void generateNewGame(SudokuUtilities.SudokuLevel level) {
        this.currentDifficulty = level;
        //this.model = SudokuUtilities.generateNewGame(level);
    }

    public void restartGame() {
        if (currentDifficulty != null) {
            this.model = new SudokuGame(currentDifficulty);
            //this.model = SudokuUtilities.generateNewGame(currentDifficulty);
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

    public void isDone() {
        model.isDone();
    }

    public int[][] getBoardData() {
        return model.getBoardData();
    }

    public int[][] getSolution() {
        return model.getSolutionData();
    }


    public boolean check() {
        return model.check();
    }

    public void getHint() {
        model.getHint();
    }
}
