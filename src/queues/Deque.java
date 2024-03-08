package queues;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node begin;
    private Node end;
    private int size;

    private class Node {
        Item val;
        Node next;
        Node prev;

        Node(Item item) {
            val = item;
            prev = next = null;
        }
    }

    // construct an empty deque
    public Deque() {
        begin = end = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item is null");
        }

        Node curFirst = begin;
        begin = new Node(item);
        begin.next = curFirst;

        if (end == null) {
            end = begin;
        } else {
            curFirst.prev = begin;
        }

        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item is null");
        }

        Node curLast = end;
        end = new Node(item);
        end.prev = curLast;

        if (begin == null) {
            begin = end;
        } else {
            curLast.next = end;
        }

        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException("deque is empty");
        }

        Item curFirst = begin.val;

        if (begin.next == null) {
            begin = end = null;
        } else {
            begin = begin.next;
            begin.prev = null;
        }

        size--;

        return curFirst;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (size == 0) {
            throw new NoSuchElementException("deque is empty");
        }

        Item curLast = end.val;

        if (end.prev == null) {
            begin = end = null;
        } else {
            end = end.prev;
            end.next = null;
        }

        size--;

        return curLast;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeueIterator();
    }

    private class DequeueIterator implements Iterator<Item> {
        private Node cur = begin;

        @Override
        public boolean hasNext() {
            return cur != null;
        }

        @Override
        public Item next() {
            if (cur == null) {
                throw new NoSuchElementException("no more items");
            }
            Item val = cur.val;
            cur = cur.next;
            return val;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove is not a supported operation");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();

//        System.out.println("Object size is: " + ObjectSizeFetcher.getObjectSize(deque));

        deque.addFirst(3);
        deque.addLast(7);
        deque.addFirst(2);
        deque.addLast(9);

        // 2 3 7 9
        for (int i : deque) {
            System.out.print(i + " ");
        }
        System.out.println("\n" + deque.size + "\n" + deque.isEmpty());

        System.out.println(deque.removeFirst());
        System.out.println(deque.removeLast());

        // 3 7
        for (int i : deque) {
            System.out.print(i + " ");
        }

        System.out.println("\n" + deque.size + "\n" + deque.isEmpty());
    }
}
