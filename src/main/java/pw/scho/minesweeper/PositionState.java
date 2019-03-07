package pw.scho.minesweeper;

public class PositionState {

    private final Position position;

    private State state = State.HIDDEN;

    public PositionState(Position position) {
        this.position = position;
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
        return state == State.REVEALED && position.isBomb();
    }

    @Override
    public String toString() {
        return state.toString(position);
    }

    private enum State {
        HIDDEN {
            @Override
            public String toString(Position position) {
                return "■";
            }
        },

        REVEALED {
            @Override
            public String toString(Position position) {
                if (position.isBomb()) {
                    return "x";
                }

                long numberOfAdjacentBombs = position.getAdjacentPositions().stream().filter(Position::isBomb).count();
                if (numberOfAdjacentBombs == 0) {
                    return " ";
                }
                return String.valueOf(numberOfAdjacentBombs);
            }
        },

        MARKED_AS_BOMB {
            @Override
            public String toString(Position position) {
                return "⚑";
            }
        };

        public abstract String toString(Position position);
    }
}
