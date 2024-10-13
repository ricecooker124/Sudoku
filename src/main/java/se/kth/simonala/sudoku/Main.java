package se.kth.simonala.sudoku;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import se.kth.simonala.sudoku.model.SudokuGame;
import se.kth.simonala.sudoku.model.SudokuUtilities;
import se.kth.simonala.sudoku.view.SudokuView;
import se.kth.simonala.sudoku.view.GridView;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        SudokuGame model = new SudokuGame(SudokuUtilities.SudokuLevel.EASY);
        SudokuView view = new SudokuView(model);
        SudokuController controller = new SudokuController(model, view);

        Scene scene = new Scene(view.getNumberPane());

        stage.setTitle("Sudoku");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}