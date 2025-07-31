package main;
/*
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import sufftree.SuffixTree;
import symbol.ArraySS;
import symbol.Symbol;
import symbol.SymbolSeq;
import symbol.UnicodeSymbol;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    private static int count = 0;
    private static int minLength = 10;
    private static int maxLength = 10000;

    @ParameterizedTest(name = "Input: \"{0}\"")
    @MethodSource("generateRandomArraySS")
    void testRandomContains(SymbolSeq input) {
        SuffixTree st = new SuffixTree(input);
        for (int i = 0; i < input.length() - 1; i++) {
            assertTrue(st.containsSuffix(input.substring(i)));
        }
    }

    public static Stream<SymbolSeq> generateRandomArraySS() {
        UnicodeSymbol a = new UnicodeSymbol('a');
        UnicodeSymbol b = new UnicodeSymbol('b');
        UnicodeSymbol c = new UnicodeSymbol('c');
        ArrayList<Symbol> alph = new ArrayList<>();
        alph.add(a);
        alph.add(b);
        alph.add(c);
        UnicodeSymbol eos = new UnicodeSymbol(-1);
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        return IntStream.range(0, count)
                .mapToObj(i -> {
                    int length = rnd.nextInt(minLength, maxLength + 1);
                    ArrayList<Symbol> als = new ArrayList<>();
                    for (int j = 0; j < length - 1; j++) {
                        als.add(alph.get(rnd.nextInt(3)));
                    }
                    als.add(eos);
                    return new ArraySS(als);
                });
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
*/