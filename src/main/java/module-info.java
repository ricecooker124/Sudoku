module se.kth.simonala.sudoku {
    requires javafx.controls;
    requires javafx.fxml;


    opens se.kth.simonala.sudoku to javafx.fxml;
    exports se.kth.simonala.sudoku;
}