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

    public static void alertGameDone() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(("No tiles left"));
        alert.setHeaderText(null);
        alert.setContentText("You won!");
        alert.show();
    }


}
