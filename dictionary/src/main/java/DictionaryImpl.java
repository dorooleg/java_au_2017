/**
 * Created by user on 03.03.2017.
 */
public class DictionaryImpl implements Dictionary {
    
    private int size;
    private List hashTable[];

    public DictionaryImpl() {
        hashTable = new List[100];
        for (int i = 0; i < hashTable.length; i++) {
            hashTable[i] = new List();
        }
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains(String key) {
        if (key == null) {
            return false;
        }
        final int hashKey = Math.abs(key.hashCode()) % hashTable.length;
        return hashTable[hashKey].contains(key);
    }

    @Override
    public String get(String key) {
        if (key == null) {
            return null;
        }

        final int hashKey = Math.abs(key.hashCode()) % hashTable.length;
        return hashTable[hashKey].get(key);
    }

    @Override
    public String put(String key, String value) {
        if (key == null || value == null) {
            return null;
        }

        final int hashKey = Math.abs(key.hashCode()) % hashTable.length;
        String previousValue = hashTable[hashKey].insert(key, value);

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
        if (key == null) {
            return null;
        }

        final int hashKey = Math.abs(key.hashCode()) % hashTable.length;
        String previousValue = hashTable[hashKey].get(key);
        hashTable[hashKey].removeElement(key);

         if (previousValue != null) {
            size--;
        }
        return previousValue;
    }

    @Override
    public void clear() {
        for (List l : hashTable) {
            l.clear();
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

    private class List {

        private Node root;
        private int size;

        class Pair {

            public String first;
            public String second;

            Pair(String first, String second) {
                this.first = first;
                this.second = second;
            }
        }

        class Node {
            public Node(String key, String value) {
                this.key = key;
                this.value = value;
            }
            public String key;
            public String value;
            public Node next;
        }

        public void pushBack(String key, String value) {
            if (root == null) {
                size++;
                root = new Node(key, value);
                return;
            }

            Node node = root;
            while (node.next != null) {
                node = node.next;
            }

            node.next = new Node(key, value);
            size++;
        }

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

        public void removeElement(String key) {
            if (root == null) {
                return;
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
    }
}