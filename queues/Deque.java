import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    Item[] a = (Item[]) new Object[10];
    int N = 0;
    int start = 0;
    int end = 0;

    public Deque() {

    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public void addFirst(Item item) {
        if (N == a.length-1){
            resize(a.length*2);
        }
        a[start] = item;
        if (start == 0){
            start = a.length-1;
        }
        else {
            start--;
        }
        N++;
    }

    public void addLast(Item item) {
        if (N == a.length-1){
            resize(a.length*2);
        }
        a[end] = item;
        if (end == a.length-1){
            end = 0;
        }
        else {
            end++;
        }
        N++;
    }

    public Item removeFirst() {
        Item item = a[(start+1)%a.length];
        a[(start+1)%a.length] = null;
        N--;
        if (start == a.length-1){
            start = 0;
        }
        else {
            start++;
        }
        if (N > 0 && N == a.length/4) {
            resize(a.length/2);
        }

        return item;
    }

    public Item removeLast() {
        Item item;
        if (end == 0) {
            end = a.length-1;
            item = a[(end-1)];
            a[(end-1)] = null;
            N--;
        }
        else {
            item = a[(end-1)];
            a[(end-1)] = null;
            N--;
            end--;
        }

        if (N > 0 && N == a.length/4) {
            resize(a.length/2);
        }

        return item;
    }

    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private int i = start;
        private int count = 0;

        public boolean hasNext() {
            return count < N;
        }

        public Item next() {
            if (!hasNext()){
                throw new NoSuchElementException();
            }
            if (i == a.length-1){
                i = 0;
            }
            count++;
            return a[i++];
        }



    }

    private void resize(int capacity){
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = start, j = 0; j < N; ++i){
            if (i == copy.length-1){
                i = 0;
            }
            copy[j] = a[i];
            j++;
        }
        this.a = copy;
        start = 0;
        end = N;
        System.out.println("Resizing to " + a.length + " so start is now " + start + " and end is " + end);
    }

    public static void main(String[] args) {
        removeTest();

    }

    private static void addFirstTest() {
        Deque<Integer> deque = new Deque<>();

        deque.addFirst(1);
        System.out.println("Size: " + deque.size());
        System.out.println("Shouldn't be empty: " + deque.isEmpty());
        for(int i = 0; i < 100; ++i){
            deque.addFirst(i);
        }
        System.out.println("Size: " + deque.size());
    }

    private static void addLastTest() {
        Deque<Integer> deque = new Deque<>();

        deque.addLast(1);
        System.out.println("Size: " + deque.size());
        System.out.println("Shouldn't be empty: " + deque.isEmpty());
        for(int i = 0; i < 100; ++i){
            deque.addLast(i);
        }
        System.out.println("Size: " + deque.size());
    }

    private static void interleavedAddTest(){
        Deque<Integer> deque = new Deque<>();
        for(int i = 0; i < 100; ++i){
            if (i%2 == 0){
                deque.addFirst(i);
            }
            else{
                deque.addLast(i);
            }
        }
        System.out.println("Size: " + deque.size());
    }

    private static void removeTest() {
        Deque<Integer> deque = new Deque<>();

        for(int i = 0; i < 5; ++i){
            int start = deque.start;
            deque.addFirst(i);
            System.out.println("Adding " + i + " at index " + start);
        }

        System.out.println("Now start is: " + deque.start + " and end is: " + deque.end);
        for (int i = 0; i < 5; ++i) {
            int start = deque.start;
            System.out.println("Removing: " + deque.removeFirst() + " at index: " + start + " and N= " + deque.N);
        }

        for(int i = 0; i < 5; ++i){
            int start = deque.start;
            deque.addLast(i);
            System.out.println("Adding " + i + " at index " + start);
        }
        for (Integer entry : deque) {
            System.out.println("Iterating through " + entry);
        }
    }

}
