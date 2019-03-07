package pw.scho.minesweeper;

public class Position {

    private final char column;
    private final int row;

    private Position(char column, int row) {
        this.column = column;
        this.row = row;
    }

    public boolean isValid() {
        return row >= 0 && row < 10 && column >= 'A' && column <= 'J';
    }

    public int getRow() {
        return row;
    }

    public char getColumn() {
        return column;
    }

    public boolean isAdjacent(Position otherPosition) {
        return Math.abs(row - otherPosition.row) <= 1 && Math.abs((int) column - (int) otherPosition.column) <= 1;
    }

    public static Position of(String value) {
        if (value.length() != 2) {
            throw new IllegalArgumentException("Only input length of 2 is supported");
        }

        return new Position(value.charAt(0), Character.getNumericValue(value.charAt(1)));
    }
}
