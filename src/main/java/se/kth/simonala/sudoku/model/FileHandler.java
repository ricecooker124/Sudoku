package se.kth.simonala.sudoku.model;

import java.io.*;

public class FileHandler {

    private FileHandler(){
    }

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

    @SuppressWarnings("unchecked")
    public static SudokuGame loadFromFile(File file) throws IOException, ClassNotFoundException{
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))){
            return (SudokuGame) in.readObject();
        }
    }

}