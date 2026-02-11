package lotto.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LottosTest {

    @DisplayName("당첨 로또를 기준으로 각 등수 통계를 계산한다.")
    @Test
    void calculateStatisticsTest() {
        Lottos lottos = new Lottos(List.of(
                Lotto.from(List.of(1, 2, 3, 4, 5, 6)),
                Lotto.from(List.of(1, 2, 3, 4, 5, 7)),
                Lotto.from(List.of(1, 2, 3, 10, 11, 12))
        ));
        WinningLotto winningLotto = new WinningLotto(
                Lotto.from(List.of(1, 2, 3, 4, 5, 6)),
                LottoNumber.of(7)
        );
        PurchaseAmount purchaseAmount = new PurchaseAmount(3000);

        LottoStatistics lottoStatistics = lottos.calculateStatistics(winningLotto, purchaseAmount);

        assertEquals(1L, lottoStatistics.countOf(LottoResult.FIRST));
        assertEquals(1L, lottoStatistics.countOf(LottoResult.SECOND));
        assertEquals(1L, lottoStatistics.countOf(LottoResult.FIFTH));
        assertEquals(0L, lottoStatistics.countOf(LottoResult.MISS));
        int totalPrize = LottoResult.FIRST.getPrize() + LottoResult.SECOND.getPrize() + LottoResult.FIFTH.getPrize();
        assertEquals(totalPrize, lottoStatistics.calculateTotalPrize());
        double profitRate = (double) totalPrize / 3000;
        assertEquals(profitRate, lottoStatistics.profitRate());
    }
}
