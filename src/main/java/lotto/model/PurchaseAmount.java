package lotto.model;

import lotto.util.LottoRules;

import java.util.Objects;

public class PurchaseAmount {
    private static final String MIN_PURCHASE_AMOUNT_ERROR_MESSAGE = "[ERROR] 구입금액은 1,000원 이상이어야 합니다.";
    private static final String PURCHASE_UNIT_ERROR_MESSAGE = "[ERROR] 구입금액은 1,000원 단위여야 합니다.";

    private final int amount;

    public PurchaseAmount(int amount) {
        validate(amount);
        this.amount = amount;
    }

    private void validate(int amount) {
        if (amount < LottoRules.PURCHASE_UNIT) {
            throw new IllegalArgumentException(MIN_PURCHASE_AMOUNT_ERROR_MESSAGE);
        }

        if (amount % LottoRules.PURCHASE_UNIT != 0) {
            throw new IllegalArgumentException(PURCHASE_UNIT_ERROR_MESSAGE);
        }
    }

    public int getLottoCount() {
        return amount / LottoRules.PURCHASE_UNIT;
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
