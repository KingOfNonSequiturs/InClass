import java.util.NoSuchElementException;

public class DoublyLinkedList {

	int size = 0;
	
	boolean empty = true;
	
	Node first;
	Node last;
	
	public int getSize() {
		return size;
	}
	
	public boolean isEmpty() {
		return empty;
	}
	
	public void add(int value) {
		add(value, size);
	}
	
	public void addBefore(int value) {
		add(value, 0);
	}
	
	public void add(int value, int position) {
		
		if (position < 0 || position > size) {
			throw new ArrayIndexOutOfBoundsException();
			
		} else if (empty) {
			empty = false;
			first = last = new Node(value);
			
		} else if (position == 0) {
			first.previous = new Node(value, null, first);
			first = first.previous;
			
		} else if (position == size) {
			last.next = new Node(value, last, null);
			last = last.next;
			
		} else if (position <= size/2) {
			Node before = first;
			for (int i = 1; i < position; i++) {
				before = before.next;
			}
			
			before.next = new Node(value, before, before.next);
			before = before.next;
			before.next.previous = before;
		} else {
			Node before = last;
			for (int i = size; i > position; i--) {
				before = before.previous;
			}
			
			before.next = new Node(value, before, before.next);
			before = before.next;
			before.next.previous = before;
		}
		
		size++;
		
	}
	
	public int remove() {
		
		int returnValue;
		
		if (empty) {
			throw new NoSuchElementException();
			
		} else if (first == last) {
			returnValue = last.value;
			first = last = null;
			empty = true;
			
		} else {
			returnValue = last.value;
			last = last.previous;
			last.next = null;
			
		}
		
		size--;
		return returnValue;
		
	}
	
	public static void main(String[] args) {
		DoublyLinkedList d = new DoublyLinkedList();
		d.add(1);
		d.add(2);
		d.add(3);
		d.add(4);
		d.add(5,2);
		
		System.out.println(d.remove());
		System.out.println(d.remove());
		System.out.println(d.remove());
		System.out.println(d.remove());
		System.out.println(d.remove());
	}
	
	public class Node {
		
		Node previous;
		Node next;
		
		int value;
		
		public Node(int value) {
			
			this(value, null, null);
			
		}
		
		public Node(int value, Node previous, Node next) {
			
			this.previous = previous;
			this.next = next;
			
			this.value = value;
			
		}
		
	}
}
