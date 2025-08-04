package symbol;

public interface SymbolSeq {

    Symbol charAt(int index);
    int length();
    boolean isEmpty();
    SymbolSeq substring(int start);
    SymbolSeq substring(int start, int end);
    void addSymbol(Symbol symbol);
    SymbolSeq concat(SymbolSeq second);
}
