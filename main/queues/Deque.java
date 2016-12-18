import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node<Item> first = null;
    private Node<Item> last = null;

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        int size = 0;
        for (Item ignored : this) {
            size++;
        }
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        if (first == null) {
            Node<Item> node = new Node<>(item);
            first = node;
            last = node;
        } else {
            Node<Item> oldHead = first;
            first = new Node<>(item, oldHead, null);
            oldHead.prev = first;
        }
    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        if (first == null) {
            Node<Item> node = new Node<>(item);
            first = node;
            last = node;
        } else {
            Node<Item> oldLast = last;
            last = new Node<>(item, null, oldLast);
            oldLast.next = last;
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        } else {
            Node<Item> toBeRemoved = first;
            first = toBeRemoved.next;
            if (first == null) {
                last = null;
            } else {
                first.prev = null;
            }
            return toBeRemoved.item;
        }
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        } else {
            Node<Item> toBeRemoved = last;
            last = toBeRemoved.prev;
            if (last == null) {
                first = null;
            } else {
                last.next = null;
            }
            return toBeRemoved.item;
        }
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator(first);
    }

    private static class Node<T> {
        final T item;
        Node<T> next;
        Node<T> prev;

        Node(T item) {
            this.item = item;
        }

        Node(T item, Node<T> next, Node<T> prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    private class DequeIterator implements Iterator<Item> {
        private Node<Item> current;

        private DequeIterator(Node<Item> head) {
            this.current = head;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (current == null) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;

            return item;
        }
    }

    // unit testing
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();

        assert deque.size() == 0;

        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        deque.addLast(0);

        assert deque.size() == 4;

        Integer removed = deque.removeFirst();
        assert removed == 3;
        removed = deque.removeLast();
        assert removed == 0;
        assert deque.size() == 2;

        removed = deque.removeFirst();
        assert removed == 2;
        removed = deque.removeLast();
        assert removed == 1;
        assert deque.isEmpty();
    }
}
