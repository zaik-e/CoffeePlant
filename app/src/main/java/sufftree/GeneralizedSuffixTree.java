package sufftree;

import symbol.ArraySS;
import symbol.Symbol;
import symbol.SymbolSeq;

import java.util.*;

public class GeneralizedSuffixTree {
    Node root;
    List<SymbolSeq> ss;
    private int strInSet;
    private SymbolSeq s;

    public GeneralizedSuffixTree(List<SymbolSeq> ss) {
        this.ss = ss;
//        build();
    }

    protected GeneralizedSuffixTree() {
        this.ss = new ArrayList<>();
//        build();
    }


    private boolean containsSuffAux(SymbolSeq suff, int index, int ann, Node node) {
        if (index == suff.length() && node.isLeaf() && node.containsAnnotation(ann)) {
            return true;
        }
        if (!node.hasChild(suff.charAt(index))) {
            return false;
        }
        Node next = node.getChild(suff.charAt(index));
        int currIndex = index;
        int sourceIndex = next.start;
        while (sourceIndex <= next.end && currIndex < suff.length()) {
            if (!(ss.get(next.usedStrIndex).charAt(sourceIndex).equals(suff.charAt(currIndex)))) {
                return false;
            }
            currIndex++;
            sourceIndex++;
        }
        return containsSuffAux(suff, currIndex, ann, next);
    }

    /**
     * Check if given string with end marker is a suffix representing in
     * this suffix tree.
     * Do not use without endmarker in input string.
     * */

    public boolean containsSuffix(SymbolSeq suff, int ann) {
        if (suff.isEmpty()) {
            return true;
        }
        return containsSuffAux(suff, 0, ann, root);
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
            SymbolSeq str = ss.get(strInSet);
            root.addToAnnotation(countStr);
            int n = str.length();
            for (int i = 0; i < n; i++) {
                head = addSuffix(head, i);
//                System.out.println(head);
//                print();
            }
//            print();
//            System.out.println("----------------");

        }

    }

    protected void addLeaf(Node parent, int strInSet, int start, int end, int depth) {
        Node child = new Node(parent, strInSet, start, end, depth);
        parent.putChild(ss.get(strInSet).charAt(child.start), child);
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
//        System.out.println(parent.childsToString());
//        System.out.println(newInternal);
//        System.out.println(child);
        return newInternal;
    }

    protected Node addSuffix(Node head, int start) {
        Node newHead = slowScan(fastScan(head), start);
        addLeaf(newHead, strInSet,start + newHead.depth, ss.get(strInSet).length()-1, ss.get(strInSet).length() - start);
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
//        System.out.println("---" + node);
//        System.out.println("---" + s.charAt(curPos).toCharacter());
//        System.out.println("---" + start);
        while (curNode.hasChild(s.charAt(curPos))) {
//            System.out.println("curnode " + curNode);
            Node child = curNode.getChild(s.charAt(curPos));
            int edgePos = 0;

            while (curPos < s.length() && edgePos < child.length &&
                    s.charAt(curPos).equals(ss.get(child.usedStrIndex).charAt(child.start + edgePos))) {
                curPos++;
                edgePos++;
            }
            if (edgePos == child.length) {
                curNode = child;
            }
            else {
//                System.out.println(curNode);
//                System.out.println(child);
                curNode = divideEdge(curNode, child, child.usedStrIndex, child.start, child.start + edgePos - 1,
                        curNode.depth + edgePos);
//                System.out.println(curNode);
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

        List<Symbol> keys = new ArrayList<>(node.getTransitions());
//        Collections.sort(keys);

        for (Symbol ch : keys) {
            Node child = node.getChild(ch);
            boolean lastChild = (++idx == total);
            printTreeWithEdge(child, childPrefix, lastChild, ch.toCharacter(), visited);
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

        System.out.printf("%s%s%c [%d,%d] str=%d depth=%d len=%d%n",
                prefix, connector, edgeChar, node.start, node.end, node.usedStrIndex, node.depth, node.length);

        String childPrefix = prefix + (isLast ? "    " : "│   ");

        List<Symbol> keys = new ArrayList<>(node.getTransitions());
//        Collections.sort(keys);
        for (int i = 0; i < keys.size(); i++) {
            Symbol k = keys.get(i);
            printTreeWithEdge(node.getChild(k), childPrefix, i == keys.size() - 1, k.toCharacter(), visited);
        }
    }

    /**
     * Pretty printer
     */
    public void print() {
        printTree(root, "", true, new HashSet<>());
    }

}
