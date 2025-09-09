package main;

import factor.Factor;
import factor.FactorCode;
import sufftree.GSTPrinter;
import sufftree.GeneralizedSuffixTree;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        Factor a = new Factor("hsabbsa");
        Factor b = new Factor("abbd");
        Factor c = new Factor("asabbdsa");

        ArrayList<Factor> input = new ArrayList<>();
        input.add(a);
        input.add(b);
        input.add(c);
        GeneralizedSuffixTree st = new GeneralizedSuffixTree(input);
//        st.build();
        System.out.println(st.unusedIndexes);
        GSTPrinter.print(st);
//        st.print();

        ArrayList<Factor> common1 = st.LCSSoddeven(1);
        System.out.println(common1);
        System.out.println("=====");
        FactorCode f = new FactorCode(common1);
        System.out.println(f.getFactors());

//        System.out.println(st.addFactor(c));
//        GSTPrinter.print(st);


//        String s = "ab2";
//        SymbolSeq sufss = new ArraySS(s);
//        sufss.addSymbol(new UnicodeSymbol(-2));
//        boolean contains = st.containsSuffix(s, 1);
//        System.out.println(contains);

//        Factor common = st.findLCSS();
//        System.out.println(common);
//        System.out.println("=====");

        System.out.println(st.containsSubstr(new Factor("bs")));


    }
}
