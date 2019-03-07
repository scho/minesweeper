package pw.scho.minesweeper;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Game {

    private static final Random RANDOM = new Random();

    private final List<List<State>> states;

    public Game(List<List<State>> states) {
        this.states = states;
    }

    public void print(Consumer<String> printer) {
        String columnLabels = states.get(0).stream().map(State::getPosition).map(Position::getColumn).map(Object::toString).collect(Collectors.joining(" "));

        printer.accept("  " + columnLabels);

        states.stream().map(row -> {
            int rowLabel = row.get(0).getPosition().getRow();
            return rowLabel + " " + row.stream().map(State::toString).collect(Collectors.joining(" "));
        }).forEach(printer);
    }

    public void reveal(Position position) {
        State state = findPositionState(position);

        state.reveal();
    }

    public void markAsBomb(Position position) {
        State state = findPositionState(position);

        state.markAsBomb();
    }

    public boolean isGameOver() {
        return states.stream().anyMatch(row -> row.stream().anyMatch(State::isGameOver));
    }

    public boolean isCorrect() {
        return states.stream().allMatch(row -> row.stream().allMatch(State::isCorrect));
    }

    public void revealAllAfterGameOver() {
        states.forEach(row -> row.forEach(State::revealAfterGameOver));
    }

    private State findPositionState(Position position) {
        State state = states.get(position.getRow()).get(position.getColumn() - (int) 'A');

        if (state == null) {
            throw new IllegalStateException("Position does not exist");
        }
        return state;
    }

    public static Game newDefault() {
        var states = new LinkedList<List<State>>();
        var allStates = new LinkedList<State>();
        IntStream.range(0, 5).forEach(row -> {
            List<State> rowStates = new LinkedList<>();
            List.of('A', 'B', 'C', 'D', 'E').forEach(column -> {
                State state;

                if (RANDOM.nextInt(10) > 7) {
                    state = State.bomb(column + String.valueOf(row));
                } else {
                    state = State.empty(column + String.valueOf(row));
                }

                rowStates.add(state);
            });
            states.add(rowStates);
            allStates.addAll(rowStates);
        });
        allStates.forEach(state -> state.withAllStates(allStates));

        return new Game(states);
    }

}
