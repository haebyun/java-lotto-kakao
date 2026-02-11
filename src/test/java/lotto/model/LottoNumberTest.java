package lotto.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.Test;

public class LottoNumberTest {
    @DisplayName("같은 번호를 요청하면 캐시된 동일 인스턴스를 반환한다.")
    @Test
    void cacheLottoNumberTest() {
        LottoNumber lottoNumber = LottoNumber.of(7);
        LottoNumber sameLottoNumber = LottoNumber.of(7);

        assertSame(lottoNumber, sameLottoNumber);
    }

    @DisplayName("로또번호는 1~45 사이의 숫자에선 정상 작동한다.")
    @ParameterizedTest(name = "유효 경계값 {0}")
    @ValueSource(ints = {1, 45})
    void validBoundaryLottoNumberTest(int number) {
        assertDoesNotThrow(() -> LottoNumber.of(number));
    }

    @DisplayName("로또번호는 1~45 사이의 숫자에선 예외가 발생한다.")
    @ParameterizedTest(name = "유효하지 않은 경계값 {0}")
    @ValueSource(ints = {0, 46})
    void invalidBoundaryLottoNumberTest(int number) {
        assertThrows(IllegalArgumentException.class, () -> LottoNumber.of(number));
    }
}
