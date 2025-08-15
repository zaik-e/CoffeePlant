package sufftree;

import java.util.*;
import factor.Factor;

public class GeneralizedSuffixTree {
    Node root;
    List<Factor> ss;
    private int strInSet;
    private Factor s;

    public GeneralizedSuffixTree(List<Factor> ss) {
        this.ss = ss;
        build();
    }

    protected GeneralizedSuffixTree() {
        this.ss = new ArrayList<>();
        build();
    }


    private Node matchWord(Factor infix, int index, Node node) {
        if (index == infix.length()) {
            return node;
        }
        if (!node.hasChild(infix.charAt(index))) {
            return node;
        }
        Node next = node.getChild(infix.charAt(index));
        int currIndex = index;
        int sourceIndex = next.start;
        while (sourceIndex <= next.end && currIndex < infix.length()) {
            if (!(ss.get(next.usedStrIndex).charAt(sourceIndex) == infix.charAt(currIndex))) {
                return node;
            }
            currIndex++;
            sourceIndex++;
        }

        if (sourceIndex == next.end + 1)
            return matchWord(infix, currIndex, next);
        else
            return node;
    }

    public boolean containsSubstr(Factor infix) {
        if (infix.isEmpty()) {
            return true;
        }
        Node nearest = matchWord(infix, 0, root);
        if (nearest.depth == infix.length())
            return true;
        int index = nearest.depth;

        Node next = nearest.getChild(infix.charAt(index));
        if (next == null)
            return false;

        int currIndex = index;
        int sourceIndex = next.start;
        while (sourceIndex <= next.end && currIndex < infix.length()) {
            if (!(ss.get(next.usedStrIndex).charAt(sourceIndex) == infix.charAt(currIndex))) {
                return false;
            }
            currIndex++;
            sourceIndex++;
        }

        return currIndex == infix.length();
    }

    private Node auxFindLCSS() {
        int count = ss.size();
        Stack<Node> activeNodes = new Stack<>();
        activeNodes.add(root);
        Node maxNode = root;
        while (!(activeNodes.isEmpty())) {
            Node curNode = activeNodes.pop();
            if (curNode.depth > maxNode.depth)
                maxNode = curNode;
            for (Node e : curNode.getChildren().values()) {
                if (e.getAnnotation().size() == count) {
                    activeNodes.push(e);
                }
            }
        }
        return maxNode;
    }

    public Factor findLCSS() {
        Node found = auxFindLCSS();
        if (found.equals(root))
            return new Factor("");
        else if (found.parent.equals(root)) {
            return ss.get(found.usedStrIndex).substring(found.start, found.end + 1);
        }
        Factor substr = new Factor("");
        while (!(found.equals(root))) {
            System.out.println(found);
            substr = ss.get(found.usedStrIndex).substring(found.start, found.end + 1).concat(substr);
            found = found.parent;
        }
        return substr;
    }


    public void build() {
        SuperNode superRoot = new SuperNode();
        this.root = new Node(superRoot, 0, -1, 0);
        root.suf = superRoot;
        superRoot.setChild(root);

        Node head = root;
        for (int countStr = 0; countStr < ss.size(); countStr++) {
            strInSet = countStr;
            s = ss.get(strInSet);
            Factor str = ss.get(strInSet);
            root.addToAnnotation(countStr);
            int n = str.length();
            for (int i = 0; i < n; i++) {
                head = addSuffix(head, i);
            }
        }
    }

    public void addFactor(Factor newFactor) {
        ss.add(newFactor);
        strInSet = ss.size() - 1;
        s = ss.get(strInSet);
        root.addToAnnotation(strInSet);
        int n = newFactor.length();
        Node head = root;
        for (int i = 0; i < n; i++) {
            head = addSuffix(head, i);
        }

    }

    protected void addLeaf(Node parent, int strInSet, int start, int end, int depth) {
        if (Character.isDigit(ss.get(strInSet).charAt(start))) {
            parent.addFinalOf(strInSet);
        }
        Node child = new Node(parent, strInSet, start, end, depth);
        parent.putChild(ss.get(strInSet).charAt(child.start), child);
        parent.addToAnnotation(strInSet);
    }

    /* Here child node must be child of parent node */
    protected Node divideEdge(Node parent, Node child,
                              int strInSet, int start, int end, int depth) {
        Node newInternal = new Node(parent, strInSet, start, end, depth);
        child.start = child.start + newInternal.length;
        newInternal.putChild(ss.get(child.usedStrIndex).charAt(child.start), child);
        newInternal.copyAnnotation(child);
        parent.putChild(ss.get(newInternal.usedStrIndex).charAt(newInternal.start), newInternal);
        child.parent = newInternal;
        child.updateLength();
        return newInternal;
    }

    protected Node addSuffix(Node head, int start) {
        Node newHead = slowScan(fastScan(head), start);
        addLeaf(newHead, strInSet,
                start + newHead.depth, ss.get(strInSet).length() - 1,
                ss.get(strInSet).length() - start);
        return newHead;
    }

    protected Node fastScan(Node head) {
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

        Node nextNode = curNode.getChild(ss.get(head.usedStrIndex).charAt(curPos));
        while (skipped >= nextNode.length) {
            skipped -= nextNode.length;
            curPos += nextNode.length;
            curNode = nextNode;
            curNode.addToAnnotation(strInSet);
            nextNode = curNode.getChild(ss.get(head.usedStrIndex).charAt(curPos));
        }

        if (skipped > 0) {
            Node next = curNode.getChild(ss.get(head.usedStrIndex).charAt(curPos));
            curNode = divideEdge(curNode, next, next.usedStrIndex,
                    next.start, next.start + skipped - 1, curNode.depth + skipped);
        }
        head.suf = curNode;

        return curNode;
    }

    protected Node slowScan(Node node, int start) {
        Node curNode = node;
        curNode.addToAnnotation(strInSet);
        int curPos = start + node.depth;
        while (curNode.hasChild(s.charAt(curPos))) {
            Node child = curNode.getChild(s.charAt(curPos));
            int edgePos = 0;

            while (curPos < s.length() && edgePos < child.length &&
                    s.charAt(curPos) == ss.get(child.usedStrIndex).charAt(child.start + edgePos)) {
                curPos++;
                edgePos++;
            }
            if (edgePos == child.length) {
                curNode = child;
            }
            else {

                curNode = divideEdge(curNode, child, child.usedStrIndex, child.start, child.start + edgePos - 1,
                        curNode.depth + edgePos);
                break;
            }
            curNode.addToAnnotation(strInSet);
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
//        Collections.sort(keys);

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

        System.out.printf("%s%s%c [%d,%d] str=%d depth=%d len=%d" + " " + node.getAnnotation() + "\n",
                prefix, connector, edgeChar, node.start, node.end, node.usedStrIndex, node.depth, node.length);

        String childPrefix = prefix + (isLast ? "    " : "│   ");

        List<Character> keys = new ArrayList<>(node.getTransitions());
//        Collections.sort(keys);
        for (int i = 0; i < keys.size(); i++) {
            Character k = keys.get(i);
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
