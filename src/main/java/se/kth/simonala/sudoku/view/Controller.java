package se.kth.simonala.sudoku.view;

import se.kth.simonala.sudoku.model.SudokuGame;

public class Controller {

    private SudokuGame model;
    private SudokuView view;

    public Controller(SudokuGame model, SudokuView view) {
        this.model = model;
        this.view = view;
    }
}
