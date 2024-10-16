package se.kth.simonala.sudoku.view;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertView {

    private AlertView() {
    }

    public static void alertShowRules() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sudoku rules");
        alert.setHeaderText(null);
        alert.setContentText(
                "The goal of the game is to fill all the empty tiles with numbers from 1-9.\n" +
                        "Each number must appear exactly once in every row, column, and three-by-three subgrid inside of the board.\n" +
                        "You may press 'Check' or 'Help' -> 'Validate tiles' to check if you are on the right track.\n" +
                        "Good luck!\n");
        alert.show();
    }

    public static void alertChooseDifficulty(Controller controller) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Choose Difficulty");
        alert.setHeaderText(null);
        alert.setContentText("What difficulty do you want to play?");

        ButtonType easyButton = new ButtonType("Easy");
        ButtonType mediumButton = new ButtonType("Medium");
        ButtonType hardButton = new ButtonType("Hard");

        alert.getButtonTypes().setAll(easyButton, mediumButton, hardButton);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent()) {
            if (result.get() == easyButton) {
                //controller.setDifficulty(SudokuUtilities.SudokuLevel.EASY);
            } else if (result.get() == mediumButton) {
                //controller.setDifficulty(SudokuUtilities.SudokuLevel.MEDIUM);
            } else if (result.get() == hardButton) {
                //controller.setDifficulty(SudokuUtilities.SudokuLevel.HARD);
            }
        }
    }

    public static void alertGameDone(boolean gameWon) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(("No tiles left"));
        alert.setHeaderText(null);
        if(gameWon == true) {
            alert.setContentText("You won!");
        } else {
            alert.setContentText("You lost!");
        }
        alert.show();
    }


}
