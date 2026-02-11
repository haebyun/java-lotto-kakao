package lotto.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LottoNumberTest {
    @DisplayName("같은 번호를 요청하면 캐시된 동일 인스턴스를 반환한다.")
    @Test
    void cacheLottoNumberTest() {
        LottoNumber lottoNumber = LottoNumber.of(7);
        LottoNumber sameLottoNumber = LottoNumber.of(7);

        assertSame(lottoNumber, sameLottoNumber);
    }

    @DisplayName("로또번호는 1~45 사이의 숫자만을 입력받는다.")
    @Test
    void validLottoNumberTest() {
        assertDoesNotThrow(() -> LottoNumber.of(44));
    }

    @DisplayName("로또번호는 1~45 사이의 숫자만을 입력받는다.")
    @Test
    void invalidLottoNumberTest() {
        assertThrows(IllegalArgumentException.class, () -> LottoNumber.of(46));
    }
}
