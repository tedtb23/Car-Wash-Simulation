package cwsim;
/**
 * Implements several statistics for the car wash simulation.
 * 
 * @author Ted Barnaby
 * @version 11/8/23
 */
public class SimStats {
	private int numWashes;
	private Customer maxWait;
	private Customer minWait;
	private double totalWait;
	private double avgWait;
	private double totalSales;
	private double avgPrice;
	
	public SimStats() {
		setNumWashes(0);
		setMaxWait(new Customer(-1, -1, -1));
		getMaxWait().setWaitTime(Integer.MIN_VALUE);
		setMinWait(new Customer(-1, -1, -1));
		getMinWait().setWaitTime(Integer.MAX_VALUE);
		setTotalWait(0);
		setTotalSales(0);
	}
	
	/**
	 * @return Total number of washes.
	 */
	public int getNumWashes() {
		return numWashes;
	}
	
	/**
	 * 
	 * @param numWashes Total number of washes.
	 */
	public void setNumWashes(int numWashes) {
		this.numWashes = numWashes;
	}
	
	/**
	 * 
	 * @return Maximum cycles a car has had to wait.
	 */
	public Customer getMaxWait() {
		return maxWait;
	}
	
	/**
	 * 
	 * @param maxWait Maximum cycles a car has had to wait.
	 */
	public void setMaxWait(Customer maxWait) {
		this.maxWait = maxWait;
	}
	
	/**
	 * 
	 * @return Minimum cycles a car has had to wait.
	 */
	public Customer getMinWait() {
		return minWait;
	}
	
	/**
	 * 
	 * @param minWait Minimum cycles a car has had to wait.
	 */
	public void setMinWait(Customer minWait) {
		this.minWait = minWait;
	}
	
	
	/**
	 * 
	 * @return Total wait time for all cars.
	 */
	public double getTotalWait() {
		return totalWait;
	}
	
	/**
	 * 
	 * @param totalWait Total wait time for all cars.
	 */
	public void setTotalWait(double totalWait) {
		this.totalWait = totalWait;
	}
	
	/**
	 * 
	 * @return Average wait time across all cars.
	 */
	public double getAvgWait() {
		return avgWait;
	}
	
	/**
	 * 
	 * @param avgWait Average wait time across all cars.
	 */
	public void setAvgWait(double avgWait) {
		this.avgWait = avgWait;
	}
	
	/**
	 * 
	 * @return Average price paid across all cars.
	 */
	public double getAvgPrice() {
		return avgPrice;
	}
	
	/**
	 * 
	 * @param avgPrice Average price paid across all cars.
	 */
	public void setAvgPrice(double avgPrice) {
		this.avgPrice = avgPrice;
	}
	
	/**
	 * 
	 * @return Total sales across all cars.
	 */
	public double getTotalSales() {
		return totalSales;
	}
	
	/**
	 * 
	 * @param totalSales Total sales across all cars.
	 */
	public void setTotalSales(double totalSales) {
		this.totalSales = totalSales;
	}
	
}
