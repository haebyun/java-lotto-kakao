package lotto.model;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public enum LottoResult {
    FIRST(6, 2_000_000_000, false),
    SECOND(5, 30_000_000, true),
    THIRD(5, 1_500_000, false),
    FOURTH(4, 50_000, false),
    FIFTH(3, 5_000, false),
    MISS(0, 0, false);

    private final int matchCount;
    private final int prize;
    private final boolean includesBonusMatch;

    LottoResult(int matchCount, int prize, boolean includesBonusMatch) {
        this.matchCount = matchCount;
        this.prize = prize;
        this.includesBonusMatch = includesBonusMatch;
    }

    public static LottoResult findByCountAndBonus(int count, boolean hasBonus) {
        if (SECOND.matchCount == count && hasBonus) {
            return SECOND;
        }

        return Arrays.stream(values())
                .filter(result -> result.matchCount == count)
                .filter(result -> result != SECOND)
                .findFirst()
                .orElse(MISS);
    }

    public int getMatchCount() {
        return matchCount;
    }

    public int getPrize() {
        return prize;
    }

    public long calculatePrizeFor(long count) {
        return prize * count;
    }

    public boolean includesBonusMatch() {
        return includesBonusMatch;
    }

    public static List<LottoResult> winningResultsByPrizeAscending() {
        return Arrays.stream(values())
                .filter(result -> result != MISS)
                .sorted(Comparator.comparingInt(LottoResult::getPrize))
                .toList();
    }
}
