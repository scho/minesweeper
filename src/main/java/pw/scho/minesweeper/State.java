package pw.scho.minesweeper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class State {

    private final Position position;
    private final boolean bomb;

    private List<State> adjacentStates = Collections.emptyList();

    private SelectedState selectedState = SelectedState.HIDDEN;

    public State(Position position, boolean bomb) {
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
        if (selectedState != SelectedState.HIDDEN) {
            throw new IllegalStateException();
        }

        this.selectedState = SelectedState.MARKED_AS_BOMB;
    }

    public void reveal() {
        if (selectedState != SelectedState.HIDDEN) {
            throw new IllegalStateException();
        }

        revealIfNoBombsAdjacent();
        this.selectedState = SelectedState.REVEALED;
    }

    public void revealAfterGameOver() {
        if (selectedState != SelectedState.HIDDEN) {
            return;
        }

        selectedState = SelectedState.REVEALED_AFTER_GAME_OVER;
    }

    private void revealIfNoBombsAdjacent() {
        if (isBomb()) {
            return;
        }

        if (selectedState != SelectedState.HIDDEN) {
            return;
        }

        this.selectedState = SelectedState.REVEALED;

        if (getNumberOfAdjacentBombs() == 0) {
            adjacentStates.forEach(State::revealIfNoBombsAdjacent);
        }
    }

    public boolean isGameOver() {
        return selectedState == SelectedState.REVEALED && isBomb();
    }

    public boolean isCorrect() {
        if (getNumberOfAdjacentBombs() == 0) {
            return true;
        }

        if (selectedState == SelectedState.HIDDEN) {
            return false;
        }

        if (selectedState == SelectedState.MARKED_AS_BOMB && !isBomb()) {
            return false;
        }

        return true;
    }

    public State withAllStates(List<State> allStates) {
        adjacentStates = allStates.stream().filter(state -> state.position.isAdjacent(this.position)).collect(Collectors.toList());

        return this;
    }

    private long getNumberOfAdjacentBombs() {
        return adjacentStates.stream().filter(State::isBomb).count();
    }

    @Override
    public String toString() {
        return selectedState.toString(this);
    }

    public static State bomb(String position) {
        return new State(Position.of(position), true);
    }

    public static State empty(String position) {
        return new State(Position.of(position), false);
    }

    private enum SelectedState {
        HIDDEN {
            @Override
            public String toString(State state) {
                return "■";
            }
        },

        REVEALED {
            @Override
            public String toString(State state) {
                if (state.isBomb()) {
                    return "x";
                }

                return numberOfBombs(state);
            }
        },

        MARKED_AS_BOMB {
            @Override
            public String toString(State state) {
                return "⚑";
            }
        },

        REVEALED_AFTER_GAME_OVER {
            @Override
            public String toString(State state) {
                if (state.isBomb()) {
                    return "o";
                }

                return numberOfBombs(state);
            }
        };


        public abstract String toString(State state);

        String numberOfBombs(State state) {
            long numberOfAdjacentBombs = state.getNumberOfAdjacentBombs();
            if (numberOfAdjacentBombs == 0) {
                return " ";
            }
            return String.valueOf(numberOfAdjacentBombs);
        }
    }
}
