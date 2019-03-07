package pw.scho.minesweeper;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PositionStateTest {

    @Test
    void hidden_HasSquare() {
        PositionState state = PositionState.bomb("A0");

        assertThat(state.toString()).isEqualTo("■");
    }

    @Test
    void markAsBomb_SetsState() {
        PositionState state = PositionState.bomb("A0");

        state.markAsBomb();

        assertThat(state.toString()).isEqualTo("⚑");
        assertThat(state.isGameOver()).isFalse();
    }

    @Test
    void markAsBomb_OnMarkedPosition_ThrowsException() {
        PositionState state = PositionState.bomb("A0");
        state.markAsBomb();

        assertThatThrownBy(state::markAsBomb).isExactlyInstanceOf(IllegalStateException.class);
    }

    @Test
    void reveal_OnEmptyPosition_IsEmpty() {
        PositionState state = PositionState.empty("A0").withAllStates(Collections.emptyList());

        state.reveal();

        assertThat(state.toString()).isEqualTo(" ");
        assertThat(state.isGameOver()).isFalse();
    }

    @Test
    void reveal_OnBomb_IsX() {
        PositionState state = PositionState.bomb("A0").withAllStates(Collections.emptyList());

        state.reveal();

        assertThat(state.toString()).isEqualTo("x");
        assertThat(state.isGameOver()).isTrue();
    }

    @Test
    void reveal_OnEmptyPosition_ShowsNumberOfBombs() {
        List<PositionState> adjacentPositionStates = List.of(PositionState.empty("A1"), PositionState.bomb("B0"), PositionState.bomb("B1"));
        PositionState state = PositionState.empty("A0").withAllStates(adjacentPositionStates);

        state.reveal();

        assertThat(state.toString()).isEqualTo("2");
        assertThat(state.isGameOver()).isFalse();
    }
}
