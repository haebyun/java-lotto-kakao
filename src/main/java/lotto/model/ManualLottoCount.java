package lotto.model;

import java.util.Objects;

public class ManualLottoCount {
    private static final String NEGATIVE_MANUAL_COUNT_ERROR_MESSAGE = "[ERROR] 수동 구매 개수는 0 이상이어야 합니다.";

    private final int count;

    private ManualLottoCount(int count) {
        this.count = count;
    }

    public static ManualLottoCount from(int count) {
        validate(count);
        return new ManualLottoCount(count);
    }

    private static void validate(int count) {
        if (count < 0) {
            throw new IllegalArgumentException(NEGATIVE_MANUAL_COUNT_ERROR_MESSAGE);
        }
    }

    public int count() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ManualLottoCount that = (ManualLottoCount) o;
        return count == that.count;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(count);
    }
}
