package lotto.model;

import lotto.util.LottoNumberGenerator;
import lotto.util.RandomLottoNumberGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class LottoMachine {
    private final PurchaseAmount purchaseAmount;
    private final IssuedLottos issuedLottos;

    private LottoMachine(PurchaseAmount purchaseAmount, IssuedLottos issuedLottos) {
        this.purchaseAmount = purchaseAmount;
        this.issuedLottos = issuedLottos;
    }

    public static LottoMachine issue(PurchaseAmount purchaseAmount, IssuePlan issuePlan, List<Lotto> manualLottos) {
        return issue(purchaseAmount, issuePlan, manualLottos, new RandomLottoNumberGenerator());
    }

    public static LottoMachine issue(
            PurchaseAmount purchaseAmount,
            IssuePlan issuePlan,
            List<Lotto> manualLottos,
            LottoNumberGenerator generator
    ) {
        validateManualLottoCount(issuePlan, manualLottos);
        List<Lotto> issuedLottos = new ArrayList<>(manualLottos);
        issuedLottos.addAll(issueAutoLottos(issuePlan.autoCount(), generator));
        return new LottoMachine(
                purchaseAmount,
                IssuedLottos.of(issuePlan, issuedLottos)
        );
    }

    private static void validateManualLottoCount(IssuePlan issuePlan, List<Lotto> manualLottos) {
        if (issuePlan.manualCount() != manualLottos.size()) {
            throw new IllegalArgumentException("[ERROR] 수동 구매 개수와 입력된 수동 로또 개수가 일치하지 않습니다.");
        }
    }

    private static List<Lotto> issueAutoLottos(int autoLottoCount, LottoNumberGenerator generator) {
        return IntStream.range(0, autoLottoCount)
                .mapToObj(index -> Lotto.from(generator.generate(Lotto.requiredNumberCount())))
                .toList();
    }

    public IssuedLottos getIssuedLottos() {
        return issuedLottos;
    }

    public LottoStatistics calculateResult(WinningLotto winningLotto) {
        return issuedLottos.calculateStatistics(winningLotto, purchaseAmount);
    }
}
