package lotto.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IssuePlanTest {
    @DisplayName("구매 금액과 수동 구매 개수로 발급 계획을 만든다.")
    @Test
    void createIssuePlanTest() {
        PurchaseAmount purchaseAmount = new PurchaseAmount(5000);
        ManualLottoCount manualLottoCount = ManualLottoCount.from(2);

        IssuePlan issuePlan = IssuePlan.from(purchaseAmount, manualLottoCount);

        assertEquals(2, issuePlan.manualCount());
        assertEquals(3, issuePlan.autoCount());
        assertEquals(5, issuePlan.totalCount());
    }

    @DisplayName("수동 구매 개수가 구매 가능한 개수를 초과하면 발급 계획 생성에 실패한다.")
    @Test
    void invalidIssuePlanTest() {
        PurchaseAmount purchaseAmount = new PurchaseAmount(5000);
        ManualLottoCount manualLottoCount = ManualLottoCount.from(6);

        assertThrows(IllegalArgumentException.class, () -> IssuePlan.from(purchaseAmount, manualLottoCount));
    }
}
