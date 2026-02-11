package lotto.model;

import lotto.util.LottoNumberGenerator;
import lotto.util.RandomLottoNumberGenerator;

import java.util.List;
import java.util.stream.IntStream;

public class LottoMachine {
    private final PurchaseAmount purchaseAmount;
    private final Lottos lottos;

    private LottoMachine(PurchaseAmount purchaseAmount, Lottos lottos) {
        this.purchaseAmount = purchaseAmount;
        this.lottos = lottos;
    }

    public static LottoMachine issue(PurchaseAmount purchaseAmount) {
        return issue(purchaseAmount, new RandomLottoNumberGenerator());
    }

    public static LottoMachine issue(PurchaseAmount purchaseAmount, LottoNumberGenerator generator) {
        List<Lotto> issuedLottos = IntStream.range(0, purchaseAmount.getLottoCount())
                .mapToObj(index -> Lotto.from(generator.generate()))
                .toList();
        return new LottoMachine(purchaseAmount, new Lottos(issuedLottos));
    }

    public Lottos getLottos() {
        return lottos;
    }

    public LottoStatistics calculateResult(WinningLotto winningLotto) {
        return lottos.calculateStatistics(winningLotto, purchaseAmount);
    }
}
