/**
 * Specifies an iterator for a linked list.
 * 
 * @author Ted Barnaby
 * @version 4/1/23
 *
 * @param <E> Generic variable.
 */
public class LinkedListIterator<E> {
	private ListNode<E> curr;
	
	/**
	 * Initializes a LinkedListIterator with a position specified by curr.
	 * 
	 * @param curr The current element seen by the iterator.
	 */
	LinkedListIterator(ListNode<E> curr) {
		this.curr = curr;
	}
	
	/**
	 * @return If the iterator still points to
	 * a valid node in a linked list.
	 */
	public boolean isValid() {
		return curr != null;
	}
	
	/**
	 * Returns the current element seen by the iterator.
	 * 
	 * @return The current element seen by the iterator.
	 */
	public E retrieve() {
		return isValid() ? curr.getElement() : null;
	}
	
	/**
	 * @return The current ListNode seen by the iterator.
	 */
	public ListNode<E> getCurr() {
		return curr;
	}
	
	/**
	 * Advances the iterator to the next element.
	 */
	public void advance() {
		if(isValid()) {
			curr = curr.getNext();
		}
	}
}
