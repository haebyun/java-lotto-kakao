package lotto.model;

import java.util.List;

public class Lottos {
    private final List<Lotto> lottos;

    public Lottos(List<Lotto> lottos) {
        this.lottos = List.copyOf(lottos);
    }

    public LottoStatistics calculateStatistics(WinningLotto winningLotto, PurchaseAmount purchaseAmount) {
        return LottoStatistics.from(calculateLottoResults(winningLotto), purchaseAmount);
    }

    private List<LottoResult> calculateLottoResults(WinningLotto winningLotto) {
        return lottos.stream()
                .map(winningLotto::calculateResult)
                .toList();
    }

    public List<Lotto> values() {
        return List.copyOf(lottos);
    }
}
