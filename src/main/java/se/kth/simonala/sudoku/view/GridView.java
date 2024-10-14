package se.kth.simonala.sudoku.view;

import se.kth.simonala.sudoku.model.SudokuUtilities;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;

public class GridView {


    private Label[][] numberTiles; // the tiles/squares to show in the ui grid
    private GridPane numberPane;

    public GridView(Controller controller) {
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

                String tileValue = String.valueOf(controller.getTile(row, col).getValue());

                Label tile = new Label(); // data from model
                tile.setPrefWidth(32);
                tile.setPrefHeight(32);
                tile.setFont(font);
                tile.setAlignment(Pos.CENTER);
                tile.setStyle("-fx-border-color: black; -fx-border-width: 0.5px;"); // css style
                //tile.setOnMouseClicked(tileClickHandler); // add your custom event handler
                // add new tile to grid
                numberTiles[row][col] = tile;
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



