package sufftree;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

public class AnnotatedNode extends Node {

    /* can be empty */
    private @NotNull Set<Integer> annotation;
    public final int usedStrIndex;

    public AnnotatedNode(@Nullable Node parent, int usedStrIndex, int start, int end, int depth) {
        super(parent, start, end, depth);
        this.usedStrIndex = usedStrIndex;
        annotation = new HashSet<>();
        annotation.add(usedStrIndex);
    }

    public AnnotatedNode(@Nullable Node parent, int start, int end, int depth) {
        super(parent, start, end, depth);
        this.usedStrIndex = -1;
        annotation = new HashSet<>();
    }

    public void copyAnnotation(AnnotatedNode node) {
        this.annotation = new HashSet<>(node.annotation);
        annotation.add(usedStrIndex);
    }

    public void addToAnnotation(int index) {
        annotation.add(index);
    }

    public boolean containsAnnotation(int index) {
        return annotation.contains(index);
    }
}
