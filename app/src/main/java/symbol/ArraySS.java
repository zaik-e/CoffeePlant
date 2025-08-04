package symbol;

import java.util.ArrayList;

/*ArrayList Symbol Sequence*/
public class ArraySS implements SymbolSeq {

    private ArrayList<Symbol> s;
    private int length;

    public ArraySS(String s) {
        this.s = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            this.s.add(new UnicodeSymbol(s.charAt(i)));
        }
        this.length = s.length();
    }

    public ArraySS(ArrayList<Symbol> s) {
        this.s = s;
        this.length = s.size();
    }

    public ArraySS() {
        this.s = new ArrayList<>();
        this.length = 0;
    }

    @Override
    public void addSymbol(Symbol symbol) {
        s.add(symbol);
        length += 1;
    }

    @Override
    public Symbol charAt(int index) {
        return s.get(index);
    }

    @Override
    public SymbolSeq concat(SymbolSeq second) {
        int index = 0;
        SymbolSeq res = this.copy();
        while (index < second.length())
            res.addSymbol(second.charAt(index));
        return res;
    }

    @Override
    public int length() {
        return length;
    }

    @Override
    public boolean isEmpty() {
        return s.isEmpty();
    }

    @Override
    public SymbolSeq substring(int start) {
        ArrayList<Symbol> newList = new ArrayList<>();
        for (int i = start; i < s.size(); i++) {
            newList.add(s.get(i));
        }
        return new ArraySS(newList);
    }

    @Override
    public SymbolSeq substring(int start, int end) {
        ArrayList<Symbol> newList = new ArrayList<>();
        for (int i = start; i < end; i++) {
            newList.add(s.get(i));
        }
        return new ArraySS(newList);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Symbol sym : s)
            sb.append(sym.toCharacter());
        return sb.toString();
    }

    public ArraySS copy() {
        ArrayList<Symbol> arrCopy = new ArrayList<>(s);
        return new ArraySS(arrCopy);
    }
}
