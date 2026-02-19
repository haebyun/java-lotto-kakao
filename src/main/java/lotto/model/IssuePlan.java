package lotto.model;

public class IssuePlan {
    private final ManualLottoCount manualLottoCount;
    private final int autoLottoCount;

    private IssuePlan(ManualLottoCount manualLottoCount, int autoLottoCount) {
        this.manualLottoCount = manualLottoCount;
        this.autoLottoCount = autoLottoCount;
    }

    public static IssuePlan from(PurchaseAmount purchaseAmount, ManualLottoCount manualLottoCount) {
        purchaseAmount.validateManualLottoCount(manualLottoCount);
        int autoLottoCount = purchaseAmount.calculateAutoLottoCount(manualLottoCount);
        return new IssuePlan(manualLottoCount, autoLottoCount);
    }

    public int manualCount() {
        return manualLottoCount.count();
    }

    public int autoCount() {
        return autoLottoCount;
    }

    public int totalCount() {
        return manualCount() + autoCount();
    }
}
