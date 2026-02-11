package lotto.model;

import lotto.util.LottoNumberGenerator;
import lotto.util.LottoRules;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LottoMachineTest {

    @DisplayName("구매 금액에 맞는 개수만큼 로또를 발급한다.")
    @Test
    void issueLottosByPurchaseAmountTest() {
        LottoNumberGenerator lottoNumberGenerator = () -> List.of(1, 2, 3, 4, 5, 6);
        PurchaseAmount purchaseAmount = new PurchaseAmount(3000);

        LottoMachine lottoMachine = new LottoMachine(purchaseAmount, lottoNumberGenerator);

        assertEquals(3, lottoMachine.getLottos().values().size());
    }

    @DisplayName("구매한 로또들의 당첨 결과를 계산한다.")
    @Test
    void calculateLottoResultsTest() {
        List<List<Integer>> generatedNumbers = new ArrayList<>(List.of(
                List.of(1, 2, 3, 4, 5, 6),
                List.of(1, 2, 3, 4, 5, 7),
                List.of(1, 2, 3, 10, 11, 12)
        ));
        LottoNumberGenerator lottoNumberGenerator = generatedNumbers::removeFirst;
        PurchaseAmount purchaseAmount = new PurchaseAmount(3000);
        LottoMachine lottoMachine = new LottoMachine(purchaseAmount, lottoNumberGenerator);
        WinningLotto winningLotto = new WinningLotto(
                Lotto.from(List.of(1, 2, 3, 4, 5, 6)),
                LottoNumber.of(7)
        );

        LottoStatistics lottoStatistics = lottoMachine.calculateResult(winningLotto);

        assertEquals(1L, lottoStatistics.countOf(LottoResult.FIRST));
        assertEquals(1L, lottoStatistics.countOf(LottoResult.SECOND));
        assertEquals(1L, lottoStatistics.countOf(LottoResult.FIFTH));
        int totalPrize = LottoResult.FIRST.getPrize() + LottoResult.SECOND.getPrize() + LottoResult.FIFTH.getPrize();
        int purchaseAmountValue = purchaseAmount.getLottoCount() * LottoRules.PURCHASE_UNIT;
        double profitRate = (double) totalPrize / purchaseAmountValue;
        assertEquals(profitRate, lottoStatistics.profitRate());
    }
}
