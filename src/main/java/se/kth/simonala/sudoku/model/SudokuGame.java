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
        if (initialMatrix.length != SudokuUtilities.GRID_SIZE ||
                initialMatrix[0].length != SudokuUtilities.GRID_SIZE ||
                initialMatrix[0][0].length != 2) {
            throw new IllegalArgumentException("Invalid initial matrix size.");
        }
        for (int row = 0; row < SudokuUtilities.GRID_SIZE; row++) {
            for (int col = 0; col < SudokuUtilities.GRID_SIZE; col++) {
                int initialValue = initialMatrix[row][col][0];
                int correctValue = initialMatrix[row][col][1];
                boolean editable = (initialValue == 0);
                currentBoard[row][col] = new Tile(initialValue, correctValue, editable);
            }
        }
    }

    public Tile getTile(int row, int col) {
        return currentBoard[row][col];
    }

    public void updateBoard(int row, int col, int value) {
        if (!isDone() && currentBoard[row][col].isEditable()) {
            currentBoard[row][col].setValue(value);
            currentBoard[row][col].setVisibility(TileVisibility.SHOWN);
        } else {
            System.out.println("This tile is not editable.");
        }
    }

    public void clearCell(int row, int col) {
        if (currentBoard[row][col].isEditable()) {
            currentBoard[row][col].clear();
        }
    }

    public void resetBoard() {
        for (int i = 0; i < SudokuUtilities.GRID_SIZE; i++) {
            for (int j = 0; j < SudokuUtilities.GRID_SIZE; j++) {
                clearCell(i, j);
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

    public int[][] getSolution() {
        int[][] solution = new int[SudokuUtilities.GRID_SIZE][SudokuUtilities.GRID_SIZE];
        for (int i = 0; i < SudokuUtilities.GRID_SIZE; i++) {
            for (int j = 0; j < SudokuUtilities.GRID_SIZE; j++) {
                solution[i][j] = currentBoard[i][j].getCorrectValue();
            }
        }
        return solution;
    }

    public boolean check() {
        for (int i = 0; i < SudokuUtilities.GRID_SIZE; i++) {
            for (int j = 0; j < SudokuUtilities.GRID_SIZE; j++) {
                if (currentBoard[i][j].getVisibility() == TileVisibility.SHOWN) {
                    if (currentBoard[i][j].getValue() != currentBoard[i][j].getCorrectValue()) {
                        return false;
                    }
                }
            }
        }
        return true;
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
