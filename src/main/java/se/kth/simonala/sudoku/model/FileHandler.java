package se.kth.simonala.sudoku.model;

import java.io.*;

public class FileHandler {

    /**
     * Hints on how to implement serialization and deserialization
     * of sudoku model.
     */
    private FileHandler() {
    }

    /**
     * Call this method before the application exits, to store the sudoku model,
     * in serialized form.
     */
    public static void saveToFile(File file, SudokuGame game) throws IOException {

        ObjectOutputStream out = null;

        try {
            FileOutputStream fout = new FileOutputStream(file);
            out = new ObjectOutputStream(fout);
            out.writeObject(game);
        } finally {
            if(out != null){
                out.close();
            }
        }
    }

    /**
     * Call this method at startup of the application, to deserialize the sudoku model
     * from file the specified file.
     */
    @SuppressWarnings("unchecked")
    public static SudokuGame loadFromFile(File file) throws IOException, ClassNotFoundException{
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))){
            return (SudokuGame) in.readObject();
        }
    }

}