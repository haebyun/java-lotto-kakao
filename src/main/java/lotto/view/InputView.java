package lotto.view;

import lotto.model.Lotto;
import lotto.model.LottoNumber;
import lotto.model.ManualLottoCount;
import lotto.model.PurchaseAmount;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class InputView {
    private static final String PURCHASE_AMOUNT_PROMPT = "구입금액을 입력해 주세요.";
    private static final String MANUAL_LOTTO_COUNT_PROMPT = "수동으로 구매할 로또 수를 입력해 주세요.";
    private static final String MANUAL_LOTTO_NUMBERS_PROMPT = "수동으로 구매할 번호를 입력해 주세요.";
    private static final String WINNING_NUMBERS_PROMPT = "지난 주 당첨 번호를 입력해 주세요.";
    private static final String BONUS_NUMBER_PROMPT = "보너스 볼을 입력해 주세요.";
    private static final String COMMA_DELIMITER = ",";
    private static final String NOT_NUMBER_ERROR_MESSAGE = "[ERROR] 숫자만 입력해 주세요.";

    private final Scanner scanner;

    public InputView(Scanner scanner) {
        this.scanner = scanner;
    }

    public PurchaseAmount readPurchaseAmount() {
        System.out.println(PURCHASE_AMOUNT_PROMPT);
        return new PurchaseAmount(parseNumber(scanner.nextLine()));
    }

    public ManualLottoCount readManualLottoCount() {
        System.out.println();
        System.out.println(MANUAL_LOTTO_COUNT_PROMPT);
        return ManualLottoCount.from(parseNumber(scanner.nextLine()));
    }

    public List<Lotto> readManualLotto(int manualLottoCount) {
        if (manualLottoCount == 0) {
            return List.of();
        }
        System.out.println();
        System.out.println(MANUAL_LOTTO_NUMBERS_PROMPT);
        List<Lotto> manualLottos = new ArrayList<>();
        for (int index = 0; index < manualLottoCount; index++) {
            manualLottos.add(Lotto.from(parseCommaSeparatedNumbers(scanner.nextLine())));
        }
        return manualLottos;
    }

    public Lotto readWinningNumbers() {
        System.out.println();
        System.out.println(WINNING_NUMBERS_PROMPT);
        return Lotto.from(parseCommaSeparatedNumbers(scanner.nextLine()));
    }

    public LottoNumber readBonusNumber() {
        System.out.println(BONUS_NUMBER_PROMPT);
        return LottoNumber.of(parseNumber(scanner.nextLine()));
    }

    private int parseNumber(String value) {
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(NOT_NUMBER_ERROR_MESSAGE);
        }
    }

    private List<Integer> parseCommaSeparatedNumbers(String value) {
        StringTokenizer tokenizer = new StringTokenizer(value, COMMA_DELIMITER);
        List<Integer> numbers = new ArrayList<>();
        while (tokenizer.hasMoreTokens()) {
            numbers.add(parseNumber(tokenizer.nextToken()));
        }
        return numbers;
    }
}
