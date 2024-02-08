package cwsim;
/**
 * Implements a priority queue.
 * 
 * @author Ted Barnaby
 * @version 4/1/23
 *
 * @param <E> Any type that extends comparable of a type that is a super class of E or E itself.
 */
public class PriorityQueue<E extends Comparable<? super E>> {
	private E[] data;
	private int currSize;
	private static final int DEFAULT_SIZE = 10;
	
	/**
	 * Initialize an empty priority queue.
	 */
	PriorityQueue() {
		data = (E[]) new Comparable[DEFAULT_SIZE];
		currSize = 0;
	}
	
	/**
	 * Add a new element to the priority queue.
	 * 
	 * @param element The element to enqueue.
	 */
	public void enqueue(E element) {
		currSize++;
		if(currSize >= data.length) doubleQueue();
		data[currSize - 1] = element;	
	}
	
	/**
	 * Get but do not remove the highest
	 * priority element in the priority 
	 * queue.
	 * 
	 * @return The highest priority element.
	 */
	public E peek() {
		if(currSize == 0) return null;
		if(currSize == 1) return data[currSize - 1];
		
		int p = 0;
		for(int i = 1; i < currSize; i++) 
			if(data[i].compareTo(data[p]) > 0) 
				p = i;

		return data[p];
	}
	
	/**
	 * Return and remove the highest
	 * priority element in the priority 
	 * queue.
	 * 
	 * @return The highest priority element.
	 */
	public E dequeue() {
		if(currSize == 0) return null;
		if(currSize == 1) return data[--currSize];
		
		int p = 0;
		for(int i = 1; i < currSize; i++) 
			if(data[i].compareTo(data[p]) > 0) 
				p = i;
			
		E element = data[p];
		data[p] = data[--currSize];
		return element;
	}
	
	/**
	 * @return If the priority queue is logically empty.
	 */
	public boolean isEmpty() {
		return currSize == 0;
	}
	
	/**
	 * Makes the priority queue logically empty.
	 */
	public void makeEmpty() {
		currSize = 0;
	}
	
	/**
	 * @return The size of the priority queue.
	 */
	public int size() {
		return currSize;
	}
	
	/**
	 * Doubles the current size of the array
	 * that represents the priority queue.
	 */
	private void doubleQueue() {
		E[] newData = (E[]) new Comparable[data.length * 2];
		for(int i = 0; i < currSize; i++) newData[i] = data[i];
		data = newData;
	}
}
