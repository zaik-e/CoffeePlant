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

    /* can be empty */
    private @NotNull Set<Integer> annotation;
    private @NotNull Set<Integer> finalOf;
    public final int usedStrIndex;

    public Node(@Nullable Node parent, int start, int end, int depth) {
        this.parent = parent;
        this.start = start;
        this.end = end;
        this.depth = depth;
        this.children = new HashMap<>();
        this.length = end - start + 1;
        this.usedStrIndex = -1;
        annotation = new HashSet<>();
        finalOf = new HashSet<>();
    }

    public Node(@Nullable Node parent, int usedStrIndex, int start, int end, int depth) {
        this.parent = parent;
        this.start = start;
        this.end = end;
        this.depth = depth;
        this.children = new HashMap<>();
        this.length = end - start + 1;
        this.usedStrIndex = usedStrIndex;
        annotation = new HashSet<>();
        annotation.add(usedStrIndex);
        finalOf = new HashSet<>();
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

    public Map<Symbol,Node> getChildren() {
        return children;
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

    public void copyAnnotation(Node node) {
        this.annotation = new HashSet<>(node.annotation);
        annotation.add(usedStrIndex);
    }

    public void addToAnnotation(int index) {
        annotation.add(index);
    }

    public boolean containsAnnotation(int index) {
        return annotation.contains(index);
    }

    public Set<Integer> getAnnotation() {
        return  annotation;
    }

    public void addFinalOf(int index) {
        finalOf.add(index);
    }
     public Set<Integer> getFinals() {
        return finalOf;
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
