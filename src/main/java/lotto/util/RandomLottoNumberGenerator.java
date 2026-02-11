package lotto.util;

import lotto.model.Lotto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public final class RandomLottoNumberGenerator implements LottoNumberGenerator {
    private static final int MIN_LOTTO_NUMBER = 1;
    private static final int MAX_LOTTO_NUMBER = 45;

    @Override
    public List<Integer> generate() {
        List<Integer> candidates = createCandidates();
        Collections.shuffle(candidates);
        return candidates.stream()
                .limit(Lotto.requiredNumberCount())
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
