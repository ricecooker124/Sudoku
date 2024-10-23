package se.kth.simonala.sudoku.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import se.kth.simonala.sudoku.model.FileHandler;
import se.kth.simonala.sudoku.model.ModelFacade;
import se.kth.simonala.sudoku.model.SudokuGame;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class SudokuView extends BorderPane implements Serializable {

    private ModelFacade model;
    private MenuBar menuBar;
    private GridView gridView;
    private Button checkButton, hintButton;
    private Button[] numberButtons;

    public SudokuView(ModelFacade model) {
        super();
        this.model = model;

        Controller controller = new Controller(model, this);
        createMenuBar(controller);

        gridView = new GridView(controller, this);
        this.setCenter(gridView.getNumberPane());

        createActionButtons(controller);
        createNumberButtons();
        addEventHandlers(controller);
    }

    public GridView getGridView() {
        return gridView;
    }

    public void updateGrid(int[][] board) {
        gridView.clearBoard();  // Clear the current grid before updating
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int value = board[row][col];
                gridView.updateTile(row, col, String.valueOf(value)); // Update each tile in the grid
            }
        }
    }

    private void createActionButtons(Controller controller) {
        checkButton = new Button("Check");
        hintButton = new Button("Hint");

        VBox leftButtons = new VBox(10, checkButton, hintButton);
        leftButtons.setAlignment(Pos.CENTER);
        this.setLeft(leftButtons);
    }

    private void createNumberButtons() {
        numberButtons = new Button[10]; // 1-9 + Clear
        VBox numberButtonLayout = new VBox(2);

        for (int i = 1; i <= 9; i++) {
            final int number = i;
            numberButtons[i] = new Button(String.valueOf(i));
            numberButtons[i].setOnAction(event -> gridView.setSelectedNumber(number));
            numberButtonLayout.getChildren().add(numberButtons[i]);
        }

        numberButtons[0] = new Button("C");
        numberButtons[0].setOnAction(event -> gridView.setSelectedNumber(0));
        numberButtonLayout.getChildren().add(numberButtons[0]);

        this.setRight(numberButtonLayout);
    }

    public void addEventHandlers(Controller controller) {
        EventHandler<ActionEvent> checkHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                List<int[]> wrongTiles = controller.handleCheck();
                gridView.highlightIncorrectCells(wrongTiles);
            }
        };
        checkButton.addEventHandler(ActionEvent.ACTION, checkHandler);

        EventHandler<ActionEvent> hintHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                controller.handleHint();
            }
        };
        hintButton.addEventHandler(ActionEvent.ACTION, hintHandler);
    }

    private void createMenuBar(Controller controller) {
        Menu fileMenu = new Menu("File");
        MenuItem loadItem = new MenuItem("Load game");
        loadItem.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open saved Sudoku puzzle");
            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Sudoku Files (*.sudoku)", "*.sudoku");
            fileChooser.getExtensionFilters().add(filter);
            File file = fileChooser.showOpenDialog(new Stage());

            if(file != null) {
                try {
                    SudokuGame loadedGame = FileHandler.loadFromFile(file);
                    model.setCurrentGame(loadedGame); // Uppdatera model med det laddade spelet
                    gridView.refreshBoard(controller);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace(); // Hantera fel
                }
            }
        });

        MenuItem saveItem = new MenuItem("Save game");
        saveItem.setOnAction(event ->{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Sudoku game");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Sudoku Files (*.sudoku)", "*.sudoku");
            fileChooser.getExtensionFilters().add(extFilter);
            File file = fileChooser.showSaveDialog(new Stage());

            if(file != null){
                if (!file.getPath().endsWith(".sudoku")) {
                    file = new File(file.getPath() + ".sudoku");
                }
                try{
                    SudokuGame currentGame = model.getCurrentGame();
                    FileHandler.saveToFile(file, currentGame);
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
            System.exit(0);
        });

        MenuItem exitItem = new MenuItem("Exit");
        EventHandler<ActionEvent> exitHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.exit(0);
            }
        };
        exitItem.addEventHandler(ActionEvent.ACTION, exitHandler);

        fileMenu.getItems().addAll(loadItem, saveItem, exitItem);

        Menu sudokuMenu = new Menu("Game");
        MenuItem restartItem = new MenuItem("Restart");
        EventHandler<ActionEvent> restartHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                controller.handleRestartGame();
            }
        };
        restartItem.addEventHandler(ActionEvent.ACTION, restartHandler);

        Menu difficultySubMenu = new Menu("Difficulty level");
        MenuItem easyItem = new MenuItem("Easy");
        MenuItem mediumItem = new MenuItem("Medium");
        MenuItem hardItem = new MenuItem("Hard");

        EventHandler<ActionEvent> easyHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                controller.handleNewDifficulty("Easy");
            }
        };
        difficultySubMenu.addEventHandler(ActionEvent.ACTION, easyHandler);

        EventHandler<ActionEvent> mediumHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                controller.handleNewDifficulty("Medium");
            }
        };
        difficultySubMenu.addEventHandler(ActionEvent.ACTION, mediumHandler);

        EventHandler<ActionEvent> hardHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                controller.handleNewDifficulty("Hard");
            }
        };
        difficultySubMenu.addEventHandler(ActionEvent.ACTION, hardHandler);
        difficultySubMenu.getItems().addAll(easyItem, mediumItem, hardItem);
        sudokuMenu.getItems().addAll(restartItem, difficultySubMenu);

        Menu helpMenu = new Menu("Help");
        MenuItem clearItem = new MenuItem("Clear");
        EventHandler<ActionEvent> clearHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                controller.handleReset();
            }
        };
        clearItem.addEventHandler(ActionEvent.ACTION, clearHandler);

        MenuItem progressItem = new MenuItem("Board check");
        EventHandler<ActionEvent> progressHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                List<int[]> wrongTiles = controller.handleCheck();
                gridView.highlightIncorrectCells(wrongTiles);
            }
        };
        progressItem.addEventHandler(ActionEvent.ACTION, progressHandler);

        MenuItem rulesItem = new MenuItem("How to play");
        EventHandler<ActionEvent> rulesHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                controller.handleShowRules();
            }
        };
        rulesItem.addEventHandler(ActionEvent.ACTION, rulesHandler);
        helpMenu.getItems().addAll(clearItem, progressItem, rulesItem);

        menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, sudokuMenu, helpMenu);
        this.setTop(menuBar);
    }

    public MenuBar getMenuBar() {
        return this.menuBar;
    }
}
