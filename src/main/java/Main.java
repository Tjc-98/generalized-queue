/**
 * The task is to create a generalized queue in which you are able to remove a specific element
 * in the desired place.
 */

import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        GeneralizedQueue<Integer> q = new GeneralizedQueue<>();
        // Test 1
        q.insert(1);        // [1]
        q.insert(2);        // [1],[2]
        q.insert(3);        // [1],[2],[3]
        q.insert(4);        // [1],[2],[3],[4]
        q.insert(5);        // [1],[2],[3],[4],[5]
        System.out.println("");  // Blank line.
        q.removeAt(3);        // [1],[2],[4],[5]
        q.removeAt(1);        // [1],[2],[4]
        q.removeAt(3);        // [2],[4]
        q.removeAt(2);        // [4]
        q.removeAt(1);        // []

        System.out.println("");  // Blank line.
        // Test 2
        q.insert(1);        // [1]
        q.insert(2);        // [1],[2]
        q.insert(3);        // [1],[2],[3]
        q.removeAt(10);       // Error message.
        q.removeAt(2);        // [1],[3]
        q.removeAt(2);        // [3]
        q.removeAt(1);        // []
        q.removeAt(1);        // Error message.
    }
}

/**
 * the list of data to store in FIFO(tail in tail out) fashion in other words double linked circular list.
 * @param <Item> the object type the list will consist of.
 */
class GeneralizedQueue<Item> implements Iterable<Item>{
    private Node<Item> tail; // Node to reference the first element in the list.
    private Node<Item> head; // Node to reference the last recent added element.
    private int size;

    /**
     * Constructor.
     */
    GeneralizedQueue() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     * Add the item to the queue list.
     * @param item the item that will be added to the list.
     */
    void insert(Item item) {
        if(isEmpty()) { // The list is empty [] so fill the tail node.
            this.tail = new Node<Item>(item);
            this.head = new Node<Item>(item);
            this.tail.setNextNode(this.head);
            this.tail.setPreviousNode(this.head);
            this.head.setNextNode(this.tail);
            this.head.setPreviousNode(this.tail);
        } else if(this.size == 1) { // The list consist of 1 element thus update the head node (back of the list).
            this.head = new Node<Item>(item);
            this.tail.setNextNode(this.head);
            this.tail.setPreviousNode(this.head);
            this.head.setNextNode(this.tail);
            this.head.setPreviousNode(this.tail);
        } else { // No special case presented os add the element in the end.
            Node<Item> oldHead = this.head;
            this.head = new Node<Item>(item);
            oldHead.setNextNode(this.head);
            this.head.setNextNode(this.tail);
            this.head.setPreviousNode(oldHead);
            this.tail.setPreviousNode(this.head);
        }
        this.size++;
        System.out.println(toString());
    }

    /**
     * It will remove an item form the queue in the specific place.
     * @param k the place where is the desired item.
     * @return the removed item.
     */
    Item removeAt(int k) {
        if(isEmpty()) { // It is not possible to remove and element if the list is already empty.
            this.size = -1;
            System.out.println(toString());
            return null;
        } else if(this.size == 1) { // One element is present in the list in which the head and tail nodes.
            Item temp = this.tail.getItem();
            this.tail = null;
            this.head = null;
            this.size--;
            System.out.println(toString());
            return temp;
        } else if(k == 1) { // Edge case so the head node needs to be updated.
            Item temp  = this.head.getItem();
            this.head = this.head.getPreviousNode();
            this.head.setNextNode(this.tail);
            this.tail.setPreviousNode(head);
            this.size--;
            System.out.println(toString());
            return temp;
        } else if(k == this.size) { // Edge case so the tail node needs to be updated.
            Item temp  = this.tail.getItem();
            this.tail = this.tail.getNextNode();
            this.tail.setPreviousNode(this.head);
            this.head.setNextNode(this.tail);
            this.size--;
            System.out.println(toString());
            return temp;
        } else if(k < this.size) { // No special case is present so remove the kth element in the list.
            Node<Item> temp = this.head;
            for(int i = 0; i < k - 1; i++) {
                temp = temp.getPreviousNode();
            }
            temp.getPreviousNode().setNextNode(temp.getNextNode());
            temp.getNextNode().setPreviousNode(temp.getPreviousNode());
            this.size--;
            System.out.println(toString());
            return temp.getItem();
        } else {
            System.out.println("The number is out of range.");
            return null;
        }
    }

    /**
     * Check whether the list is empty or not.
     * @return true or false depending on the condition.
     */
    boolean isEmpty() {
        return this.tail == null;
    }

    /**
     * Gives the size of the list.
     * @return the count of the items in the list.
     */
    int getSize() {
        return this.size;
    }

    /**
     * Represent the list in a specific style ([x],[x]...).
     * @return string containing the elements of the list.
     */
    @Override
    public String toString() {
        String output = "";
        Iterator<Item> iterator = iterator();
        for(int i = 0; i < size; i++) {
            output += ",[" + iterator.next() + "]";
        }
        if(size == 0) {
            output += "[]";
        } else if(size < 0) {
            output += "Unable to remove any element because the queue is already empty.";
        }
        return output.replaceFirst(",", "");
    }

    /**
     * Create an instance of the iterator for the queue.
     * @return the queue instance.
     */
    public Iterator<Item> iterator() {
        return new QueueIterator();
    }

    /**
     * The iterator implementation to loop through the elements of the list.
     * Inspired from the book "Algorithms 4th Edition by Robert Sedgewick, Kevin Wayne" p.155.
     */
    class QueueIterator implements Iterator<Item> {
        Node<Item> current = tail;

        /**
         * Check whether there is an elements existent after the current one.
         * @return true or false depending on the situation.
         */
        public boolean hasNext() {
            return this.current != null;
        }

        /**
         * Get the the current element and update it for the next one.
         * @return the current element.
         */
        public Item next() {
            Item item = current.getItem();
            current = current.getNextNode();
            return item;
        }
    }
}

/**
 * The node holding the element and reference the next element that comes after.
 * @param <Item> the type of element.
 */
class Node<Item> {
    private Item item; // Hold the value.
    private Node<Item> nextNode; // Reference for the node that is next in line.
    private Node<Item> previousNode; // Reference for the node that is previous in line.

    /**
     * Constructor.
     * @param item the element to store.
     */
    Node(Item item) {
        this.item = item;
        this.nextNode = null;
        this.previousNode = null;
    }

    /**
     * Update the next node that comes after.
     * @param newNode the next container.
     */
    void setNextNode(Node<Item> newNode) {
        this.nextNode = newNode;
    }

    /**
     * Update the previous node with what comes before.
     * @param newNode the previous node.
     */
    void setPreviousNode(Node<Item> newNode) {
        this.previousNode = newNode;
    }

    /**
     * Gives the next node.
     * @return the next node.
     */
    Node<Item> getNextNode() {
        return this.nextNode;
    }

    /**
     * Gives the previous node.
     * @return the previous node.
     */
    Node<Item> getPreviousNode() {
        return this.previousNode;
    }

    /**
     * Gives the item value.
     * @return the item value.
     */
    Item getItem() {
        return this.item;
    }

    /**
     * Check whether there is next node or not.
     * @return true or false depending on the condition.
     */
    boolean hasNext() {
        return this.nextNode == null;
    }

    /**
     * Checks whether there is a previous node or not.
     * @return true or false depending on the condition.
     */
    boolean hasPrevious() {
        return this.previousNode == null;
    }
}
