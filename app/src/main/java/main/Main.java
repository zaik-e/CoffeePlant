package main;

import sufftree.SuffixTree;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        String a = "bccacacbacb^cacbbbabacbc$";
        SuffixTree st = new SuffixTree(a);

        st.print();

        String s = "babacbc$";
        boolean contains = st.containsSuffix(s);
        System.out.println(contains);

    }
}
