package se.kth.simonala.sudoku.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SudokuGame {

    private Tile[][] currentBoard;

    public SudokuGame(SudokuUtilities.SudokuLevel level) {
        this.currentBoard = new Tile[SudokuUtilities.GRID_SIZE][SudokuUtilities.GRID_SIZE];
        initializeBoard(SudokuUtilities.generateSudokuMatrix(level));
    }

    private void initializeBoard(int[][][] initialMatrix) {
        for (int row = 0; row < SudokuUtilities.GRID_SIZE; row++) {
            for (int col = 0; col < SudokuUtilities.GRID_SIZE; col++) {
                int initialValue = initialMatrix[row][col][0];
                int correctValue = initialMatrix[row][col][1];
                boolean editable = (initialValue == 0);
                currentBoard[row][col] = new Tile(initialValue, correctValue, editable);
            }
        }
    }

    public Tile getTile(int row, int col) throws IllegalArgumentException {
        if (row < 0 || row >= SudokuUtilities.GRID_SIZE || col < 0 || col >= SudokuUtilities.GRID_SIZE) {
            throw new IllegalArgumentException("Row or column is out of bounds.");
        }
        return currentBoard[row][col];
    }

    public boolean addTile(int row, int col, int value) {
        if (!isDone() && currentBoard[row][col].isEditable() && !currentBoard[row][col].isFilled()) {
            currentBoard[row][col].setValue(value);
            return true;
        }
        return false;
    }

    public boolean clearCell(int row, int col) {
        if (currentBoard[row][col].isEditable()) {
            currentBoard[row][col].setValue(0);
            return true;
        }
        return false;
    }

    public void resetBoard() {
        for (int i = 0; i < SudokuUtilities.GRID_SIZE; i++) {
            for (int j = 0; j < SudokuUtilities.GRID_SIZE; j++) {
                if (currentBoard[i][j].isEditable()) {
                    currentBoard[i][j].setValue(0);
                }
            }
        }
    }

    public boolean isDone() {
        for (int i = 0; i < SudokuUtilities.GRID_SIZE; i++) {
            for (int j = 0; j < SudokuUtilities.GRID_SIZE; j++) {
                if (currentBoard[i][j].getValue() != currentBoard[i][j].getCorrectValue()) {
                    return false;
                }
            }
        }
        return true;
    }

    public int[][] getBoardData() {
        int[][] copy = new int[SudokuUtilities.GRID_SIZE][SudokuUtilities.GRID_SIZE];
        for (int i = 0; i < SudokuUtilities.GRID_SIZE; i++) {
            for (int j = 0; j < SudokuUtilities.GRID_SIZE; j++) {
                copy[i][j] = currentBoard[i][j].getValue();
            }
        }
        return copy;
    }

    public int[][] getSolutionData() {
        int[][] solution = new int[SudokuUtilities.GRID_SIZE][SudokuUtilities.GRID_SIZE];
        for (int i = 0; i < SudokuUtilities.GRID_SIZE; i++) {
            for (int j = 0; j < SudokuUtilities.GRID_SIZE; j++) {
                solution[i][j] = currentBoard[i][j].getCorrectValue();
            }
        }
        return solution;
    }

    public List<int[]> check() {
        List<int[]> wrongTiles = new ArrayList<>();

        for (int row = 0; row < SudokuUtilities.GRID_SIZE; row++) {
            for (int col = 0; col < SudokuUtilities.GRID_SIZE; col++) {
                if (currentBoard[row][col].isFilled()) {
                    if (currentBoard[row][col].getValue() != currentBoard[row][col].getCorrectValue()) {
                        wrongTiles.add(new int[]{row, col});
                    }
                }
            }
        }
        return wrongTiles;
    }

    public void getHint() {
        List<int[]> editableCells = new ArrayList<>();

        for (int row = 0; row < SudokuUtilities.GRID_SIZE; row++) {
            for (int col = 0; col < SudokuUtilities.GRID_SIZE; col++) {
                if (currentBoard[row][col].isEditable() && currentBoard[row][col].getValue() == 0) {
                    editableCells.add(new int[]{row, col});
                }
            }
        }

        if (!editableCells.isEmpty()) {
            Random rand = new Random();
            int[] randomCell = editableCells.get(rand.nextInt(editableCells.size()));

            int row = randomCell[0];
            int col = randomCell[1];
            currentBoard[row][col].setValue(currentBoard[row][col].getCorrectValue());
        }
    }
}
