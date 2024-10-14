package se.kth.simonala.sudoku.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import se.kth.simonala.sudoku.model.SudokuGame;

import java.util.ArrayList;

public class SudokuView extends BorderPane {

    private SudokuGame model;
    private MenuBar menuBar;
    private GridView gridView;
    private Button checkButton, hintButton;

    public SudokuView(SudokuGame model) {
        super();
        this.model = model;

        gridView = new GridView();
        this.setCenter(gridView.getNumberPane());

        this.checkButton = new Button("Check");
        this.hintButton = new Button("Hint");
        VBox leftButtons = new VBox(10, this.checkButton, this.hintButton);
        leftButtons.setAlignment(Pos.CENTER);
        this.setLeft(leftButtons);

        VBox numberButtons = new VBox(2); // 5 is spacing between buttons
        for (int i = 1; i <= 10; i++) {
            Button numberButton;
            if (i == 10) {
                numberButton = new Button("C");
            } else {
                numberButton = new Button(String.valueOf(i));
            }
            numberButton.setOnAction(event -> {
                // handle number selection
            });
            numberButtons.getChildren().add(numberButton);
        }

        this.setRight(numberButtons);

        Controller controller = new Controller(model, this);
        createMenuBar(controller);
    }

    private void createMenuBar(Controller controller) {
        Menu fileMenu = new Menu("File");
        MenuItem loadItem = new MenuItem("Load game");
        EventHandler<ActionEvent> loadHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.exit(0); //sav data?
            }
        };
        loadItem.addEventHandler(ActionEvent.ACTION, loadHandler);

        MenuItem saveItem = new MenuItem("Save game");
        EventHandler<ActionEvent> saveHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.exit(0); //sav data?
            }
        };
        saveItem.addEventHandler(ActionEvent.ACTION, saveHandler);

        MenuItem exitItem = new MenuItem("Exit");
        EventHandler<ActionEvent> exitHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.exit(0); //sav data?
            }
        };
        exitItem.addEventHandler(ActionEvent.ACTION, exitHandler);

        fileMenu.getItems().addAll(loadItem, saveItem, exitItem);








        Menu sudokuMenu = new Menu("Game");
        MenuItem restartItem = new MenuItem("restart");
        EventHandler<ActionEvent> restartHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //controller.handleClear();
            }
        };
        restartItem.addEventHandler(ActionEvent.ACTION, restartHandler);


        MenuItem difficultyItem = new MenuItem("Difficulty level");
        EventHandler<ActionEvent> difficultyHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //controller.handleClear();
            }
        };
        difficultyItem.addEventHandler(ActionEvent.ACTION, difficultyHandler);

        sudokuMenu.getItems().addAll(restartItem, difficultyItem);


        Menu helpMenu = new Menu("Help");
        MenuItem clearItem = new MenuItem("Clear");
        EventHandler<ActionEvent> clearHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //
            }
        };
        clearItem.addEventHandler(ActionEvent.ACTION, clearHandler);

        MenuItem progressItem = new MenuItem("Board check");
        EventHandler<ActionEvent> progressHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        };
        progressItem.addEventHandler(ActionEvent.ACTION, progressHandler);

        MenuItem rulesItem = new MenuItem("How to play");
        EventHandler<ActionEvent> rulesHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        };
        rulesItem.addEventHandler(ActionEvent.ACTION, rulesHandler);
        helpMenu.getItems().addAll(clearItem, progressItem, rulesItem);



        menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, sudokuMenu, helpMenu);
    }

    public MenuBar getMenuBar() {
        return this.menuBar;
    }
}
