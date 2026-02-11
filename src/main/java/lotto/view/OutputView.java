package lotto.view;

import lotto.model.Lotto;
import lotto.model.LottoResult;
import lotto.model.LottoStatistics;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class OutputView {
    private static final String PURCHASED_LOTTOS_MESSAGE = "개를 구매했습니다.";
    private static final String STATISTICS_TITLE = "당첨 통계";
    private static final String STATISTICS_SEPARATOR = "---------";
    private static final String RESULT_LINE_FORMAT = "%d개 일치 (%d원)- %d개";
    private static final String SECOND_RESULT_LINE_FORMAT = "%d개 일치, 보너스 볼 일치(%d원) - %d개";
    private static final String PROFIT_RATE_MESSAGE_FORMAT = "총 수익률은 %.2f입니다.";

    public void printPurchasedLottos(List<Lotto> lottos) {
        System.out.println(lottos.size() + PURCHASED_LOTTOS_MESSAGE);
        lottos.forEach(System.out::println);
    }

    public void printStatistics(LottoStatistics lottoStatistics) {
        System.out.println();
        System.out.println(STATISTICS_TITLE);
        System.out.println(STATISTICS_SEPARATOR);
        LottoResult.winningResultsByPrizeAscending().stream()
                .map(result -> formatResultLine(result, lottoStatistics.countOf(result)))
                .forEach(System.out::println);
        System.out.printf((PROFIT_RATE_MESSAGE_FORMAT) + "%n", toDisplayProfitRate(lottoStatistics.profitRate()));
    }

    private String formatResultLine(LottoResult lottoResult, long count) {
        if (lottoResult.includesBonusMatch()) {
            return SECOND_RESULT_LINE_FORMAT.formatted(lottoResult.getMatchCount(), lottoResult.getPrize(), count);
        }
        return RESULT_LINE_FORMAT.formatted(lottoResult.getMatchCount(), lottoResult.getPrize(), count);
    }

    private double toDisplayProfitRate(double profitRate) {
        return BigDecimal.valueOf(profitRate)
                .setScale(2, RoundingMode.DOWN)
                .doubleValue();
    }

    public void printError(String message) {
        System.out.println(message);
    }
}
