package lotto.controller;

import lotto.model.Lotto;
import lotto.model.LottoMachine;
import lotto.model.LottoNumber;
import lotto.model.PurchaseAmount;
import lotto.model.WinningLotto;
import lotto.view.InputView;
import lotto.view.OutputView;

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
        LottoMachine lottoMachine = new LottoMachine(purchaseAmount);
        outputView.printPurchasedLottos(lottoMachine.getLottos().values());
        WinningLotto winningLotto = readValidWinningLotto();
        outputView.printStatistics(lottoMachine.calculateResult(winningLotto));
    }

    private PurchaseAmount readValidPurchaseAmount() {
        return readUntilValid(inputView::readPurchaseAmount);
    }

    private WinningLotto readValidWinningLotto() {
        Lotto winningNumbers = readValidWinningNumbers();
        LottoNumber bonusNumber = readValidBonusNumber(winningNumbers);
        return new WinningLotto(winningNumbers, bonusNumber);
    }

    private Lotto readValidWinningNumbers() {
        return readUntilValid(inputView::readWinningNumbers);
    }

    private LottoNumber readValidBonusNumber(Lotto winningNumbers) {
        return readUntilValid(() -> validateBonusNumber(winningNumbers, inputView.readBonusNumber()));
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

    private LottoNumber validateBonusNumber(Lotto winningNumbers, LottoNumber bonusNumber) {
        new WinningLotto(winningNumbers, bonusNumber);
        return bonusNumber;
    }
}
