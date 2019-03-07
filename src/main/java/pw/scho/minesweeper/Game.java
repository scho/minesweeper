package pw.scho.minesweeper;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Game {

    private static final Random RANDOM = new Random();

    private final List<List<PositionState>> states;

    public Game(List<List<PositionState>> states) {
        this.states = states;
    }

    public void print(Consumer<String> printer) {
        states.stream().map(row -> row.stream().map(PositionState::toString).collect(Collectors.joining(" "))).forEach(printer);
    }

    public void reveal(char column, int row) {
        PositionState positionState = findPositionState(column, row);

        positionState.reveal();
    }

    public void markAsBomb(char column, int row) {
        PositionState positionState = findPositionState(column, row);

        positionState.markAsBomb();
    }

    private PositionState findPositionState(int column, int row) {
        PositionState positionState = states.get(row).get(column - (int) 'A');

        if (positionState == null) {
            throw new IllegalStateException("Position does not exist");
        }
        return positionState;
    }

    public static Game newDefault() {
        var states = new LinkedList<List<PositionState>>();
        var positions = new LinkedList<Position>();
        IntStream.range(0, 5).forEach(row -> {
            List<PositionState> rowStates = new LinkedList<>();
            List.of('A', 'B', 'C', 'D', 'E').forEach(column -> {
                Position position;

                if (RANDOM.nextInt(10) > 7) {
                    position = Position.bomb(column + String.valueOf(row));
                } else {
                    position = Position.empty(column + String.valueOf(row));
                }

                positions.add(position);
                rowStates.add(new PositionState(position));
            });
            states.add(rowStates);
        });
        positions.forEach(position -> position.withPositions(positions));

        return new Game(states);
    }

}
