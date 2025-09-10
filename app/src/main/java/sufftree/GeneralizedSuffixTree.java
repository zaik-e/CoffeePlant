package sufftree;

import java.net.Inet4Address;
import java.util.*;
import factor.Factor;

/*
 * special class for storing factors in tree with automatically normalization of factorcode
 * */
public class GeneralizedSuffixTree {
    protected Node root;
    private ArrayList<Factor> factors;
    private int strInSet;
    private Factor currentFactor;

    public Set<Integer> unusedIndexes = new HashSet<>();

    public GeneralizedSuffixTree(ArrayList<Factor> ss, boolean checkingNeded) {
        this.factors = new ArrayList<>();
        if (checkingNeded)
            build(ss);
        else
            buildWithoutChecking(ss);
    }

    public GeneralizedSuffixTree() {
        this.factors = new ArrayList<>();
        build(factors);
    }

    public boolean isEmpty() {
        return root.getChildren().isEmpty();
    }

    public int size() {
        return factors.size() - unusedIndexes.size();
    }

    public ArrayList<Factor> getFactors() {
        ArrayList<Factor> clearFactors = new ArrayList<>();
        for (int i = 0; i < factors.size(); i++) {
            if (unusedIndexes.contains(i))
                continue;
            clearFactors.add(factors.get(i));
        }
        return clearFactors;
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
            if (!(factors.get(next.usedStrIndex).charAt(sourceIndex) == infix.charAt(currIndex))) {
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
            if (!(factors.get(next.usedStrIndex).charAt(sourceIndex) == infix.charAt(currIndex))) {
                return false;
            }
            currIndex++;
            sourceIndex++;
        }

        return currIndex == infix.length();
    }

    public ArrayList<Factor> LCSSoddeven(int divider) {
        Stack<Node> stack = new Stack<>();
        stack.push(root);

        ArrayList<Factor> result = new ArrayList<>();
        while (!stack.isEmpty()) {
            Node currNode = stack.pop();
            boolean hasDeeper = false;
            for (Node child : currNode.getChildren().values()) {
                if (child.hasBoth(divider)) {
                    stack.push(child);
                    hasDeeper = true;
                }
            }
            if (!hasDeeper && !(currNode.equals(root)))
                result.add(factors.get(currNode.usedStrIndex).substring(currNode.end - currNode.depth + 1,
                        currNode.end + 1));
        }
        return result;
    }


    public void build(ArrayList<Factor> factors) {
        SuperNode superRoot = new SuperNode();
        this.root = new Node(superRoot, 0, -1, 0);
        root.suf = superRoot;
        superRoot.setChild(root);

        for (int countStr = 0; countStr < factors.size(); countStr++) {
            addFactor(factors.get(countStr));
        }
    }

    public void buildWithoutChecking(ArrayList<Factor> factors) {
        SuperNode superRoot = new SuperNode();
        this.root = new Node(superRoot, 0, -1, 0);
        root.suf = superRoot;
        superRoot.setChild(root);

        for (int countStr = 0; countStr < factors.size(); countStr++) {
            addFactorWithoutChecking(factors.get(countStr));
        }
    }

    public void addFactorWithoutChecking(Factor factor) {
        strInSet = factors.size();
        factors.add(factor);
        currentFactor = factor;

        root.addToAnnotation(strInSet);
        int n = currentFactor.length();
        Node head = root;

        for (int i = 0; i < n; i++) {
            head = addSuffix(head, i);
        }
        if (!head.equals(root))
            head.suf = root;
    }

    public void addFactor(Factor factor) {
        if (containsSubstr(factor))
            return;

        addFactorWithoutChecking(factor);
    }

    protected void addLeaf(Node parent, int strInSet, int start, int end, int depth) {
        if (start > end) {
            parent.addFinalOf(strInSet);
            parent.addToAnnotation(strInSet);
            return;
        }
        Node child = new Node(parent, strInSet, start, end, depth);
        parent.putChild(currentFactor.charAt(child.start), child);
        parent.addToAnnotation(strInSet);
        child.addFinalOf(strInSet);
    }

    /* Here child node must be child of parent node */
    protected Node divideEdge(Node parent, Node child,
                              int strInSet, int start, int end, int depth) {
        Node newInternal = new Node(parent, strInSet, start, end, depth);
        child.start = child.start + newInternal.length;
        newInternal.putChild(factors.get(child.usedStrIndex).charAt(child.start), child);
        newInternal.copyAnnotation(child);
        parent.putChild(factors.get(newInternal.usedStrIndex).charAt(newInternal.start), newInternal);
        child.parent = newInternal;
        child.updateLength();
        return newInternal;
    }

    protected Node addSuffix(Node head, int start) {
//        Node fastHead = fastScanWithReplacement(head, start);
        Node newHead = slowScan(fastScanWithReplacement(head, start), start);
        addLeaf(newHead, strInSet,
                start + newHead.depth, currentFactor.length() - 1,
                currentFactor.length() - start);
        return newHead;
    }

    private void replaceEdge(int newStrIndex, int newStart, Node node) {
        if (node.equals(root) || node.equals(root.parent))
            return;
        if (node.usedStrIndex == newStrIndex)
            return;

        for (int index : node.getFinals()) {
            if (factors.get(index).length() == node.depth)
                unusedIndexes.add(index);
        }
        node.start = newStart;
        node.end = node.start + node.length - 1;
        node.usedStrIndex = newStrIndex;
    }

    protected Node fastScanWithReplacement(Node head, int start) {
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
        replaceEdge(strInSet, start + curNode.depth - curNode.length, curNode);

        Node nextNode = curNode.getChild(factors.get(head.usedStrIndex).charAt(curPos));

        while (skipped >= nextNode.length) {
            skipped -= nextNode.length;
            curPos += nextNode.length;
            curNode = nextNode;
            curNode.addToAnnotation(strInSet);
            replaceEdge(strInSet, start + curNode.depth - curNode.length, curNode);
            if (skipped == 0)
                break;
            nextNode = curNode.getChild(factors.get(head.usedStrIndex).charAt(curPos));
        }

        if (skipped > 0) {
            Node next = curNode.getChild(factors.get(head.usedStrIndex).charAt(curPos));
            curNode = divideEdge(curNode, next, next.usedStrIndex,
                    next.start, next.start + skipped - 1, curNode.depth + skipped);
        }
        head.suf = curNode;

        return curNode;
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

        Node nextNode = curNode.getChild(factors.get(head.usedStrIndex).charAt(curPos));
        while (skipped >= nextNode.length) {
            skipped -= nextNode.length;
            curPos += nextNode.length;
            curNode = nextNode;
            curNode.addToAnnotation(strInSet);
            if (skipped == 0)
                break;
            nextNode = curNode.getChild(factors.get(head.usedStrIndex).charAt(curPos));
        }

        if (skipped > 0) {
            Node next = curNode.getChild(factors.get(head.usedStrIndex).charAt(curPos));
            curNode = divideEdge(curNode, next, next.usedStrIndex,
                    next.start, next.start + skipped - 1, curNode.depth + skipped);
        }
        head.suf = curNode;

        return curNode;
    }

    protected Node slowScan(Node node, int start) {
        Node curNode = node;
        curNode.addToAnnotation(strInSet);
        replaceEdge(strInSet, start + curNode.depth - curNode.length, curNode);
        int curPos = start + node.depth;

        if (curPos >= currentFactor.length())
            return curNode;

        while (curPos < currentFactor.length() && curNode.hasChild(currentFactor.charAt(curPos))) {
            Node child = curNode.getChild(currentFactor.charAt(curPos));
            int edgePos = 0;

            while (curPos < currentFactor.length() && edgePos < child.length &&
                    currentFactor.charAt(curPos) == factors.get(child.usedStrIndex).charAt(child.start + edgePos)) {
                curPos++;
                edgePos++;
            }

            if (edgePos == child.length) {
                curNode = child;
                curNode.addToAnnotation(strInSet);
                replaceEdge(strInSet, start + curNode.depth - curNode.length, curNode);
            } else {
                curNode = divideEdge(curNode, child, child.usedStrIndex, child.start, child.start + edgePos - 1,
                        curNode.depth + edgePos);
                break;
            }

        }

        return curNode;
    }

    public GeneralizedSuffixTree clearCopy() {
        ArrayList<Factor> clearFactors = new ArrayList<>();
        for (int i = 0; i < factors.size(); i++) {
            if (unusedIndexes.contains(i))
                continue;
            clearFactors.add(factors.get(i));
        }

        GeneralizedSuffixTree newTree = new GeneralizedSuffixTree(clearFactors, false);

        return newTree;
    }

    @Override
    public int hashCode() {
        Set<Factor> sf = new HashSet<>(getFactors());
        return sf.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof GeneralizedSuffixTree that))
            return false;
        Set<Factor> sfThis = new HashSet<>(this.getFactors());
        Set<Factor> sfThat = new HashSet<>(that.getFactors());
        return sfThat.equals(sfThis);
    }
}
