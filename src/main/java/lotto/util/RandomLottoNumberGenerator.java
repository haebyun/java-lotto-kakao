package lotto.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public final class RandomLottoNumberGenerator implements LottoNumberGenerator {
    private static final int MIN_LOTTO_NUMBER = 1;
    private static final int MAX_LOTTO_NUMBER = 45;

    @Override
    public List<Integer> generate(int requiredNumberCount) {
        List<Integer> candidates = createCandidates();
        Collections.shuffle(candidates);
        return candidates.stream()
                .limit(requiredNumberCount)
                .sorted()
                .toList();
    }

    private List<Integer> createCandidates() {
        return new ArrayList<>(IntStream.rangeClosed(MIN_LOTTO_NUMBER, MAX_LOTTO_NUMBER)
                .boxed()
                .toList()
        );
    }
}
