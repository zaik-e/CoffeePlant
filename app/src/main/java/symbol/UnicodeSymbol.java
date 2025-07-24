package symbol;

public class UnicodeSymbol implements Symbol {
    int codePoint;

    public UnicodeSymbol(Character c) {
        codePoint = Character.codePointAt(new char[]{c.charValue()}, 0);
    }

    public UnicodeSymbol(char c) {
        codePoint = c;
    }

    public UnicodeSymbol(int codePoint) {
        this.codePoint = codePoint;
    }

    @Override
    public Character toCharacter() {
        return null;
    }

    @Override
    public int hashCode() {
        return codePoint;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UnicodeSymbol that)
            return this.codePoint == that.codePoint;
        return false;
    }
}
