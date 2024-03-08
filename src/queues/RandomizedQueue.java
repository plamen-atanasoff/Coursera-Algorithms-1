package queues;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] queue;
    private int size;
    private int capacity;

    // construct an empty randomized queue
    public RandomizedQueue() {
        size = capacity = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item is null");
        }

        if (size == capacity) {
            resize(capacity == 0 ? 1 : capacity * 2);
        }

        queue[size++] = item;
    }

    private void resize(int newCapacity) {
        Item[] newQueue = (Item[]) new Object[newCapacity];

        if (size > 0) {
            System.arraycopy(queue, 0, newQueue, 0, size);
        }

        queue = newQueue;
        capacity = newCapacity;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("randomized queue is empty");
        }

        int randomPos = getRandomPosition();
        Item res = queue[randomPos];

        queue[randomPos] = queue[--size];
        queue[size] = null;

        if (size == capacity / 4) {
            resize(capacity / 2);
        }

        return res;
    }

    private int getRandomPosition() {
        return StdRandom.uniformInt(size);
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("randomized queue is empty");
        }

        return queue[getRandomPosition()];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private Item[] iteratorQueue;
        private int iteratorSize;

        public RandomizedQueueIterator() {
            if (size > 0) {
                iteratorQueue = (Item[]) new Object[size];
                System.arraycopy(queue, 0, iteratorQueue, 0, size);
            }

            iteratorSize = size;
        }

        @Override
        public boolean hasNext() {
            return iteratorSize > 0;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Randomized queue iterator is empty");
            }

            int randomPos = StdRandom.uniformInt(iteratorSize);
            Item res = iteratorQueue[randomPos];

            iteratorQueue[randomPos] = iteratorQueue[--iteratorSize];
            iteratorQueue[iteratorSize] = null;

            return res;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove is not a supported operation");
        }
    }
    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<>();

//        System.out.println("Object size is: " + ObjectSizeFetcher.getObjectSize(randomizedQueue));

        randomizedQueue.enqueue(3);
        randomizedQueue.enqueue(7);
        randomizedQueue.enqueue(2);
        randomizedQueue.enqueue(9);

        System.out.println(randomizedQueue.sample() + " " + randomizedQueue.sample() + " " + randomizedQueue.sample());

        // 2 3 7 9 in random order
        for (int i : randomizedQueue) {
            System.out.print(i + " ");
        }
        System.out.println("\n" + randomizedQueue.size + "\n" + randomizedQueue.isEmpty());

        System.out.println(randomizedQueue.dequeue());
        System.out.println(randomizedQueue.dequeue());

        for (int i : randomizedQueue) {
            System.out.print(i + " ");
        }

        System.out.println("\n" + randomizedQueue.size + "\n" + randomizedQueue.isEmpty());
    }

}
