package lotto.model;

import java.util.Objects;

public class PurchaseAmount {
    private static final int PURCHASE_UNIT = 1000;
    private static final String MIN_PURCHASE_AMOUNT_ERROR_MESSAGE = "[ERROR] 구입금액은 1,000원 이상이어야 합니다.";
    private static final String PURCHASE_UNIT_ERROR_MESSAGE = "[ERROR] 구입금액은 1,000원 단위여야 합니다.";
    private static final String MANUAL_LOTTO_COUNT_ERROR_MESSAGE = "[ERROR] 수동 구매 개수는 0 이상, 구매 가능한 로또 개수 이하여야 합니다.";

    private final int amount;

    public PurchaseAmount(int amount) {
        validate(amount);
        this.amount = amount;
    }

    private void validate(int amount) {
        if (amount < PURCHASE_UNIT) {
            throw new IllegalArgumentException(MIN_PURCHASE_AMOUNT_ERROR_MESSAGE);
        }

        if (amount % PURCHASE_UNIT != 0) {
            throw new IllegalArgumentException(PURCHASE_UNIT_ERROR_MESSAGE);
        }
    }

    public int getLottoCount() {
        return amount / PURCHASE_UNIT;
    }

    public void validateManualLottoCount(ManualLottoCount manualLottoCount) {
        validatePurchasableManualLottoCount(manualLottoCount);
    }

    public int calculateAutoLottoCount(ManualLottoCount manualLottoCount) {
        validatePurchasableManualLottoCount(manualLottoCount);
        return getLottoCount() - manualLottoCount.count();
    }

    private void validatePurchasableManualLottoCount(ManualLottoCount manualLottoCount) {
        if (manualLottoCount.count() > getLottoCount()) {
            throw new IllegalArgumentException(MANUAL_LOTTO_COUNT_ERROR_MESSAGE);
        }
    }

    public double calculateProfitRate(long totalPrize) {
        return (double) totalPrize / amount;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseAmount that = (PurchaseAmount) o;
        return amount == that.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(amount);
    }
}
