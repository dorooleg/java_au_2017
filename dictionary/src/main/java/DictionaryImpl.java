/**
 * Created by user on 03.03.2017.
 */
public class DictionaryImpl implements Dictionary {

    private static final int INITIAL_HASH_SIZE = 100;
    private int size;
    private List hashTable[];

    public DictionaryImpl() {
        clear();
    }

    private List getList(String key)
    {
        if (key == null) {
            throw new IllegalArgumentException("Key is invalid argument");
        }

        final int hashKey = Math.abs(key.hashCode()) % hashTable.length;
        return hashTable[hashKey];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains(String key) {
        return getList(key).contains(key);
    }

    @Override
    public String get(String key) {
        return getList(key).get(key);
    }

    @Override
    public String put(String key, String value) {
        if (value == null) {
            throw new IllegalArgumentException("Value is invalid argument");
        }

        final String previousValue = getList(key).insert(key, value);

        if (previousValue == null) {
            size++;
        }

        if (size() > hashTable.length) {
            resize();
        }

        return previousValue;
    }

    @Override
    public String remove(String key) {
        final String previousValue = getList(key).removeElement(key);

         if (previousValue != null) {
            size--;
        }

        return previousValue;
    }

    @Override
    public void clear() {
        hashTable = new List[INITIAL_HASH_SIZE];
        for (int i = 0; i < hashTable.length; i++) {
            hashTable[i] = new List();
        }
        size = 0;
    }

    private void resize()
    {
        final int oldHashTableLength = hashTable.length;
        List oldHashTable[] = hashTable;

        hashTable = new List[2 * hashTable.length];

        for (int i = 0; i < hashTable.length; i++) {
            hashTable[i] = new List();
        }

        size = 0;

        for (int i = 0; i < oldHashTableLength; i++) {
            List.Pair[] elements = oldHashTable[i].convertToArray();
            for (List.Pair v : elements) {
                put(v.first, v.second);
            }

        }
    }

    private static class List {

        private Node root;
        private int size;

        public String insert(String key, String value) {
            Node node = root;
            Node previousNode = root;
            while (node != null && !node.key.equals(key)) {
                previousNode = node;
                node = node.next;
            }

            if (node == null) {
                if (root == null) {
                    size++;
                    root = new Node(key, value);
                    return null;
                }
                previousNode.next = new Node(key, value);
                size++;
                return null;
            } else {
                final String previousValue = node.value;
                node.value = value;
                return previousValue;
            }
        }

        public boolean contains(String key) {
            Node node = root;
            while (node != null && !node.key.equals(key)) {
                node = node.next;
            }

            return node != null;
        }

        public String get(String key) {
            Node node = root;
            while (node != null && !node.key.equals(key)) {
                node = node.next;
            }

            return node == null ? null : node.value;
        }

        public String removeElement(String key) {
            if (root == null) {
                return null;
            }

            Node previousNode = root;
            Node currentNode = root;

            while (currentNode != null && !currentNode.key.equals(key)) {
                previousNode = currentNode;
                currentNode = currentNode.next;
            }

            if (currentNode != null) {
                size--;
                if (previousNode == currentNode) root = currentNode.next;
                else previousNode.next = currentNode.next;
            }

            return currentNode.value;
        }

        public Pair[] convertToArray() {
            Pair[] elements = new Pair[size];
            Node node = root;
            int i = 0;
            while (node != null) {
                elements[i] = new Pair(node.key, node.value);
                i++;
                node = node.next;
            }
            return elements;
        }

        public void clear() {
            root = null;
        }

        static class Node {

            public final String key;
            public String value;
            public Node next;

            public Node(String key, String value) {
                this.key = key;
                this.value = value;
            }
        }

        static class Pair {

            public final String first;
            public final String second;

            Pair(String first, String second) {
                this.first = first;
                this.second = second;
            }
        }
    }
}