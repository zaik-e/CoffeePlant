package sufftree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class GSTPrinter {

     /*
     Methods for printing the suffix tree
     */
    private static void printTree(Node node,
                                  String prefix,
                                  boolean isLast,
                                  Set<Node> visited) {

        if (node == null) return;

        if (!visited.add(node)) {
            System.out.println(prefix + (isLast ? "└── " : "├── ") + "(cycle)");
            return;
        }

        String connector = isLast ? "└── " : "├── ";

        System.out.printf("%s%s[%d,%d] depth=%d len=%d%n",
                prefix, connector, node.start, node.end, node.depth, node.length);

        String childPrefix = prefix + (isLast ? "    " : "│   ");

        int total = node.countChildren();
        int idx = 0;

        List<Character> keys = new ArrayList<>(node.getTransitions());

        for (Character ch : keys) {
            Node child = node.getChild(ch);
            boolean lastChild = (++idx == total);
            printTreeWithEdge(child, childPrefix, lastChild, ch, visited);
        }
    }

    private static void printTreeWithEdge(Node node,
                                          String prefix,
                                          boolean isLast,
                                          char edgeChar,
                                          Set<Node> visited) {
        if (node == null) return;

        String connector = isLast ? "└── " : "├── ";

        if (!visited.add(node)) {
            System.out.println(prefix + connector + edgeChar + " -> (cycle)");
            return;
        }

        System.out.printf("%s%s%c [%d,%d] str=%d depth=%d len=%d" + " " + node.getAnnotation() +  " " + node.getFinals() + "\n",
                prefix, connector, edgeChar, node.start, node.end, node.usedStrIndex, node.depth, node.length);

        String childPrefix = prefix + (isLast ? "    " : "│   ");

        List<Character> keys = new ArrayList<>(node.getTransitions());
        for (int i = 0; i < keys.size(); i++) {
            Character k = keys.get(i);
            printTreeWithEdge(node.getChild(k), childPrefix, i == keys.size() - 1, k, visited);
        }
    }

    /**
     * Pretty printer
     */
    public static void print(GeneralizedSuffixTree tree) {
        printTree(tree.root, "", true, new HashSet<>());
    }

}
