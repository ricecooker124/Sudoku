package se.kth.simonala.sudoku.model;

import java.io.Serializable;

/**
 * This class represent every tile in a 9x9 sudoku-board.
 * Each tile has a number and are either shown or hidden, declared by the enum.
 */
public class Tile implements Serializable {
    private int value;
    private int correctValue;
    private final boolean editable;
    private boolean isFilled;
    private TileVisibility visibility;

    /**
     * The constructor declares the new tile with the variables and the enum.
     * @param value
     * @param correctValue
     * @param editable
     */
    public Tile(int value, int correctValue, boolean editable) {
        this.value = value;
        this.correctValue = correctValue;
        this.editable = editable;
        this.isFilled = isFilled(value);
        this.visibility = initVisibilityStatus(value);
    }

    private boolean isFilled(int value) {
        return value != 0;
    }

    /**
     * Sets the status of the tile as hidden if the number in the tile equals 0, else the status will be set to shown.
     * @param value
     * @return the status of the tile.
     */
    private TileVisibility initVisibilityStatus(int value) {
        if (value == 0) {
            return TileVisibility.HIDDEN;
        }
        return TileVisibility.SHOWN;
    }

    public int getValue() {
        return value;
    }

    public int getCorrectValue() {
        return correctValue;
    }

    public boolean isEditable() {
        return editable;
    }

    public TileVisibility getVisibility() {
        return visibility;
    }

    public boolean isFilled() {
        return isFilled;
    }

    public void setValue(int value) {
        this.value = value;
        if (value != 0 && isEditable() && !isFilled()) {
            this.isFilled = true;
            this.visibility = TileVisibility.SHOWN;
        } else if (value == 0) {
            this.isFilled = false;
            this.visibility = TileVisibility.HIDDEN;
        }
    }

    public void setFilled(boolean filled) {
        isFilled = filled;
    }

    public void setVisibility(TileVisibility visibility) {
        this.visibility = visibility;
    }
}
