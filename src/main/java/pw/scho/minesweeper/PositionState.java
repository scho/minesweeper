package pw.scho.minesweeper;

import java.util.List;
import java.util.stream.Collectors;

public class PositionState {

    private final Position position;
    private final boolean bomb;

    private List<PositionState> adjacentPositionStates;

    private State state = State.HIDDEN;

    public PositionState(Position position, boolean bomb) {
        this.position = position;
        this.bomb = bomb;
    }

    public Position getPosition() {
        return position;
    }

    public boolean isBomb() {
        return bomb;
    }

    public void markAsBomb() {
        if (state != State.HIDDEN) {
            throw new IllegalStateException();
        }

        this.state = State.MARKED_AS_BOMB;
    }

    public void reveal() {
        if (state != State.HIDDEN) {
            throw new IllegalStateException();
        }

        this.state = State.REVEALED;
    }

    public boolean isGameOver() {
        return state == State.REVEALED && isBomb();
    }


    public PositionState withAllStates(List<PositionState> allStates) {
        adjacentPositionStates = allStates.stream().filter(state -> state.position.isAdjacent(this.position)).collect(Collectors.toList());

        return this;
    }

    @Override
    public String toString() {
        return state.toString(this);
    }

    public static PositionState bomb(String position) {
        return new PositionState(Position.of(position), true);
    }

    public static PositionState empty(String position) {
        return new PositionState(Position.of(position), false);
    }

    private enum State {
        HIDDEN {
            @Override
            public String toString(PositionState positionState) {
                return "■";
            }
        },

        REVEALED {
            @Override
            public String toString(PositionState positionState) {
                if (positionState.isBomb()) {
                    return "x";
                }

                long numberOfAdjacentBombs = positionState.adjacentPositionStates.stream().filter(PositionState::isBomb).count();
                if (numberOfAdjacentBombs == 0) {
                    return " ";
                }
                return String.valueOf(numberOfAdjacentBombs);
            }
        },

        MARKED_AS_BOMB {
            @Override
            public String toString(PositionState positionState) {
                return "⚑";
            }
        };

        public abstract String toString(PositionState positionState);
    }
}
