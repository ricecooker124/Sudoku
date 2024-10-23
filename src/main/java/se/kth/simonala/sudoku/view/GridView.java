package se.kth.simonala.sudoku.view;

import se.kth.simonala.sudoku.model.SudokuUtilities;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.List;

public class GridView {


    private Label[][] numberTiles; // the tiles/squares to show in the ui grid
    private GridPane numberPane;
    //private SudokuView view;
    private int selectedNumber = 0; // currently selected number

    public GridView(Controller controller, SudokuView view) {
        //this.view = view;
        numberTiles = new Label[SudokuUtilities.GRID_SIZE][SudokuUtilities.GRID_SIZE];
        initNumberTiles(controller);
        // ...
        numberPane = makeNumberPane();
        // ...
    }

    // use this method to get a reference to the number (called by some other class)
    public GridPane getNumberPane() {
        return numberPane;
    }

    // called by constructor (only)
    private final void initNumberTiles(Controller controller) {
        Font font = Font.font("Monospaced", FontWeight.NORMAL, 20);

        for (int row = 0; row < SudokuUtilities.GRID_SIZE; row++) {
            for (int col = 0; col < SudokuUtilities.GRID_SIZE; col++) {
                int tileValue = controller.getTileValue(row, col);
                String displayValue = (tileValue == 0) ? "" : String.valueOf(tileValue);
                Label tile = new Label(displayValue); // data from model


                tile.setPrefWidth(32);
                tile.setPrefHeight(32);
                tile.setFont(font);
                tile.setAlignment(Pos.CENTER);
                tile.setStyle("-fx-border-color: black; -fx-border-width: 0.5px;"); // css style
                //tile.setOnMouseClicked(tileClickHandler); // add your custom event handler
                setupTileClickHandler(tile, controller, row, col);
                // add new tile to grid
                numberTiles[row][col] = tile;
            }
        }
    }

    private void setupTileClickHandler(Label tile, Controller controller, int row, int col) {
        tile.setOnMouseClicked(event -> {
            boolean anyAction = controller.handleNewNumber(row, col, selectedNumber);
            if (anyAction) {
                if (selectedNumber == 0) {
                    tile.setText("");
                } else {
                    tile.setText(String.valueOf(selectedNumber));
                }
            }
        });
    }

    public void updateTile(int row, int col, String value) {
        Label label = numberTiles[row][col];
        label.setText(value);
    }

    public void clearBoard() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                updateTile(row, col, "");
            }
        }
    }

    public void refreshBoard(Controller controller) {
        for (int row = 0; row < SudokuUtilities.GRID_SIZE; row++) {
            for (int col = 0; col < SudokuUtilities.GRID_SIZE; col++) {
                int value = controller.getTileValue(row, col);  // Get the tile's value from the model
                String displayValue = value == 0 ? "" : String.valueOf(value);
                updateTile(row, col, displayValue);
            }
        }
    }

    public void setSelectedNumber(int number) {
        this.selectedNumber = number;
    }

    public void highlightIncorrectCells(List<int[]> incorrectCells) {
        // Återställ alla celler till vit bakgrund först

        // Markera alla felaktiga celler med röd bakgrund
        for (int[] cell : incorrectCells) {
            int row = cell[0];
            int col = cell[1];
            numberTiles[row][col].setStyle("-fx-background-color: red; -fx-border-color: black;");
        }
    }

    public void resetTileStyles() {
        for (int row = 0; row < SudokuUtilities.GRID_SIZE; row++) {
            for (int col = 0; col < SudokuUtilities.GRID_SIZE; col++) {
                numberTiles[row][col].setStyle("-fx-background-color: white; -fx-border-color: black;");
            }
        }
    }

    private final GridPane makeNumberPane() {
        // create the root grid pane
        GridPane root = new GridPane();
        root.setStyle(
                "-fx-border-color: black; -fx-border-width: 1.0px; -fx-background-color: white;");

        // create the 3*3 sections and add the number tiles
        for (int srow = 0; srow < SudokuUtilities.SECTIONS_PER_ROW; srow++) {
            for (int scol = 0; scol < SudokuUtilities.SECTIONS_PER_ROW; scol++) {
                GridPane section = new GridPane();
                section.setStyle( "-fx-border-color: black; -fx-border-width: 0.5px;");

                // add number tiles to this section
                for (int row = 0; row < SudokuUtilities.SECTION_SIZE; row++) {
                    for (int col = 0; col < SudokuUtilities.SECTION_SIZE; col++) {
                        // calculate which tile and add
                        section.add(
                                numberTiles[srow * SudokuUtilities.SECTION_SIZE + row][scol * SudokuUtilities.SECTION_SIZE + col],
                                col, row);
                    }
                }

                // add the section to the root grid pane
                root.add(section, scol, srow);
            }
        }

        return root;
    }
}



