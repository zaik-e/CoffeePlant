package main;

import factor.Factor;
import sufftree.GeneralizedSuffixTree;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        Factor a = new Factor("sabbsa1");
        Factor b = new Factor("csabbcabb2");
        Factor c = new Factor("daabcabbd3");

        ArrayList<Factor> input = new ArrayList<>();
        input.add(a);
        input.add(b);
//        input.add(c);
        GeneralizedSuffixTree st = new GeneralizedSuffixTree(input);
//        st.build();
        st.print();

        Factor common1 = st.findLCSS();
        System.out.println(common1);
        System.out.println("=====");

        st.addFactor(c);
        st.print();

//        String s = "ab2";
//        SymbolSeq sufss = new ArraySS(s);
//        sufss.addSymbol(new UnicodeSymbol(-2));
//        boolean contains = st.containsSuffix(s, 1);
//        System.out.println(contains);

        Factor common = st.findLCSS();
        System.out.println(common);
        System.out.println("=====");

        System.out.println(st.containsSubstr(new Factor("bs")));


    }
}
