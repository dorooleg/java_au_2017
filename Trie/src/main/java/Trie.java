import java.io.*;

/**
 * Created by user on 23.02.2017.
 */
public class Trie implements ITrie, StreamSerializable {

    private Node root;
    private final int COUNT_LATIN_SYMBOLS = 26;
    private final int MAX_COUNT_SYMBOLS = 26 * 2;

    Trie() {
        root = null;
    }

    @Override
    public void serialize(OutputStream out) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(out);
        serialize(root, dataOutputStream);
    }

    public void serialize(Node node, DataOutputStream out) throws IOException {
        if (node == null) {
            return;
        }

        for (char ch = 'a'; ch <= 'z'; ch++) {
            final int index = getSymbolIndex(ch);
            if (node.nodes[index] != null) {
                out.writeChar(ch);
                serialize(node.nodes[index], out);
            }
        }

        for (char ch = 'A'; ch <= 'Z'; ch++) {
            final int index = getSymbolIndex(ch);
            if (node.nodes[index] != null) {
                out.writeChar(ch);
                serialize(node.nodes[index], out);
            }
        }

        out.writeChar('#');
        out.writeBoolean(node.isTerminal);
        out.writeInt(node.countWithPrefix);
    }

    @Override
    public void deserialize(InputStream in) throws IOException {
        root = null;
        DataInputStream dataInputStream = new DataInputStream(in);
        deserialize(root, dataInputStream);
    }

    public void deserialize(Node node, DataInputStream in) throws IOException {
        try {
            char ch = in.readChar();

            if (root == null) {
                root = new Node();
                node = root;
            }

            while (ch != '#') {
                final int index = getSymbolIndex(ch);
                node.nodes[index] = new Node();
                deserialize(node.nodes[index], in);
                ch = in.readChar();
            }

            node.isTerminal = in.readBoolean();
            node.countWithPrefix = in.readInt();
        } catch (EOFException ex) {
            //skip exception
        } catch (IOException ex) {
            root = null;
            throw ex;
        }
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

        if (index == element.length()) {
            node.isTerminal = true;
            node.countWithPrefix++;
            return;
        }

        if (index >= element.length()) {
            return;
        }

        final int symbolIndex = getSymbolIndex(element.charAt(index));
        if (node.nodes[symbolIndex] == null) {
            node.nodes[symbolIndex]  = new Node();
        }

        node.countWithPrefix++;

        insert(element, index + 1, node.nodes[symbolIndex]);
    }

    public boolean add(String element) {

        if (element == null || element.isEmpty() || contains(element)) {
            return false;
        }

        if (root == null && !element.isEmpty()) {
            root = new Node();
        }

        insert(element,0, root);
        return true;
    }

    private boolean contains(String element, int index, Node node) {

        if (node == null) {
            return false;
        }

        if (index == element.length() && node.isTerminal) {
            return true;
        }

        if (index >= element.length()) {
            return false;
        }

        final int symbolIndex = getSymbolIndex(element.charAt(index));
        return contains(element, index + 1, node.nodes[symbolIndex]);
    }

    public boolean contains(String element) {
        return element != null && contains(element, 0, root);
    }

    private void remove(String element, int index, Node node) {
        if (index >= element.length()) {
            return;
        }

        final int symbolIndex = getSymbolIndex(element.charAt(index));
        if (index + 1 == element.length()) {
            node.nodes[symbolIndex].isTerminal = false;
            node.nodes[symbolIndex].countWithPrefix--;
            if (node.nodes[symbolIndex].countWithPrefix == 0) {
                node.nodes[symbolIndex] = null;
            }
            return;
        }

        remove(element, index + 1, node.nodes[symbolIndex]);
        node.countWithPrefix--;
        if (node.nodes[symbolIndex].countWithPrefix == 0) {
            node.nodes[symbolIndex] = null;
        }
    }

    public boolean remove(String element) {
        if (element == null) {
            return false;
        }

        if (!contains(element)) {
            return false;
        }

        remove(element, 0, root);
        if (root.countWithPrefix == 0) {
            root = null;
        }
        return true;
    }

    public int size() {
        return root == null ? 0 : root.countWithPrefix;
    }

    private int howManyStartsWithPrefix(String element, int index, Node node) {
        if (node == null) {
            return 0;
        }

        //noinspection Since15
        if (element.isEmpty()) {
            return root != null ? root.countWithPrefix : 0;
        }

        if (index >= element.length()) {
            return 0;
        }

        final int symbolIndex = getSymbolIndex(element.charAt(index));
        if (index + 1 == element.length() && node.nodes[symbolIndex] != null) {
            return  node.nodes[symbolIndex].countWithPrefix;
        }

        return howManyStartsWithPrefix(element, index + 1, node.nodes[symbolIndex]);
    }

    public int howManyStartsWithPrefix(String prefix) {
        if (prefix == null) {
            return 0;
        }
        return howManyStartsWithPrefix(prefix, 0, root);
    }

    private class Node {
        Node() {
            nodes = new Node[MAX_COUNT_SYMBOLS];
            countWithPrefix = 0;
            isTerminal = false;
        }
        boolean isTerminal;
        int countWithPrefix;
        Node[] nodes;
    }
}
