/**
 * Basic structure for a node that can be linked
 * to more nodes to form a LinkedList. 
 * 
 * @author Ted Barnaby
 * @version 4/1/23
 *
 * @param <E> Generic variable.
 */
public class ListNode<E> {
	private E element;
	private ListNode<E> next;
	
	/**
	 * Initialize an empty node.
	 */
	public ListNode() {
		element = null;
		next = null;
	}
	
	/**
	 * Initialize the node with data.
	 * 
	 * @param element The data of the node.
	 */
	public ListNode(E element) {
		this.element = element;
	}
	
	/**
	 *Initialize a node with data and
	 *a link to another node.
	 * 
	 * @param element The data of the node.
	 * @param next The link to the next node.
	 */
	public ListNode(E element, ListNode<E> next) {
		this.element = element;
		this.next = next;
	}
	
	/**
	 * @return The data contained in this node.
	 */
	public E getElement() {
		return element;
	}
	
	/**
	 * @param element The data to set to this node.
	 */
	public void setElement(E element) {
		this.element = element;
	}
	
	/**
	 * @return Reference to the next node.
	 */
	public ListNode<E> getNext() {
		return next;
	}
	
	/**
	 * @param Set reference to the next node.
	 */
	public void setNext(ListNode<E> next) {
		this.next = next;
	}
	
}
