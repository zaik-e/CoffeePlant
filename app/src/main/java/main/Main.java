package main;

import sufftree.GeneralizedSuffixTree;
import sufftree.SuffixTree;
import symbol.ArraySS;
import symbol.SymbolSeq;
import symbol.UnicodeSymbol;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        String a = "sabsa";
        String b = "csabcab";
        String c = "dsabcabd";
        SymbolSeq ss = new ArraySS(a);
        SymbolSeq ss2 = new ArraySS(b);
        SymbolSeq ss3 = new ArraySS(c);
        ss.addSymbol(new UnicodeSymbol(-1));
        ss2.addSymbol(new UnicodeSymbol(-2));
        ss3.addSymbol(new UnicodeSymbol(-3));
        ArrayList<SymbolSeq> input = new ArrayList<>();
        input.add(ss);
        input.add(ss2);
        input.add(ss3);
        GeneralizedSuffixTree st = new GeneralizedSuffixTree(input);
//        st.build();
        st.print();

        String s = "ab";
        SymbolSeq sufss = new ArraySS(s);
        sufss.addSymbol(new UnicodeSymbol(-2));
        boolean contains = st.containsSuffix(sufss, 1);
        System.out.println(contains);

        SymbolSeq common = st.findLCSS();
        System.out.println(common);
        System.out.println("=====");


    }
}
