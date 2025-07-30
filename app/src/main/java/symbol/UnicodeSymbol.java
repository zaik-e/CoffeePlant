package symbol;

public class UnicodeSymbol implements Symbol {
    int codePoint;

    public static UnicodeSymbol createEndmarker(int count) {
        return new UnicodeSymbol(-1 * count);
    }

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
        return (char) codePoint;
    }

    @Override
    public boolean isEndmarker() {
        return (codePoint <= 0);
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
