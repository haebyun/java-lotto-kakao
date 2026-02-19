package lotto.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IssuedLottosTest {
    @DisplayName("수동/자동 발급 개수와 발급된 로또 목록을 반환한다.")
    @Test
    void countsAndValuesTest() {
        PurchaseAmount purchaseAmount = new PurchaseAmount(5000);
        IssuePlan issuePlan = IssuePlan.from(purchaseAmount, ManualLottoCount.from(2));
        Lotto firstManual = Lotto.from(List.of(1, 2, 3, 4, 5, 6));
        Lotto secondManual = Lotto.from(List.of(7, 8, 9, 10, 11, 12));
        Lotto firstAuto = Lotto.from(List.of(13, 14, 15, 16, 17, 18));
        Lotto secondAuto = Lotto.from(List.of(19, 20, 21, 22, 23, 24));
        Lotto thirdAuto = Lotto.from(List.of(25, 26, 27, 28, 29, 30));
        IssuedLottos issuedLottos = IssuedLottos.of(
                issuePlan,
                List.of(firstManual, secondManual, firstAuto, secondAuto, thirdAuto)
        );

        assertEquals(2, issuedLottos.manualCount());
        assertEquals(3, issuedLottos.autoCount());
        assertEquals(5, issuedLottos.values().size());
        assertSame(firstManual, issuedLottos.values().get(0));
        assertSame(secondManual, issuedLottos.values().get(1));
    }

    @DisplayName("발급 계획과 실제 발급된 로또 개수가 다르면 생성에 실패한다.")
    @Test
    void invalidIssuedLottoCountTest() {
        PurchaseAmount purchaseAmount = new PurchaseAmount(5000);
        IssuePlan issuePlan = IssuePlan.from(purchaseAmount, ManualLottoCount.from(2));

        assertThrows(IllegalArgumentException.class, () -> IssuedLottos.of(
                issuePlan,
                List.of(
                        Lotto.from(List.of(1, 2, 3, 4, 5, 6)),
                        Lotto.from(List.of(7, 8, 9, 10, 11, 12)),
                        Lotto.from(List.of(13, 14, 15, 16, 17, 18)),
                        Lotto.from(List.of(19, 20, 21, 22, 23, 24))
                )
        ));
    }
}
