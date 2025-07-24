package main;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import sufftree.SuffixTree;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    private static int count = 100;
    private static int minLength = 10;
    private static int maxLength = 10000;

    @ParameterizedTest(name = "Input: \"{0}\"")
    @MethodSource("generateRandomStrings")
    void testRandomContains(String input) {
        SuffixTree st = new SuffixTree(input);
        for (int i = 0; i < input.length() - 1; i++) {
            assertTrue(st.containsSuffix(input.substring(i)));
        }
    }

    public static Stream<String> generateRandomStrings() {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        return IntStream.range(0, count)
                .mapToObj(i -> {
                    int length = rnd.nextInt(minLength, maxLength + 1);
                    StringBuilder sb = new StringBuilder(length);
                    for (int j = 0; j < length - 1; j++) {
                        sb.append("abc".charAt(rnd.nextInt(3)));
                    }
                    sb.append('$');
                    return sb.toString();
                });
    }

}
