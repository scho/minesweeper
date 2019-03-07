package pw.scho.minesweeper;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StateTest {

    @Test
    void hidden_HasSquare() {
        State state = State.bomb("A0");

        assertThat(state.toString()).isEqualTo("■");
    }

    @Test
    void markAsBomb_SetsState() {
        State state = State.bomb("A0");

        state.markAsBomb();

        assertThat(state.toString()).isEqualTo("⚑");
        assertThat(state.isGameOver()).isFalse();
    }

    @Test
    void markAsBomb_OnMarkedPosition_ThrowsException() {
        State state = State.bomb("A0");
        state.markAsBomb();

        assertThatThrownBy(state::markAsBomb).isExactlyInstanceOf(IllegalStateException.class);
    }

    @Test
    void reveal_OnEmptyPosition_IsEmpty() {
        State state = State.empty("A0").withAllStates(Collections.emptyList());

        state.reveal();

        assertThat(state.toString()).isEqualTo(" ");
        assertThat(state.isGameOver()).isFalse();
    }

    @Test
    void reveal_OnBomb_IsX() {
        State state = State.bomb("A0").withAllStates(Collections.emptyList());

        state.reveal();

        assertThat(state.toString()).isEqualTo("x");
        assertThat(state.isGameOver()).isTrue();
    }

    @Test
    void reveal_OnEmptyPosition_ShowsNumberOfBombs() {
        List<State> adjacentStates = List.of(State.empty("A1"), State.bomb("B0"), State.bomb("B1"));
        State state = State.empty("A0").withAllStates(adjacentStates);

        state.reveal();

        assertThat(state.toString()).isEqualTo("2");
        assertThat(state.isGameOver()).isFalse();
    }
}
