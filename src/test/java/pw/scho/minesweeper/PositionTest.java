package pw.scho.minesweeper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PositionTest {


    @Test
    void constructionOfString_SetsFieldsCorrectly() {
        Position position = Position.of("A0");

        assertThat(position.getColumn()).isEqualTo('A');
        assertThat(position.getRow()).isEqualTo(0);
    }

    @Test
    void constructionOfString_ValidatesLengthOfInput() {
        assertThatThrownBy(() -> Position.of("A")).isExactlyInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Position.of("A12")).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @CsvSource({
            "A0, true",
            "J9, true",
            "K9, false",
            "a0, false",
            "AA, false"
    })
    void isValid_ValidatesCorrectly(String position, boolean expectedValue) {
        assertThat(Position.of(position).isValid()).isEqualTo(expectedValue);
    }

    @ParameterizedTest
    @CsvSource({
            "A0, A1, true",
            "A0, B0, true",
            "A0, B1, true",
            "A0, A2, false",
            "A0, C0, false"
    })
    void isAdjacent(String position, String otherPosition, boolean expectedValue) {
        Position first = Position.of(position);
        Position second = Position.of(otherPosition);

        assertThat(first.isAdjacent(second)).isEqualTo(expectedValue);
    }

}