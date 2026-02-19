package lotto.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PurchaseAmountTest {
    @DisplayName("구매금액은 로또 가격을 나누어 떨어져야한다.")
    @Test
    void validLottoNumberTest() {
        assertDoesNotThrow(() -> new PurchaseAmount(4000));
    }

    @DisplayName("구매금액은 로또 가격을 나누어 떨어져야한다.")
    @Test
    void invalidLottoNumberTest() {
        assertThrows(IllegalArgumentException.class, () -> new PurchaseAmount(4001));
    }

    @DisplayName("구매금액은 1,000원 이상이어야 한다.")
    @Test
    void invalidMinimumPurchaseAmountTest() {
        assertThrows(IllegalArgumentException.class, () -> new PurchaseAmount(0));
        assertThrows(IllegalArgumentException.class, () -> new PurchaseAmount(-1000));
    }

    @DisplayName("총 당첨금 기준으로 수익률을 계산한다.")
    @Test
    void calculateProfitRateTest() {
        PurchaseAmount purchaseAmount = new PurchaseAmount(3000);
        long totalPrize = 6000L;

        assertEquals(2.0, purchaseAmount.calculateProfitRate(totalPrize));
    }

    @DisplayName("수동 구매 개수에 따라 자동 구매 개수를 계산한다.")
    @Test
    void calculateAutoLottoCountTest() {
        PurchaseAmount purchaseAmount = new PurchaseAmount(5000);
        ManualLottoCount manualLottoCount = ManualLottoCount.from(2);

        assertEquals(3, purchaseAmount.calculateAutoLottoCount(manualLottoCount));
    }

    @DisplayName("수동 구매 개수는 구매 가능한 개수 이하여야 한다.")
    @Test
    void invalidManualLottoCountTest() {
        PurchaseAmount purchaseAmount = new PurchaseAmount(5000);
        ManualLottoCount manualLottoCount = ManualLottoCount.from(6);

        assertThrows(IllegalArgumentException.class, () -> purchaseAmount.validateManualLottoCount(manualLottoCount));
        assertThrows(IllegalArgumentException.class, () -> purchaseAmount.calculateAutoLottoCount(manualLottoCount));
    }
}
