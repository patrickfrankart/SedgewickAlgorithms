import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private static final int INIT_CAPACITY = 8;

    private Item[] q;
    private int n;


    public RandomizedQueue() {
        q = (Item[]) new Object[INIT_CAPACITY];
        n = 0;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        q[n] = item;

//        System.out.println("Item: " + item);
//        System.out.println("n and q[n]: " + n + q[n]);
        n++;

        if (n == q.length) {
            resize(2*q.length);
        }
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
//        System.out.println("In dequeue");
        int index = StdRandom.uniformInt(n);
//        System.out.println("index: " + index);
        Item item = q[index];
//        System.out.println("Item: " + item);
        q[index] = q[n-1];
//        System.out.println("q[index]" + q[index]);
        q[n-1] = null;


        n--;
//        System.out.println("n: " + " " + n);

        if (n == q.length/4) {
            resize(q.length/2);
        }

        return item;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return q[StdRandom.uniformInt(n)];
    }

    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    private class RandomIterator implements Iterator<Item> {
        int i = 0;
        Item[] qCopy = (Item[]) new Object[n];

        public RandomIterator() {
            StdRandom.shuffle(q, 0, n);
            for (int i = 0; i < n; i++) {
                qCopy[i] = q[i];
            }
        }

        public boolean hasNext() {
            return i < n;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = qCopy[i];
            i++;
            return item;
        }

        public void remove(Item item) {
            throw new UnsupportedOperationException();
        }
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            copy[i] = q[i];
        }

        this.q = copy;
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<Integer>();
        System.out.println("Is it empty? " + randomizedQueue.isEmpty());
        System.out.println("Size: " + randomizedQueue.size());
        for (int i = 0; i < 5; i++) {
            randomizedQueue.enqueue(i);
        }
        System.out.println("Is it empty? " + randomizedQueue.isEmpty());
        System.out.println("Size: " + randomizedQueue.size());
        for (Integer entry : randomizedQueue) {
            System.out.println(entry);
        }
        System.out.println("Sampling: " + randomizedQueue.sample() + " , " + randomizedQueue.sample());
        System.out.println("Now dequeue half of them.");
        for (int i = 0; i < 2; i++) {
            System.out.println(randomizedQueue.dequeue());
        }
        System.out.println("The array looks like this: ");
        System.out.println("Now run through the remaining with the iterator.");
        for (Integer entry : randomizedQueue) {
            System.out.println(entry);
        }
        iteratorTest();
    }
    private static void iteratorTest() {
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<Integer>();
        for (int i = 0 ; i < 10; i++) {
            randomizedQueue.enqueue(i);
        }
        Iterator<Integer> firstIterator = randomizedQueue.iterator();
        Iterator<Integer> secondIterator = randomizedQueue.iterator();
        for (int i = 0; i < 10; i++) {
            System.out.println("First: " + firstIterator.next() + " Second: " + secondIterator.next());
        }
    }
}
