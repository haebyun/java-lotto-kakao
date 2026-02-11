package lotto.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class LottoTest {
    @DisplayName("로또는 6개의 서로다른 숫자를 가져야한다.")
    @Test
    void validLottoTest() {
        assertDoesNotThrow(() -> new Lotto(Stream.of(1, 2, 3, 4, 5, 6)
                        .map(LottoNumber::of)
                        .toList()
                )
        );
    }

    @DisplayName("로또는 6개의 서로다른 숫자를 가져야한다.")
    @Test
    void invalidLottoTest() {
        assertThrows(IllegalArgumentException.class, () -> new Lotto(Stream.of(1, 2, 3, 4, 5, 6, 7)
                        .map(LottoNumber::of)
                        .toList()
                )
        );

        assertThrows(IllegalArgumentException.class, () -> new Lotto(Stream.of(1, 2, 3, 4, 5, 5)
                        .map(LottoNumber::of)
                        .toList()
                )
        );
    }
}
