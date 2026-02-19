package lotto.view;

import lotto.model.IssuedLottos;
import lotto.model.LottoResult;
import lotto.model.LottoStatistics;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class OutputView {
    private static final String PURCHASED_LOTTOS_MESSAGE = "수동으로 %d장, 자동으로 %d개를 구매했습니다.";
    private static final String STATISTICS_TITLE = "당첨 통계";
    private static final String STATISTICS_SEPARATOR = "---------";
    private static final String RESULT_LINE_FORMAT = "%d개 일치 (%d원)- %d개";
    private static final String SECOND_RESULT_LINE_FORMAT = "%d개 일치, 보너스 볼 일치(%d원) - %d개";
    private static final String PROFIT_RATE_MESSAGE_FORMAT = "총 수익률은 %.2f입니다.";

    public void printPurchasedLottos(IssuedLottos issuedLottos) {
        System.out.println();
        System.out.printf((PURCHASED_LOTTOS_MESSAGE) + "%n", issuedLottos.manualCount(), issuedLottos.autoCount());
        issuedLottos.values().forEach(System.out::println);
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
