package sufftree;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SuperNode extends Node {
    Node child;

    public SuperNode() {
        super(null, 0, -1, 0);
        this.suf = this;
    }

    public void setChild(@NotNull Node child) {
        this.child = child;
    }

    @Override
    public boolean hasChild(Character c) {
        return true;
    }

    @Override
    public Node getChild(Character c) {
        return child;
    }
}
