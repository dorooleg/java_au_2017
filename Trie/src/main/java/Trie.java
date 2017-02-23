import com.sun.javaws.exceptions.InvalidArgumentException;

/**
 * Created by user on 23.02.2017.
 */
public class Trie implements ITrie {

    private int size;
    private Node root;
    private final int COUNT_LATIN_SYMBOLS = 26;
    private final int MAX_COUNT_SYMBOLS = 26 * 2;

    private class Node {
        boolean isTerminal;
        int countWithPrefix;
        Node[] nodes;
    }

    Trie() {
        size = 0;
        root = null;
    }

    private int getSymbolIndex(char symbol) {
        if ('a' <= symbol && symbol <= 'z') {
            return symbol - 'a';
        }

        if ('A' <= symbol && symbol <= 'Z') {
            return symbol - 'A' + COUNT_LATIN_SYMBOLS;
        }

        return -1;
    }

    private void insert(String element, int index, Node node) {
        if (index >= element.length()) {
            return;
        }

        if (node.nodes[getSymbolIndex(element.charAt(index))] == null) {
            node.nodes[getSymbolIndex(element.charAt(index))]  = new Node();
            node.nodes[getSymbolIndex(element.charAt(index))].nodes = new Node[MAX_COUNT_SYMBOLS];
            node.nodes[getSymbolIndex(element.charAt(index))].countWithPrefix = 0;
            node.nodes[getSymbolIndex(element.charAt(index))].isTerminal = false;
        }

        node.countWithPrefix++;

        if (index + 1 == element.length()) {
            node.nodes[getSymbolIndex(element.charAt(index))].isTerminal = true;
            node.nodes[getSymbolIndex(element.charAt(index))].countWithPrefix++;
            return;
        }

        insert(element, index + 1, node.nodes[getSymbolIndex(element.charAt(index))]);
    }

    public boolean add(String element) {
        if (contains(element)) {
            return false;
        }

        if (root == null && element.length() != 0) {
            root = new Node();
            root.nodes = new Node[MAX_COUNT_SYMBOLS];
            root.isTerminal = false;
            root.countWithPrefix = 0;
        }

        insert(element,0, root);
        size++;
        return true;
    }

    private boolean contains(String element, int index, Node node) {
        if (node == null) {
            return false;
        }

        if (index >= element.length()) {
            return false;
        }

        if (index + 1 == element.length() && node.nodes[getSymbolIndex(element.charAt(index))] != null && node.nodes[getSymbolIndex(element.charAt(index))].isTerminal) {
            return true;
        }

        return contains(element, index + 1, node.nodes[getSymbolIndex(element.charAt(index))]);
    }

    public boolean contains(String element) {
        return contains(element, 0, root);
    }

    private void remove(String element, int index, Node node) {
        if (index >= element.length()) {
            return;
        }

        if (index + 1 == element.length()) {
            node.nodes[getSymbolIndex(element.charAt(index))].isTerminal = false;
            node.nodes[getSymbolIndex(element.charAt(index))].countWithPrefix--;
            if (node.nodes[getSymbolIndex(element.charAt(index))].countWithPrefix == 0) {
                node.nodes[getSymbolIndex(element.charAt(index))] = null;
            }
            return;
        }

        remove(element, index + 1, node.nodes[getSymbolIndex(element.charAt(index))]);
        node.countWithPrefix--;
        if (node.nodes[getSymbolIndex(element.charAt(index))].countWithPrefix == 0) {
            node.nodes[getSymbolIndex(element.charAt(index))] = null;
        }
    }

    public boolean remove(String element) {
        if (!contains(element)) {
            return false;
        }

        remove(element, 0, root);
        if (root.countWithPrefix == 0) {
            root = null;
        }
        size--;
        return true;
    }

    public int size() {
        return size;
    }

    private int howManyStartsWithPrefix(String element, int index, Node node) {
        if (node == null) {
            return 0;
        }

        if (element.length() == 0) {
            return root != null ? root.countWithPrefix : 0;
        }

        if (index >= element.length()) {
            return 0;
        }

        if (index + 1 == element.length() && node.nodes[getSymbolIndex(element.charAt(index))] != null) {
            return  node.nodes[getSymbolIndex(element.charAt(index))].countWithPrefix;
        }

        return howManyStartsWithPrefix(element, index + 1, node.nodes[getSymbolIndex(element.charAt(index))]);
    }

    public int howManyStartsWithPrefix(String prefix) {
        return howManyStartsWithPrefix(prefix, 0, root);
    }
}
