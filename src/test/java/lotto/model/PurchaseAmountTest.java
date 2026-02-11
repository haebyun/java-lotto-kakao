package lotto.model;

import lotto.util.LottoRules;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PurchaseAmountTest {
    @DisplayName("구매금액은 로또 가격을 나누어 떨어져야한다.")
    @Test
    void validLottoNumberTest() {
        assertDoesNotThrow(() -> new PurchaseAmount(LottoRules.PURCHASE_UNIT * 4));
    }

    @DisplayName("구매금액은 로또 가격을 나누어 떨어져야한다.")
    @Test
    void invalidLottoNumberTest() {
        assertThrows(IllegalArgumentException.class, () -> new PurchaseAmount(LottoRules.PURCHASE_UNIT * 4 + 1));
    }

    @DisplayName("구매금액은 1,000원 이상이어야 한다.")
    @Test
    void invalidMinimumPurchaseAmountTest() {
        assertThrows(IllegalArgumentException.class, () -> new PurchaseAmount(0));
        assertThrows(IllegalArgumentException.class, () -> new PurchaseAmount(-LottoRules.PURCHASE_UNIT));
    }

    @DisplayName("총 당첨금 기준으로 수익률을 계산한다.")
    @Test
    void calculateProfitRateTest() {
        PurchaseAmount purchaseAmount = new PurchaseAmount(3000);
        long totalPrize = 6000L;

        assertEquals(2.0, purchaseAmount.calculateProfitRate(totalPrize));
    }
}
