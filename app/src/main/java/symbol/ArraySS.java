package symbol;

import java.util.ArrayList;

/*ArrayList Symbol Sequence*/
public class ArraySS implements SymbolSeq {

    ArrayList<Symbol> s;

    public ArraySS(String s) {
        this.s = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            this.s.add(new UnicodeSymbol(s.charAt(i)));
        }
    }

    public ArraySS(ArrayList<Symbol> s) {
        this.s = s;
    }

    @Override
    public Symbol charAt(int index) {
        return s.get(index);
    }

    @Override
    public int length() {
        return s.size();
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
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Symbol sym : s)
            sb.append(sym.toCharacter());
        return sb.toString();
    }
}
