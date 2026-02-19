package lotto.model;

import java.util.List;

public class IssuedLottos {
    private static final String INVALID_ISSUED_LOTTO_COUNT_ERROR_MESSAGE = "[ERROR] 발급된 로또 개수와 발급 계획이 일치하지 않습니다.";

    private final IssuePlan issuePlan;
    private final Lottos lottos;

    private IssuedLottos(IssuePlan issuePlan, Lottos lottos) {
        this.issuePlan = issuePlan;
        this.lottos = lottos;
    }

    public static IssuedLottos of(IssuePlan issuePlan, List<Lotto> lottos) {
        validate(issuePlan, lottos);
        return new IssuedLottos(issuePlan, new Lottos(lottos));
    }

    private static void validate(IssuePlan issuePlan, List<Lotto> lottos) {
        if (issuePlan.totalCount() != lottos.size()) {
            throw new IllegalArgumentException(INVALID_ISSUED_LOTTO_COUNT_ERROR_MESSAGE);
        }
    }

    public int manualCount() {
        return issuePlan.manualCount();
    }

    public int autoCount() {
        return issuePlan.autoCount();
    }

    public List<Lotto> values() {
        return lottos.values();
    }

    public LottoStatistics calculateStatistics(WinningLotto winningLotto, PurchaseAmount purchaseAmount) {
        return lottos.calculateStatistics(winningLotto, purchaseAmount);
    }
}
