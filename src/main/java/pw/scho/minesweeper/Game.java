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
        String columnLabels = states.get(0).stream().map(PositionState::getPosition).map(Position::getColumn).map(Object::toString).collect(Collectors.joining(" "));

        printer.accept("  " + columnLabels);

        states.stream().map(row -> {
            int rowLabel = row.get(0).getPosition().getRow();
            return rowLabel + " " + row.stream().map(PositionState::toString).collect(Collectors.joining(" "));
        }).forEach(printer);
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
        var allStates = new LinkedList<PositionState>();
        IntStream.range(0, 5).forEach(row -> {
            List<PositionState> rowStates = new LinkedList<>();
            List.of('A', 'B', 'C', 'D', 'E').forEach(column -> {
                PositionState positionState;

                if (RANDOM.nextInt(10) > 7) {
                    positionState = PositionState.bomb(column + String.valueOf(row));
                } else {
                    positionState = PositionState.empty(column + String.valueOf(row));
                }

                rowStates.add(positionState);
            });
            states.add(rowStates);
            allStates.addAll(rowStates);
        });
        allStates.forEach(positionState -> positionState.withAllStates(allStates));

        return new Game(states);
    }

}
