package sufftree;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import symbol.Symbol;
import symbol.SymbolSeq;

import java.util.*;

public class Node {
    public @Nullable Node parent;
    public int start, end;
    public int length;
    public int depth;
    public @Nullable Node suf;
    private @NotNull HashMap<Symbol, Node> children;

    public Node(@Nullable Node parent, int start, int end, int depth) {
        this.parent = parent;
        this.start = start;
        this.end = end;
        this.depth = depth;
        this.children = new HashMap<>();
        this.length = end - start + 1;
    }

    public void updateLength() {
        length = end - start + 1;
    }

    public boolean hasChild(Symbol c) {
        return children.containsKey(c);
    }

    public Node getChild(Symbol c) {
        return children.get(c);
    }

    public void putChild(Symbol c, Node child) {
        children.put(c, child);
    }

    public boolean isLeaf() {
        return children.isEmpty();
    }

    public int countChildren() {
        return children.size();
    }

    public Set<Symbol> getTransitions() {
        return children.keySet();
    }

//    public boolean equals(Object other) {
//        if (!(other instanceof Node that))
//            return false;
//        return (this.start == that.start &&
//                this.end == that.end &&
//                this.depth == that.depth);
//    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" [");
        sb.append(start);
        sb.append(", ");
        sb.append(end);
        sb.append("] depth: ");
        sb.append(depth);
        return sb.toString();
    }

    public String childsToString() {
        StringBuilder sb = new StringBuilder();
        for (Node child : children.values()) {
            sb.append("    ");
            sb.append(child);
            sb.append("\n");
        }
        return  sb.toString();
    }
}
