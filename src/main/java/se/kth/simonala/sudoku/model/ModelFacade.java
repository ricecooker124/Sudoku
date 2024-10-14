package se.kth.simonala.sudoku.model;

public class ModelFacade {

    private SudokuGame model;

    public ModelFacade(SudokuUtilities.SudokuLevel level) {
        this.model = new SudokuGame(level);
    }

    public Tile getTile(int row, int col) {
        return model.getTile(row, col);
    }

    public void updateBoard(int row, int col, int value) {
        model.updateBoard(row, col, value);
    }

    public void clearCell(int row, int col) {
        model.clearCell(row, col);
    }

    public void resetBoard() {
        model.resetBoard();
    }

    public boolean check() {
        return model.check();
    }

    public void getHint() {
        model.getHint();
    }

    public int[][] getBoardData() {
        return model.getBoardData();
    }

    public int[][] getSolution() {
        return model.getSolution();
    }
}
