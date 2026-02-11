package lotto.model;

import lotto.util.LottoRules;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class LottoStatistics {
    private final Map<LottoResult, Long> counts;
    private final PurchaseAmount purchaseAmount;

    private LottoStatistics(Map<LottoResult, Long> counts, PurchaseAmount purchaseAmount) {
        this.counts = counts;
        this.purchaseAmount = purchaseAmount;
    }

    public static LottoStatistics from(List<LottoResult> results, PurchaseAmount purchaseAmount) {
        Map<LottoResult, Long> counts = initializeCounts();
        results.forEach(result -> counts.put(result, counts.get(result) + 1L));
        return new LottoStatistics(counts, purchaseAmount);
    }

    private static Map<LottoResult, Long> initializeCounts() {
        Map<LottoResult, Long> counts = new EnumMap<>(LottoResult.class);
        for (LottoResult result : LottoResult.values()) {
            counts.put(result, 0L);
        }
        return counts;
    }

    public long countOf(LottoResult lottoResult) {
        return counts.get(lottoResult);
    }

    public long calculateTotalPrize() {
        return counts.entrySet().stream()
                .mapToLong(entry -> entry.getKey().calculatePrizeFor(entry.getValue()))
                .sum();
    }

    public double profitRate() {
        int purchaseAmountValue = purchaseAmount.getLottoCount() * LottoRules.PURCHASE_UNIT;
        return (double) calculateTotalPrize() / purchaseAmountValue;
    }
}
