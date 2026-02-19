package lotto.controller;

import lotto.model.Lotto;
import lotto.model.LottoMachine;
import lotto.model.IssuePlan;
import lotto.model.ManualLottoCount;
import lotto.model.PurchaseAmount;
import lotto.model.WinningLotto;
import lotto.view.InputView;
import lotto.view.OutputView;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class LottoController {
    private final InputView inputView;
    private final OutputView outputView;

    public LottoController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        PurchaseAmount purchaseAmount = readValidPurchaseAmount();
        IssuePlan issuePlan = readValidIssuePlan(purchaseAmount);
        List<Lotto> manualLottos = readValidManualLottos(issuePlan.manualCount());
        LottoMachine lottoMachine = LottoMachine.issue(purchaseAmount, issuePlan, manualLottos);
        outputView.printPurchasedLottos(lottoMachine.getIssuedLottos());
        WinningLotto winningLotto = readValidWinningLotto();
        outputView.printStatistics(lottoMachine.calculateResult(winningLotto));
    }

    private PurchaseAmount readValidPurchaseAmount() {
        return readUntilValid(inputView::readPurchaseAmount);
    }

    private IssuePlan readValidIssuePlan(PurchaseAmount purchaseAmount) {
        return readUntilValid(() -> {
            ManualLottoCount manualLottoCount = inputView.readManualLottoCount();
            return IssuePlan.from(purchaseAmount, manualLottoCount);
        });
    }

    private List<Lotto> readValidManualLottos(int manualLottoCount) {
        return readUntilValid(() -> inputView.readManualLotto(manualLottoCount));
    }

    private WinningLotto readValidWinningLotto() {
        Lotto winningNumbers = readValidWinningNumbers();
        return readUntilValid(() -> new WinningLotto(winningNumbers, inputView.readBonusNumber()));
    }

    private Lotto readValidWinningNumbers() {
        return readUntilValid(inputView::readWinningNumbers);
    }

    private <T> T readUntilValid(Supplier<T> reader) {
        Optional<T> value = tryRead(reader);
        while (value.isEmpty()) {
            value = tryRead(reader);
        }
        return value.orElseThrow();
    }

    private <T> Optional<T> tryRead(Supplier<T> reader) {
        try {
            return Optional.of(reader.get());
        } catch (IllegalArgumentException exception) {
            outputView.printError(exception.getMessage());
            return Optional.empty();
        }
    }
}
