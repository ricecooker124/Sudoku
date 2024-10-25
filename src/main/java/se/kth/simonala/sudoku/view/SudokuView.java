package se.kth.simonala.sudoku.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import se.kth.simonala.sudoku.model.ModelFacade;


public class SudokuView extends BorderPane {

    private final ModelFacade model;
    private final MenuBar menuBar;
    private final GridView gridView;
    private final Button checkButton, hintButton;
    private Button[] numberButtons;

    public SudokuView(ModelFacade model) {
        this.model = model;

        Controller controller = new Controller(model, this);
        this.menuBar = createMenuBar(controller);
        this.setTop(menuBar);

        gridView = new GridView(controller, this);
        this.setCenter(gridView.getNumberPane());

        checkButton = new Button("Check");
        hintButton = new Button("Hint");
        createActionButtons();
        createNumberButtons();

        addEventHandlers(controller);
    }

    public GridView getGridView() {
        return gridView;
    }

    public void updateGrid(int[][] board) {
        gridView.clearBoard();
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int value = board[row][col];
                gridView.updateTile(row, col, String.valueOf(value));
            }
        }
    }

    private void createActionButtons() {
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

    private void addEventHandlers(Controller controller) {
        checkButton.setOnAction(event -> controller.handleCheck());
        hintButton.setOnAction(event -> controller.handleHint());
    }

    private MenuBar createMenuBar(Controller controller) {
        Menu fileMenu = createFileMenu(controller);
        Menu sudokuMenu = createSudokuMenu(controller);
        Menu helpMenu = createHelpMenu(controller);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, sudokuMenu, helpMenu);
        return menuBar;
    }

    private Menu createFileMenu(Controller controller) {
        Menu fileMenu = new Menu("File");

        MenuItem loadItem = new MenuItem("Load game");
        loadItem.setOnAction(event -> controller.handleLoadGame());

        MenuItem saveItem = new MenuItem("Save game");
        saveItem.setOnAction(event -> controller.handleSaveGame());

        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(event -> controller.handleExit());

        fileMenu.getItems().addAll(loadItem, saveItem, exitItem);
        return fileMenu;
    }

    private Menu createSudokuMenu(Controller controller) {
        Menu sudokuMenu = new Menu("Game");

        MenuItem restartItem = new MenuItem("Restart");
        restartItem.setOnAction(event -> controller.handleRestartGame());

        Menu difficultySubMenu = new Menu("Difficulty level");
        addDifficultyOptions(difficultySubMenu, controller);

        sudokuMenu.getItems().addAll(restartItem, difficultySubMenu);
        return sudokuMenu;
    }

    private void addDifficultyOptions(Menu difficultySubMenu, Controller controller) {
        MenuItem easyItem = new MenuItem("Easy");
        easyItem.setOnAction(event -> controller.handleNewDifficulty("Easy"));

        MenuItem mediumItem = new MenuItem("Medium");
        mediumItem.setOnAction(event -> controller.handleNewDifficulty("Medium"));

        MenuItem hardItem = new MenuItem("Hard");
        hardItem.setOnAction(event -> controller.handleNewDifficulty("Hard"));

        difficultySubMenu.getItems().addAll(easyItem, mediumItem, hardItem);
    }

    private Menu createHelpMenu(Controller controller) {
        Menu helpMenu = new Menu("Help");

        MenuItem clearItem = new MenuItem("Clear");
        clearItem.setOnAction(event -> controller.handleReset());

        MenuItem progressItem = new MenuItem("Board check");
        progressItem.setOnAction(event -> controller.handleCheck());

        MenuItem rulesItem = new MenuItem("How to play");
        rulesItem.setOnAction(event -> controller.handleShowRules());

        helpMenu.getItems().addAll(clearItem, progressItem, rulesItem);
        return helpMenu;
    }

    public MenuBar getMenuBar() {
        return this.menuBar;
    }
}
