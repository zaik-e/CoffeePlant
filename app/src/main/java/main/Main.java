package main;

import factor.Factor;
import sufftree.GeneralizedSuffixTree;
import symbol.ArraySS;
import symbol.SymbolSeq;
import symbol.UnicodeSymbol;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        Factor a = new Factor("sabbsa1");
        Factor b = new Factor("csabcabb2");
        Factor c = new Factor("daabcabbd3");

        ArrayList<Factor> input = new ArrayList<>();
        input.add(a);
        input.add(b);
        input.add(c);
        GeneralizedSuffixTree st = new GeneralizedSuffixTree(input);
//        st.build();
        st.print();

//        String s = "ab2";
//        SymbolSeq sufss = new ArraySS(s);
//        sufss.addSymbol(new UnicodeSymbol(-2));
//        boolean contains = st.containsSuffix(s, 1);
//        System.out.println(contains);

        Factor common = st.findLCSS();
        System.out.println(common);
        System.out.println("=====");


    }
}
