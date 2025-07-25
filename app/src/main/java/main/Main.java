package main;

import sufftree.SuffixTree;
import symbol.ArraySS;
import symbol.SymbolSeq;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        String a = "bccacbc$";
        SymbolSeq ss = new ArraySS(a);
        SuffixTree st = new SuffixTree(ss);

        st.print();

        String s = "c$";
        SymbolSeq sufss = new ArraySS(s);
        boolean contains = st.containsSuffix(sufss);
        System.out.println(contains);

    }
}
