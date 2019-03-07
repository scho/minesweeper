package pw.scho.minesweeper;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GameTest {

    @Test
    void newDefault_IsPrintedCorrectly() {
        Game game = Game.newDefault();
        List<String> rows = new LinkedList<>();

        game.print(rows::add);

        assertThat(rows).containsOnly("■ ■ ■ ■ ■");
    }

    @Test
    void reveal_IsDelegated() {
        Game game = Game.newDefault();

        game.reveal('A', 0);

        List<String> rows = new LinkedList<>();
        game.print(rows::add);
        assertThat(rows.get(0)).isNotEqualTo("■ ■ ■ ■ ■");
    }

    @Test
    void markAsBomb_IsDelegated() {
        Game game = Game.newDefault();

        game.markAsBomb('A', 0);

        List<String> rows = new LinkedList<>();
        game.print(rows::add);
        assertThat(rows.get(0)).isEqualTo("⚑ ■ ■ ■ ■");
    }
}