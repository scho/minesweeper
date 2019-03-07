package pw.scho.minesweeper;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PositionStateTest {

    @Test
    void hidden_HasSquare() {
        PositionState state = new PositionState(Position.bomb("A0"));

        assertThat(state.toString()).isEqualTo("■");
    }

    @Test
    void markAsBomb_SetsState() {
        PositionState state = new PositionState(Position.bomb("A0"));

        state.markAsBomb();

        assertThat(state.toString()).isEqualTo("⚑");
        assertThat(state.isGameOver()).isFalse();
    }

    @Test
    void markAsBomb_OnMarkedPosition_ThrowsException() {
        PositionState state = new PositionState(Position.bomb("A0"));
        state.markAsBomb();

        assertThatThrownBy(state::markAsBomb).isExactlyInstanceOf(IllegalStateException.class);
    }

    @Test
    void reveal_OnEmptyPosition_IsEmpty() {
        PositionState state = new PositionState(Position.empty("A0").withPositions(Collections.emptyList()));

        state.reveal();

        assertThat(state.toString()).isEqualTo(" ");
        assertThat(state.isGameOver()).isFalse();
    }

    @Test
    void reveal_OnBomb_IsX() {
        PositionState state = new PositionState(Position.bomb("A0").withPositions(Collections.emptyList()));

        state.reveal();

        assertThat(state.toString()).isEqualTo("x");
        assertThat(state.isGameOver()).isTrue();
    }

    @Test
    void reveal_OnEmptyPosition_ShowsNumberOfBombs() {
        List<Position> adjacentPositions = List.of(Position.empty("A1"), Position.bomb("B0"), Position.bomb("B1"));
        PositionState state = new PositionState(Position.empty("A0").withPositions(adjacentPositions));

        state.reveal();

        assertThat(state.toString()).isEqualTo("2");
        assertThat(state.isGameOver()).isFalse();
    }
}
