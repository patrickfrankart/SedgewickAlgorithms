import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private class Node<Item> {
        Node<Item> next;
        Node<Item> previous;
        Item value;

        public Node(Item item){
            this.value = item;
            this.next = null;
            this.previous = null;
        }
    }

    private int n;
    private Node<Item> first;
    private Node<Item> last;

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void addFirst(Item item) {
//        System.out.println("Inside addFirst");
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node<Item> addition = new Node<>(item);
        addition.previous = this.first;
//        if (this.first != null){
//            System.out.println("Making previous " + this.first.value);
//        }

        if (n > 0) {
            this.first.next = addition;
        }

        this.first = addition;
//        System.out.println(this.first.previous);

        n++;
        if (n == 1){
            this.last = addition;
        }
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node<Item> addition = new Node<>(item);
        addition.next = this.last;
        if (n > 0) {
            this.last.previous = addition;
        }

        this.last = addition;

        n++;
        if (n == 1){
            this.first = addition;
        }
    }

    public Item removeFirst() {
//        System.out.println("Previous: " + this.first.previous);
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Node<Item> node = this.first;


        if (n > 1){
//            System.out.println("Inside if");
//            System.out.println(node.value);
            node.previous.next = null;
            this.first = node.previous;
        }
        else{
            this.first = null;
            this.last = null;
        }


        n--;
        node.previous = null;
        node.next = null;

        return node.value;
    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Node<Item> node = this.last;

        if (n > 1){
            node.next.previous = null;
            this.last = node.next;
        }
        else {
            this.first = null;
            this.last = null;
        }

        n--;
        node.previous = null;
        node.next = null;

        return node.value;
    }

    public Iterator<Item> iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements Iterator<Item> {
        private Node<Item> currentNode = first;

        public boolean hasNext() {
            return this.currentNode != null;
        }

        public Item next() {
            if (!hasNext()){
                throw new NoSuchElementException();
            }

            Node<Item> result = currentNode;
            currentNode = currentNode.previous;

            return result.value;
        }

        public void remove(Item item) {
            throw new UnsupportedOperationException();
        }

    }

    public static void main(String[] args) {
//        addFirstTest();
//        addLastTest();
//        interleavedAddTest();
//        removeFirstTest();
//        removeLastTest();
        iteratorTest();

    }

    private static void iteratorTest() {
        Deque<Integer> deque = new Deque<Integer>();
        deque.addFirst(1);
        deque.addFirst(2);
        Iterator<Integer> iterator = deque.iterator();
        System.out.println(deque.size());
        System.out.println(iterator.hasNext());
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    private static void addFirstTest() {
        Deque<Integer> deque = new Deque<>();

        deque.addFirst(0);
        System.out.println("Size: " + deque.size());
        System.out.println("Shouldn't be empty: " + deque.isEmpty());
        for(Integer entry : deque){
            System.out.print(" " + entry + " ");
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

    private static void removeFirstTest() {
        Deque<Integer> deque = new Deque<>();

        for(int i = 0; i < 5; ++i){
            deque.addFirst(i);
            System.out.println("Adding " + i + " at index " + deque.first + " N: " + deque.n);
        }

        for(Integer entry : deque){
            System.out.print(" " + entry + " ");
        }

        System.out.println("Now start is: " + deque.first + " and end is: " + deque.last);
        for (int i = 0; i < 5; ++i) {
            System.out.println("Removing: " + deque.removeFirst() + " and N= " + deque.n);
        }
    }

    private static void removeLastTest(){
        Deque<Integer> deque = new Deque<>();

        for(int i = 0; i < 10; ++i){

            deque.addLast(i);
            System.out.println("Adding " + i + "size: " + deque.size());
        }
        for (Integer entry : deque) {
            System.out.println("Iterating through " + entry);
        }
        for (int i = 0; i < 10; ++i) {

            System.out.println("Removing: " + deque.removeLast() + " at index: " + deque.last + " and N= " + deque.n);
        }
    }

}
