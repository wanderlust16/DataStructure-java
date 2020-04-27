package hw_2;

public class Node<T> {
    private T item;
    private Node<T> next;

    public Node(T obj) {
        this.item = obj;
        this.next = null;
    }
    
    public Node(T obj, Node<T> next) {
    	this.item = obj;
    	this.next = next;
    }
    
    public final T getItem() {
    	return item;
    }
    
    public final void setItem(T item) {
    	this.item = item;
    }
    
    public final void setNext(Node<T> next) {
    	this.next = next;
    }
    
    public Node<T> getNext() {
    	return this.next;
    }
    
    public final void insertNext(T obj) {
    	// 여기 구현     	
    	Node newNode = new Node(obj); 
    	newNode.setNext(this.getNext());
    	this.setNext(newNode);
    }
    
    public final void removeNext() {
    	// 여기 구현 
    	Node nodeToBeDeleted = this.getNext();
    	this.setNext(nodeToBeDeleted.getNext());
    }
}