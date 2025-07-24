package sufftree;

import java.util.*;

public class SuffixTree {
    Node root;
    String s;

    public SuffixTree(String s) {
        this.s = s;
        build();
    }

    private boolean containsSuffAux(String suff, int index, Node node) {
        if (index == suff.length() && node.isLeaf()) {
            return true;
        }
        if (!node.hasChild(suff.charAt(index))) {
            return false;
        }
        Node next = node.getChild(suff.charAt(index));
        int currIndex = index;
        int sourceIndex = next.start;
        while (sourceIndex <= next.end && currIndex < suff.length()) {
            if (s.charAt(sourceIndex) != suff.charAt(currIndex)) {
                return false;
            }
            currIndex++;
            sourceIndex++;
        }
        return containsSuffAux(suff, currIndex, next);
    }

    /**
     * Check if given string with end marker is a suffix representing in
     * this suffix tree.
    * */
    public boolean containsSuffix(String suff) {
        if (suff.isEmpty()) {
            return true;
        }
        return containsSuffAux(suff, 0, root);
    }

    private void build() {
        SuperNode superRoot = new SuperNode();
        this.root = new Node(superRoot, 0, -1, 0);
        root.suf = superRoot;
        superRoot.setChild(root);

        Node head = root;
        int n = s.length();
        for (int i = 0; i < n; i++) {
            head = addSuffix(head, i);
        }

    }

    public Node addSuffix(Node head, int start) {
        Node newHead = slowScan(fastScan(head), start);
        Node newChild = new Node(newHead, start + newHead.depth, s.length()-1, s.length() - start);
        newHead.putChild(s.charAt(newChild.start), newChild);
        return newHead;
    }

    private Node fastScan(Node head) {
        if (head.equals(root)) {
            return head;
        }
        if (head.suf != null) {
            return head.suf;
        }
        int skipped = head.length;
        int curPos = head.start;

        if (head.parent.equals(root)) {
            skipped--;
            curPos++;
        }

        Node curNode = head.parent.suf;

        Node nextNode = curNode.getChild(s.charAt(curPos));
        while (skipped >= nextNode.length) {
            skipped -= nextNode.length;
            curPos += nextNode.length;
            curNode = nextNode;
            nextNode = curNode.getChild(s.charAt(curPos));
        }

        if (skipped > 0) {
            Node next = curNode.getChild(s.charAt(curPos));
            Node newInternal = new Node(curNode, next.start, next.start + skipped - 1, curNode.depth + skipped);
            newInternal.putChild(s.charAt(newInternal.end + 1), next);
            curNode.putChild(s.charAt(newInternal.start), newInternal);
            next.parent = newInternal;
            next.start = newInternal.end + 1;
            next.updateLength();

            curNode = newInternal;
        }
        head.suf = curNode;

        return curNode;
    }

    private Node slowScan(Node node, int start) {
        Node curNode = node;
        int curPos = start + node.depth;

        while (curNode.hasChild(s.charAt(curPos))) {
            Node child = curNode.getChild(s.charAt(curPos));
            int edgePos = 0;

            while (curPos < s.length() && edgePos < child.length &&
                    s.charAt(curPos) == s.charAt(child.start + edgePos)) {
                curPos++;
                edgePos++;
            }
            if (edgePos == child.length) {
                curNode = child;
            }
            else {
                Node newInternal = new Node(curNode, child.start, child.start + edgePos - 1,
                        curNode.depth + edgePos);
                newInternal.putChild(s.charAt(newInternal.end + 1), child);
                curNode.putChild(s.charAt(newInternal.start), newInternal);
                child.parent = newInternal;
                child.start = newInternal.end + 1;
                child.updateLength();
                curNode = newInternal;
                break;
            }
        }

        return curNode;
    }

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
        Collections.sort(keys);

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

        System.out.printf("%s%s%c [%d,%d] depth=%d len=%d%n",
                prefix, connector, edgeChar, node.start, node.end, node.depth, node.length);

        String childPrefix = prefix + (isLast ? "    " : "│   ");

        List<Character> keys = new ArrayList<>(node.getTransitions());
        Collections.sort(keys);
        for (int i = 0; i < keys.size(); i++) {
            char k = keys.get(i);
            printTreeWithEdge(node.getChild(k), childPrefix, i == keys.size() - 1, k, visited);
        }
    }

    /**
     * Pretty printer
     */
    public void print() {
        printTree(root, "", true, new HashSet<>());
    }

}
