/**
 * Implementation of a LinkedList using ListNode<E>
 * as the basic node structure.
 * 
 * @author Ted Barnaby
 * @version 4/1/23
 *
 * @param <E> Generic variable.
 */
public class LinkedList<E> {
	private ListNode<E> head;
	
	/**
	 * Initialize an empty list.
	 */
	public LinkedList() {
		head = new ListNode<E>();
	}
	
	/**
	 * @return If the linked list is logically empty.
	 */
	public boolean isEmpty() {
		return head.getNext() == null;
	}
	
	/**
	 * Makes the linked list logically empty.
	 */
	public void makeEmpty() {
		head.setNext(null);
	}
	
	/**
	 * @return Iterator of the head node.
	 */
	public LinkedListIterator<E> zeroth() {
		return new LinkedListIterator<E>(head);
	}
	
	/**
	 * @return Iterator of the first data node.
	 */
	public LinkedListIterator<E> first() {
		return new LinkedListIterator<E>(head.getNext());
	}
	
	/**
	 * Insert an element to the list after p.
	 * 
	 * @param element The element to insert to the list.
	 * @param p The iterator specifying the location to insert the element.
	 */
	public void insert(E element, LinkedListIterator<E> p) {
		if(p != null && p.getCurr() != null) 
			p.getCurr().setNext(new ListNode<E>(element, p.getCurr().getNext()));
	}
	
	/**
	 * Find an element in the list.
	 * 
	 * @param element the element to find.
	 * @return Iterator with its position on the specified element.
	 * Returns null if not found.
	 */
	public LinkedListIterator<E> find(E element) {
		ListNode<E> itr = head.getNext();
		
		while(itr != null && !itr.getElement().equals(element))
			itr = itr.getNext();
		
		return new LinkedListIterator<E>(itr);
	}
	
	/**
	 * Find the element previous to a 
	 * specified element in the list.
	 * 
	 * @param element the element to find the previous element for.
	 * @return Iterator with its position on the element previous to the specified element.
	 * Returns the last element in the list if not found.
	 */
	public LinkedListIterator<E> findPrevious(E element) {
		ListNode<E> itr = head;
		
		while(itr.getNext() != null && !itr.getNext().getElement().equals(element))
			itr = itr.getNext();
		
		return new LinkedListIterator<E>(itr);
	}
	
	/**
	 * Remove the first occurrence of an element.
	 * @param element the element to remove.
	 */
	public void remove(E element) {
		LinkedListIterator<E> p = findPrevious(element);
		
		if(p.getCurr().getNext() != null)
			p.getCurr().setNext(p.getCurr().getNext().getNext());
	}
	
	/**
	 * Return the list's size.
	 * @return The integer size of the list.
	 */
	public int size() {
		LinkedListIterator<E> p = first();
		int size = 0;
		
		while(p.isValid()) {
			size++;
			p.advance();
		}
		return size;
	}
}
