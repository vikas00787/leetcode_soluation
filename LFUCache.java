import java.util.*;

class LFUCache {

    class Node {
        int key, value, freq;
        Node prev, next;

        Node(int k, int v) {
            key = k;
            value = v;
            freq = 1;
        }
    }

    class DLL {
        Node head, tail;
        int size;

        DLL() {
            head = new Node(0, 0);
            tail = new Node(0, 0);

            head.next = tail;
            tail.prev = head;
            size = 0;
        }

        void addFirst(Node node) {

            node.next = head.next;
            node.prev = head;

            head.next.prev = node;
            head.next = node;

            size++;
        }

        void remove(Node node) {

            node.prev.next = node.next;
            node.next.prev = node.prev;

            size--;
        }

        Node removeLast() {

            if (size == 0) return null;

            Node last = tail.prev;
            remove(last);

            return last;
        }
    }

    private int capacity;
    private int minFreq;

    private HashMap<Integer, Node> nodeMap;
    private HashMap<Integer, DLL> freqMap;

    public LFUCache(int capacity) {

        this.capacity = capacity;
        minFreq = 0;

        nodeMap = new HashMap<>();
        freqMap = new HashMap<>();
    }

    public int get(int key) {

        if (!nodeMap.containsKey(key)) {
            return -1;
        }

        Node node = nodeMap.get(key);
        updateFreq(node);

        return node.value;
    }

    public void put(int key, int value) {

        if (capacity == 0) return;

        if (nodeMap.containsKey(key)) {

            Node node = nodeMap.get(key);
            node.value = value;

            updateFreq(node);

        } else {

            if (nodeMap.size() == capacity) {

                DLL minList = freqMap.get(minFreq);
                Node removed = minList.removeLast();

                nodeMap.remove(removed.key);
            }

            Node newNode = new Node(key, value);

            nodeMap.put(key, newNode);

            freqMap
                .computeIfAbsent(1, k -> new DLL())
                .addFirst(newNode);

            minFreq = 1;
        }
    }

    private void updateFreq(Node node) {

        int freq = node.freq;

        DLL oldList = freqMap.get(freq);
        oldList.remove(node);

        if (freq == minFreq && oldList.size == 0) {
            minFreq++;
        }

        node.freq++;

        freqMap
            .computeIfAbsent(node.freq, k -> new DLL())
            .addFirst(node);
    }
}