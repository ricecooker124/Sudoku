package se.kth.simonala.sudoku.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class represents the board for a 9x9 Sudoku game.
 */
public class SudokuGame implements Serializable {


    private Tile[][] currentBoard;

    /**
     * The constructor creates the variable with the correct size of the arrays and calls the method to initialize the board.
     * @param level
     */
    public SudokuGame(SudokuUtilities.SudokuLevel level) {
        this.currentBoard = new Tile[SudokuUtilities.GRID_SIZE][SudokuUtilities.GRID_SIZE];
        initializeBoard(SudokuUtilities.generateSudokuMatrix(level));
    }

    /**
     * This method creates the Sudoku-board.
     * The variable initialValue will be provided with the start value of the game,
     * while the variable correctValue will be provided with the solution of the Sudoku puzzle.
     * The variable editable is either true or false, depending on if the tile can be changed or not.
     * The new Sudoku board is created in the variable currentBoard with the correct information in every tile.
     * @param initialMatrix
     */
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

    /**
     * This methode adds the value to the tile.
     * @param row
     * @param col
     * @param value
     * @return true if the value has been added, else false.
     */
    public boolean addTile(int row, int col, int value) {
        if (!isDone() && currentBoard[row][col].isEditable() && !currentBoard[row][col].isFilled()) {
            currentBoard[row][col].setValue(value);
            return true;
        }
        return false;
    }

    /**
     * This methode clears a tile, if it is editable.
     * @param row
     * @param col
     * @return true if the cell has been cleared, else false.
     */
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

    /**
     * This method checks if the puzzle has been solved by comparing the value of the tile with the correct value of the tile.
     * @return true if the puzzle has been solved correctly, else false.
     */
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

    /**
     * This method makes a deep copy of the current board.
     * @return the copy.
     */
    public int[][] getBoardData() {
        int[][] copy = new int[SudokuUtilities.GRID_SIZE][SudokuUtilities.GRID_SIZE];
        for (int i = 0; i < SudokuUtilities.GRID_SIZE; i++) {
            for (int j = 0; j < SudokuUtilities.GRID_SIZE; j++) {
                copy[i][j] = currentBoard[i][j].getValue();
            }
        }
        return copy;
    }

    /**
     * This methode creates a new variable which will represents the solution of the puzzle.
     * @return the solution of the puzzle.
     */
    public int[][] getSolutionData() {
        int[][] solution = new int[SudokuUtilities.GRID_SIZE][SudokuUtilities.GRID_SIZE];
        for (int i = 0; i < SudokuUtilities.GRID_SIZE; i++) {
            for (int j = 0; j < SudokuUtilities.GRID_SIZE; j++) {
                solution[i][j] = currentBoard[i][j].getCorrectValue();
            }
        }
        return solution;
    }

    /**
     *
     * This methode checks all the tiles in the currentBoard and compares it to the solution.
     * It creates a new List to store the index of the incorrect tiles.
     * @return the list with the index of the incorrect tiles.
     */
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

    /**
     * This methode saves the row and column to all the tiles who are editable and equal to 0 in a new ArrayList.
     * Then generates a random index in the ArrayList and sets the row and column to the value from the list on the index.
     * Sets the tile from the randomized row and column to the correct value from the solution.
     * Lastly changes the visibility status to shown, so the number on the tile will appear on the board.
     */
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
