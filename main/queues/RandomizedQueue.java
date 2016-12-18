import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static edu.princeton.cs.algs4.Knuth.shuffle;
import static edu.princeton.cs.algs4.StdRandom.uniform;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int size = 0;
    private Object[] queue = new Object[4];

    // is the queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        if (size == queue.length) {
            Object[] oldQueue = queue;
            queue = new Object[2 * queue.length];
            System.arraycopy(oldQueue, 0, queue, 0, oldQueue.length);
        }

        queue[size++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int index = uniform(0, size);
        Item toBeReturned = (Item) queue[index];

        queue[index] = null;

        System.arraycopy(queue, index + 1, queue, index, size - 1 - index);

        size--;
        queue[size] = null;

        if (size < queue.length / 4 && queue.length > 4) {
            Object[] oldQueue = queue;
            queue = new Object[queue.length / 2];
            System.arraycopy(oldQueue, 0, queue, 0, size);
        }

        return toBeReturned;
    }

    // return (but do not remove) a random item
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return (Item) queue[uniform(0, size)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private Object[] shuffled;
        int index = 0;

        RandomizedQueueIterator() {
            this.shuffled = Arrays.copyOf(queue, size);
            shuffle(shuffled);
        }

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public Item next() {
            if (index >= size) {
                throw new NoSuchElementException();
            }
            return (Item) shuffled[index++];
        }
    }

    // unit testing
    public static void main(String[] args) {
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<>();

        assert randomizedQueue.size() == 0;

        randomizedQueue.enqueue(1);
        randomizedQueue.enqueue(2);
        randomizedQueue.enqueue(3);
        randomizedQueue.enqueue(0);
        randomizedQueue.enqueue(0);

        assert randomizedQueue.size() == 5;

        randomizedQueue.dequeue();
        assert randomizedQueue.size() == 4;
        //should not leave loitering objects
        assert randomizedQueue.queue[4] == null;

        randomizedQueue.dequeue();
        randomizedQueue.dequeue();
        randomizedQueue.dequeue();
        randomizedQueue.dequeue();
        assert randomizedQueue.isEmpty();
    }
}
