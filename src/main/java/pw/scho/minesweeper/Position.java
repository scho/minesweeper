package pw.scho.minesweeper;

public class Position {

    private final char row;
    private final int column;

    private Position(char row, int column) {
        this.row = row;
        this.column = column;
    }

    public boolean isValid() {
        return column >= 0 && column < 10 && row >= 'A' && row <= 'J';
    }

    public char getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public boolean isAdjacent(Position otherPosition) {
        return Math.abs(column - otherPosition.column) <= 1 && Math.abs((int) row - (int) otherPosition.row) <= 1;
    }

    public static Position of(String value) {
        if (value.length() != 2) {
            throw new IllegalArgumentException("Only input length of 2 is supported");
        }

        return new Position(value.charAt(0), Character.getNumericValue(value.charAt(1)));
    }
}
