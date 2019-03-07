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

        assertThat(rows).containsExactly("  A B C D E", "0 ■ ■ ■ ■ ■", "1 ■ ■ ■ ■ ■", "2 ■ ■ ■ ■ ■", "3 ■ ■ ■ ■ ■", "4 ■ ■ ■ ■ ■");
    }

    @Test
    void reveal_IsDelegated() {
        Game game = Game.newDefault();

        game.reveal(Position.of("A0"));

        List<String> rows = new LinkedList<>();
        game.print(rows::add);
        assertThat(rows).element(1).isNotEqualTo("0 ■ ■ ■ ■ ■");
    }

    @Test
    void markAsBomb_IsDelegated() {
        Game game = Game.newDefault();

        game.markAsBomb(Position.of("A0"));

        List<String> rows = new LinkedList<>();
        game.print(rows::add);
        assertThat(rows).element(1).isEqualTo("0 ⚑ ■ ■ ■ ■");
    }
}