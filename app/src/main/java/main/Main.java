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


        ArrayList<Factor> input = new ArrayList<>();
        input.add(a);
        input.add(b);

        GeneralizedSuffixTree st = new GeneralizedSuffixTree(input, true);
//        st.build();
        System.out.println(st.unusedIndexes);
        GSTPrinter.print(st);
//        st.print();

        ArrayList<Factor> common1 = st.LCSSoddeven(1);
        System.out.println(common1);
        System.out.println("=====");
        FactorCode f = new FactorCode(common1);
        System.out.println(f.getIdeals());
        FactorCode aaca = new FactorCode(new Factor("aaca"));
        FactorCode c = new FactorCode(new Factor("c"));
        System.out.println(aaca.join(c).getIdeals());



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
