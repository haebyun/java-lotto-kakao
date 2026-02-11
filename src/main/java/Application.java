import lotto.controller.LottoController;
import lotto.view.InputView;
import lotto.view.OutputView;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        createLottoController().run();
    }

    private static LottoController createLottoController() {
        Scanner scanner = new Scanner(System.in);
        InputView inputView = new InputView(scanner);
        OutputView outputView = new OutputView();
        return new LottoController(inputView, outputView);
    }
}
