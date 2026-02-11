package lotto.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class LottoResultTest {

    @DisplayName("맞춘 개수와 보너스 번호 일치 여부에 따라 올바른 등수를 반환한다.")
    @ParameterizedTest(name = "{index} {0}개 일치, 보너스 {1} -> {2}")
    @CsvSource({
            "6, false, FIRST",
            "5, true, SECOND",
            "5, false, THIRD",
            "4, false, FOURTH",
            "3, false, FIFTH",
            "2, false, MISS",
            "1, false, MISS",
            "0, false, MISS"
    })
    void findByCountAndBonus_Basic(int count, boolean hasBonus, LottoResult expected) {
        LottoResult result = LottoResult.findByCountAndBonus(count, hasBonus);

        assertThat(result).isEqualTo(expected);
    }
}