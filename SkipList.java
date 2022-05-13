import java.util.Random;
import java.util.Stack;

public class SkipList<K extends Comparable<K>, V> {
    SkipListNode<K, V> head;
    int highLevel;
    final int MAX_LEVEL = Integer.MAX_VALUE;
    Random random;

    public SkipList() {
        head = new SkipListNode<>(null, null);
        highLevel = 1;
        random = new Random();
    }

    public V search(K key) {
        SkipListNode<K, V> foundNode = searchSkipListNode(key);
        if (foundNode == null) {
            return null;
        }
        return foundNode.value;
    }

    public void insert(K key, V value) {
        if (searchSkipListNode(key) != null) {
            update(key, value);
            return;
        }

        Stack<SkipListNode<K, V>> predecessorsStack = new Stack<>();
        SkipListNode<K, V> pointer = head;
        while (pointer != null) {
            // same level forward
            if (pointer.right != null && pointer.right.key.compareTo(key) < 0) {
                pointer = pointer.right;
                continue;
            }

            // do down
            predecessorsStack.push(pointer);
            pointer = pointer.down;
        }

        SkipListNode<K, V> downNode = null;
        while (!predecessorsStack.isEmpty()) {
            // 1. setup current level
            // 1.1 horizontal
            SkipListNode<K, V> predecessor = predecessorsStack.pop();
            SkipListNode<K, V> newNode = new SkipListNode<>(key, value);
            newNode.right = predecessor.right;
            predecessor.right = newNode;
            // 1.2 vertical;
            newNode.down = downNode;
            downNode = newNode;

            // 2. decide if create node on upper level. 0: stop, 1: continue
            int chance = random.nextInt(2);
            if (chance == 0) {
                break;
            }

            // reach to high level, need to create an additional level
            if (predecessorsStack.isEmpty() && highLevel < MAX_LEVEL) {
                SkipListNode<K, V> newHead = new SkipListNode<>(null, null);
                SkipListNode<K, V> newNodeAtHighLevel = new SkipListNode<>(key, value);
                newHead.right = newNodeAtHighLevel;
                newHead.down = head;
                newNodeAtHighLevel.down = downNode;

                head = newHead;
                highLevel++;
            }
        }
    }

    public void update(K key, V value) {
        SkipListNode<K, V> pointer = head;
        while (pointer != null) {
            if (pointer.key != null && pointer.key.equals(key)) {
                pointer.value = value;
            }
            // same level forward
            if (pointer.right != null && pointer.right.key.compareTo(key) <= 0) {
                pointer = pointer.right;
                continue;
            }

            // do down
            pointer = pointer.down;
        }
    }

    public void delete(K key) {
        SkipListNode<K, V> pointer = head;
        while (pointer != null) {
            // same level forward
            if (pointer.right != null && pointer.right.key.compareTo(key) < 0) {
                pointer = pointer.right;
                continue;
            }

            if (pointer.right != null && pointer.right.key.equals(key)) {
                pointer.right = pointer.right.right;
            }
            pointer = pointer.down;
        }
    }

    private SkipListNode<K, V> searchSkipListNode(K key) {
        if (key == null) {
            return null;
        }
        SkipListNode<K, V> pointer = head;
        while (pointer != null) {
            // if (pointer.key == null) {
            // System.out.println("null");
            // } else {
            // System.out.println(pointer.key.toString());
            // }
            if (pointer.key != null && pointer.key.equals(key)) {
                return pointer;
            }
            // same level forward
            if (pointer.right != null && pointer.right.key.compareTo(key) <= 0) {
                pointer = pointer.right;
                continue;
            }

            // do down
            pointer = pointer.down;
        }
        return null;
    }

    public void printSkipList() {
        int level = highLevel;
        SkipListNode<K, V> rowPointer = head;
        while (rowPointer != null) {
            System.out.print("Level " + level + ": ");
            SkipListNode<K, V> colPointer = rowPointer;
            while (colPointer != null) {
                if (colPointer.key == null) {
                    System.out.print("head");
                } else {
                    System.out.print(" -> " + colPointer.key.toString() + ": " + colPointer.value.toString());
                }
                colPointer = colPointer.right;
            }

            System.out.println();
            rowPointer = rowPointer.down;
            level--;
        }
    }

    public static void main(String[] args) {
        SkipList<Integer, String> sl = new SkipList<Integer, String>();

        System.out.println("Inseart");
        sl.insert(1, "1");
        sl.insert(2, "2");
        sl.insert(3, "3");
        sl.insert(4, "4");
        sl.insert(5, "5");
        sl.insert(6, "6");
        sl.insert(7, "7");
        sl.insert(8, "9");
        sl.insert(9, "9");
        sl.printSkipList();
        System.out.println();

        System.out.println("Search key 4:");
        System.out.println(sl.search(4));
        System.out.println("Search key 5:");
        System.out.println(sl.search(5));
        System.out.println("Search key 100:");
        System.out.println(sl.search(100));
        System.out.println();

        System.out.println("Inseart <4, 7>");
        sl.insert(4, "7");
        sl.printSkipList();
        System.out.println();

        System.out.println("Update <4, 5>");
        sl.update(4, "5");
        sl.printSkipList();
        System.out.println();

        System.out.println("Delete 4 and 5");
        sl.delete(5);
        sl.delete(4);
        sl.printSkipList();
        System.out.println();
    }
}

class SkipListNode<K extends Comparable<K>, V> {
    K key;
    V value;
    SkipListNode<K, V> right;
    SkipListNode<K, V> down;

    public SkipListNode(K key, V value) {
        this.key = key;
        this.value = value;
        right = null;
        down = null;
    }
}