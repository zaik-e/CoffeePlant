package main;

import sufftree.GeneralizedSuffixTree;
import sufftree.SuffixTree;
import symbol.ArraySS;
import symbol.SymbolSeq;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        String a = "ab$";
        String b = "cab%";
        SymbolSeq ss = new ArraySS(a);
        SymbolSeq ss2 = new ArraySS(b);
        ArrayList<SymbolSeq> input = new ArrayList<>();
        input.add(ss);
        input.add(ss2);
        GeneralizedSuffixTree st = new GeneralizedSuffixTree(input);
        st.build();
        st.print();
        System.out.println("here");

        String s = "ca";
        SymbolSeq sufss = new ArraySS(s);
        boolean contains = st.containsSuffix(sufss, 1);
        System.out.println(contains);

    }
}
