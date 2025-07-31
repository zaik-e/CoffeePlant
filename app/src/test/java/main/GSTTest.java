package main;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import sufftree.GeneralizedSuffixTree;
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

class GSTTest {

    private static int count = 100;
    private static int minLength = 10;
    private static int maxLength = 1000;

    @ParameterizedTest(name = "Input: \"{0}\"")
    @MethodSource("generateRandomArraySS")
    void testRandomException(ArrayList<SymbolSeq> input) {
        GeneralizedSuffixTree st = new GeneralizedSuffixTree(input);
        assertDoesNotThrow(st::build);

    }

    public static Stream<ArrayList<SymbolSeq>> generateRandomArraySS() {
        UnicodeSymbol a = new UnicodeSymbol('a');
        UnicodeSymbol b = new UnicodeSymbol('b');
        UnicodeSymbol c = new UnicodeSymbol('c');
        ArrayList<Symbol> alph = new ArrayList<>();
        alph.add(a);
        alph.add(b);
        alph.add(c);
        UnicodeSymbol eos1 = new UnicodeSymbol(-1);
        UnicodeSymbol eos2 = new UnicodeSymbol(-2);
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        return IntStream.range(0, count)
                .mapToObj(i -> {
                    int length1 = rnd.nextInt(minLength, maxLength + 1);
                    ArrayList<Symbol> als1 = new ArrayList<>();
                    for (int j = 0; j < length1 - 1; j++) {
                        als1.add(alph.get(rnd.nextInt(3)));
                    }
                    als1.add(eos1);
                    int length2 = rnd.nextInt(minLength, maxLength + 1);
                    ArrayList<Symbol> als2 = new ArrayList<>();
                    for (int j = 0; j < length2 - 1; j++) {
                        als2.add(alph.get(rnd.nextInt(3)));
                    }
                    als2.add(eos2);
                    ArrayList<SymbolSeq> twoStr = new ArrayList<>();
                    twoStr.add(new ArraySS(als1));
                    twoStr.add(new ArraySS(als2));
                    return twoStr;
                });
    }

}
