package lotto.model;

import lotto.util.LottoNumberGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LottoMachineTest {

    @DisplayName("구매 금액에 맞는 개수만큼 로또를 발급한다.")
    @Test
    void issueLottosByPurchaseAmountTest() {
        LottoNumberGenerator lottoNumberGenerator = requiredNumberCount -> List.of(1, 2, 3, 4, 5, 6);
        PurchaseAmount purchaseAmount = new PurchaseAmount(3000);
        IssuePlan issuePlan = IssuePlan.from(purchaseAmount, ManualLottoCount.from(0));

        LottoMachine lottoMachine = LottoMachine.issue(purchaseAmount, issuePlan, List.of(), lottoNumberGenerator);

        assertEquals(3, lottoMachine.getIssuedLottos().values().size());
    }

    @DisplayName("구매한 로또들의 당첨 결과를 계산한다.")
    @Test
    void calculateLottoResultsTest() {
        List<List<Integer>> generatedNumbers = new ArrayList<>(List.of(
                List.of(1, 2, 3, 4, 5, 6),
                List.of(1, 2, 3, 4, 5, 7),
                List.of(1, 2, 3, 10, 11, 12)
        ));
        LottoNumberGenerator lottoNumberGenerator = requiredNumberCount -> generatedNumbers.removeFirst();
        PurchaseAmount purchaseAmount = new PurchaseAmount(3000);
        IssuePlan issuePlan = IssuePlan.from(purchaseAmount, ManualLottoCount.from(0));
        LottoMachine lottoMachine = LottoMachine.issue(purchaseAmount, issuePlan, List.of(), lottoNumberGenerator);
        WinningLotto winningLotto = new WinningLotto(
                Lotto.from(List.of(1, 2, 3, 4, 5, 6)),
                LottoNumber.of(7)
        );

        LottoStatistics lottoStatistics = lottoMachine.calculateResult(winningLotto);

        assertEquals(1L, lottoStatistics.countOf(LottoResult.FIRST));
        assertEquals(1L, lottoStatistics.countOf(LottoResult.SECOND));
        assertEquals(1L, lottoStatistics.countOf(LottoResult.FIFTH));
        int totalPrize = LottoResult.FIRST.getPrize() + LottoResult.SECOND.getPrize() + LottoResult.FIFTH.getPrize();
        double profitRate = purchaseAmount.calculateProfitRate(totalPrize);
        assertEquals(profitRate, lottoStatistics.profitRate());
    }

    @DisplayName("수동 구매 로또와 자동 구매 로또를 함께 발급한다.")
    @Test
    void issueManualAndAutoLottosTest() {
        LottoNumberGenerator lottoNumberGenerator = requiredNumberCount -> List.of(1, 2, 3, 4, 5, 6);
        PurchaseAmount purchaseAmount = new PurchaseAmount(5000);
        List<Lotto> manualLottos = List.of(
                Lotto.from(List.of(7, 8, 9, 10, 11, 12)),
                Lotto.from(List.of(13, 14, 15, 16, 17, 18))
        );
        IssuePlan issuePlan = IssuePlan.from(purchaseAmount, ManualLottoCount.from(manualLottos.size()));

        LottoMachine lottoMachine = LottoMachine.issue(purchaseAmount, issuePlan, manualLottos, lottoNumberGenerator);
        IssuedLottos issuedLottos = lottoMachine.getIssuedLottos();

        assertEquals(2, issuedLottos.manualCount());
        assertEquals(3, issuedLottos.autoCount());
        assertEquals(5, issuedLottos.values().size());
        assertEquals(manualLottos.get(0).toString(), issuedLottos.values().get(0).toString());
        assertEquals(manualLottos.get(1).toString(), issuedLottos.values().get(1).toString());
    }
}
