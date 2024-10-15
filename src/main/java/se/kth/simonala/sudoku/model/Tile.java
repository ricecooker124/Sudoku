package se.kth.simonala.sudoku.model;

public class Tile {
    private int value;
    private int correctValue;
    private final boolean editable;
    private boolean isFilled;
    private TileVisibility visibility;

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
