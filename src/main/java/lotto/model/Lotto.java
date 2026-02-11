package lotto.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Lotto {
    private static final int LOTTO_SIZE = 6;
    private final List<LottoNumber> numbers;

    public Lotto(List<LottoNumber> numbers) {
        validate(numbers);
        this.numbers = numbers;
    }

    public static Lotto from(List<Integer> numbers) {
        return new Lotto(numbers.stream().map(LottoNumber::of).toList());
    }

    private void validate(List<LottoNumber> numbers) {
        if (numbers.size() != LOTTO_SIZE) {
            throw new IllegalArgumentException("[ERROR] 하나의 로또는 6개의 로또 번호를 가져야 합니다.");
        }

        Set<LottoNumber> uniqueNumbers = new HashSet<>(numbers);
        if (uniqueNumbers.size() != numbers.size()) {
            throw new IllegalArgumentException("[ERROR] 하나의 로또 안에서 중복된 숫자를 가질 수 없습니다.");
        }
    }

    public boolean contains(LottoNumber bonusNumber) {
        return numbers.contains(bonusNumber);
    }

    public int calculateMatchCount(Lotto otherLotto) {
        return (int) numbers.stream()
                .filter(otherLotto::contains)
                .count();
    }

    public static int requiredNumberCount() {
        return LOTTO_SIZE;
    }

    @Override
    public String toString() {
        return numbers.toString();
    }
}
