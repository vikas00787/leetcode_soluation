import java.util.*;

class AllOne {

    // Doubly Linked List Node
    class Node {
        int count;
        Set<String> keys;
        Node prev, next;

        Node(int c) {
            count = c;
            keys = new HashSet<>();
        }
    }

    private Node head, tail;
    private Map<String, Node> map;

    public AllOne() {

        map = new HashMap<>();

        head = new Node(0);
        tail = new Node(0);

        head.next = tail;
        tail.prev = head;
    }

    // Increment
    public void inc(String key) {

        if (!map.containsKey(key)) {

            // New key → count = 1
            Node node;

            if (head.next.count == 1) {
                node = head.next;
            } else {
                node = new Node(1);
                insertAfter(head, node);
            }

            node.keys.add(key);
            map.put(key, node);

        } else {

            Node curr = map.get(key);
            Node next = curr.next;
            int newCount = curr.count + 1;

            if (next.count != newCount) {
                Node node = new Node(newCount);
                insertAfter(curr, node);
                next = node;
            }

            next.keys.add(key);
            map.put(key, next);

            curr.keys.remove(key);
            if (curr.keys.isEmpty()) remove(curr);
        }
    }

    // Decrement
    public void dec(String key) {

        Node curr = map.get(key);

        if (curr.count == 1) {

            // Remove key completely
            map.remove(key);

        } else {

            Node prev = curr.prev;
            int newCount = curr.count - 1;

            if (prev.count != newCount) {
                Node node = new Node(newCount);
                insertAfter(prev, node);
                prev = node;
            }

            prev.keys.add(key);
            map.put(key, prev);
        }

        curr.keys.remove(key);
        if (curr.keys.isEmpty()) remove(curr);
    }

    // Get Max
    public String getMaxKey() {

        if (tail.prev == head) return "";

        return tail.prev.keys.iterator().next();
    }

    // Get Min
    public String getMinKey() {

        if (head.next == tail) return "";

        return head.next.keys.iterator().next();
    }

    // Insert node after prev
    private void insertAfter(Node prev, Node node) {

        node.next = prev.next;
        node.prev = prev;

        prev.next.prev = node;
        prev.next = node;
    }

    // Remove node
    private void remove(Node node) {

        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
}