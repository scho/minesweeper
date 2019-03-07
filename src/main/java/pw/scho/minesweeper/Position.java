package pw.scho.minesweeper;

import java.util.List;
import java.util.stream.Collectors;

public class Position {

    private final char column;
    private final int row;
    private final boolean bomb;

    private List<Position> adjacentPositions;

    private Position(char column, int row, boolean bomb) {
        this.column = column;
        this.row = row;
        this.bomb = bomb;
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

    public List<Position> getAdjacentPositions() {
        return adjacentPositions;
    }

    public boolean isAdjacent(Position otherPosition) {
        return Math.abs(row - otherPosition.row) <= 1 && Math.abs((int) column - (int) otherPosition.column) <= 1;
    }

    public Position withPositions(List<Position> allPositions) {
        adjacentPositions = allPositions.stream().filter(position -> position.isAdjacent(this)).collect(Collectors.toList());

        return this;
    }

    public boolean isBomb() {
        return bomb;
    }

    public static Position bomb(String value) {
        if (value.length() != 2) {
            throw new IllegalArgumentException("Only input length of 2 is supported");
        }

        return new Position(value.charAt(0), Character.getNumericValue(value.charAt(1)), true);
    }

    public static Position empty(String value) {
        if (value.length() != 2) {
            throw new IllegalArgumentException("Only input length of 2 is supported");
        }

        return new Position(value.charAt(0), Character.getNumericValue(value.charAt(1)), false);
    }
}
