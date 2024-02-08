package cwsim;
/**
 * Specifies a customer that can be compared to
 * another customer through their prices and arrivalTimes.
 * 
 * @author Ted Barnaby
 * @version 4/1/23
 */
public class Customer implements Comparable<Customer> {
	private int id;
	private int arrivalTime;
	private double price;
	private int startTime;
	private int endTime;
	private int waitTime;
	
	/**
	 * Initialize a customer with an id, arrivalTime, and price.
	 * 
	 * @param id The id number of the Customer.
	 * @param arrivalTime The arrival time of the Customer.
	 * @param price The price the customer is paying.
	 */
	public Customer(int id, int arrivalTime, double price) {
		this.id = id;
		this.arrivalTime = arrivalTime;
		this.price = price;
	}
	
	/**
	 * @return Customer's ID number.
	 */
	public int getID() {
		return id;
	}
	
	/**
	 * @param id Set Customer's ID number.
	 */
	public void setID(int id) {
		this.id = id;
	}
	
	/**
	 * @return Customer's arrival time.
	 */
	public int getArrivalTime() {
		return arrivalTime;
	}
	
	/**
	 * @param arrivalTime Set Customer's arrival time.
	 */
	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	
	/**
	 * @return The price the Customer is paying.
	 */
	public double getPrice() {
		return price;
	}
	
	/**
	 * @param price Set Customer's price.
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	
	/**
	 * @return The time the Customer begins to be serviced at.
	 */
	public int getStartTime() {
		return startTime;
	}
	
	/**
	 * @param startTime Set the start time the Customer begins to be serviced at.
	 */
	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}
	
	/**
	 * @return The time the Customer's service is completed.
	 */
	public int getEndTime() {
		return endTime;
	}
	
	/**
	 * @param endTime Set the end time when the Customer's service will be completed.
	 */
	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}
	
	/**
	 * @return The amount of time the Customer spent waiting between their
	 * arrival time and the start of their service at startTime.
	 */
	public int getWaitTime() {
		return waitTime;
	}
	
	/**
	 * @param waitTime Set the Customer's wait time.
	 */
	public void setWaitTime(int waitTime) {
		this.waitTime = waitTime;
	}
	
	/**
	 * Compare Customers based on their prices.
	 * If prices are equal compare based on arrivalTime 
	 */
	@Override
	public int compareTo(Customer other) {
		int priceCompare = Double.compare(price, other.price);
		if(priceCompare != 0)
			return priceCompare;
		else
			return Integer.compare(other.arrivalTime, arrivalTime);
	}	
}
