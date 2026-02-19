package lotto.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ManualLottoCountTest {
    @DisplayName("유효한 수동 구매 개수로 ManualLottoCount를 생성한다.")
    @Test
    void createManualLottoCountTest() {
        ManualLottoCount manualLottoCount = ManualLottoCount.from(2);

        assertEquals(2, manualLottoCount.count());
    }

    @DisplayName("수동 구매 개수가 유효하지 않으면 생성에 실패한다.")
    @Test
    void invalidManualLottoCountTest() {
        assertThrows(IllegalArgumentException.class, () -> ManualLottoCount.from(-1));
    }
}
