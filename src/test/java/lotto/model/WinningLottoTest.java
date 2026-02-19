package lotto.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class WinningLottoTest {
    @DisplayName("보너스 번호는 로또 내부의 숫자와 중복되면 안된다.")
    @Test
    void validLottoNumberTest() {
        Lotto lotto = new Lotto(Stream.of(1, 2, 3, 4, 5, 6)
                .map(LottoNumber::of)
                .toList());
        LottoNumber bonusNumber = LottoNumber.of(7);

        assertDoesNotThrow(() -> new WinningLotto(lotto, bonusNumber));
    }

    @DisplayName("보너스 번호는 로또 내부의 숫자와 중복되면 안된다.")
    @Test
    void invalidLottoNumberTest() {
        Lotto lotto = new Lotto(Stream.of(1, 2, 3, 4, 5, 6)
                .map(LottoNumber::of)
                .toList());
        LottoNumber bonusNumber = LottoNumber.of(6);

        assertThrows(IllegalArgumentException.class, () -> new WinningLotto(lotto, bonusNumber));
    }

    @DisplayName("Lotto를 파라미터로 주었을 때, 그에 대응하는 LottoResult를 반환한다. (모든 등수)")
    @ParameterizedTest(name = "{index} => expected={0}, userNumbers={1}")
    @MethodSource("lottoResultCases")
    void calculateResult_allRanks(LottoResult expected, List<Integer> userNumbers) {
        Lotto winning = new Lotto(Stream.of(1, 2, 3, 4, 5, 6)
                .map(LottoNumber::of)
                .toList());
        LottoNumber bonus = LottoNumber.of(7);
        WinningLotto winningLotto = new WinningLotto(winning, bonus);

        Lotto userLotto = new Lotto(userNumbers.stream()
                .map(LottoNumber::of)
                .toList());

        LottoResult actual = winningLotto.calculateResult(userLotto);

        assertEquals(expected, actual);
    }

    static Stream<Arguments> lottoResultCases() {
        return Stream.of(
                // 1등: 6개 일치
                Arguments.of(LottoResult.FIRST, List.of(1, 2, 3, 4, 5, 6)),

                // 2등: 5개 일치 + 보너스
                Arguments.of(LottoResult.SECOND, List.of(1, 2, 3, 4, 5, 7)),

                // 3등: 5개 일치 (보너스 X)
                Arguments.of(LottoResult.THIRD, List.of(1, 2, 3, 4, 5, 8)),

                // 4등: 4개 일치
                Arguments.of(LottoResult.FOURTH, List.of(1, 2, 3, 4, 8, 9)),

                // 5등: 3개 일치
                Arguments.of(LottoResult.FIFTH, List.of(1, 2, 3, 8, 9, 10)),

                // 꽝: 0개 일치 (예시)
                Arguments.of(LottoResult.MISS, List.of(8, 9, 10, 11, 12, 13))
        );
    }
}
