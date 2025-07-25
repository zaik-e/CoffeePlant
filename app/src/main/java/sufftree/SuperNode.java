package sufftree;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import symbol.Symbol;

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
    public boolean hasChild(Symbol c) {
        return true;
    }

    @Override
    public Node getChild(Symbol c) {
        return child;
    }
}
