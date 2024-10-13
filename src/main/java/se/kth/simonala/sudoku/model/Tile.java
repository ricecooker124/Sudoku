package se.kth.simonala.sudoku.model;

public class Tile {
    private int value;
    private int correctValue;
    private boolean editable;
    private TileVisibility visibility;

    public Tile(int value, int correctValue, boolean editable) {
        this.value = value;
        this.correctValue = correctValue;
        this.editable = editable;
        this.visibility = initVisibilityStatus(value);
    }

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

    public void setValue(int value) {
        if (editable) {
            this.value = value;
        }
    }

    public void setVisibility(TileVisibility visibility) {
        this.visibility = visibility;
    }

}
