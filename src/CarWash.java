import java.util.Scanner;

/**
 * Simulates a car wash with a priority
 * queue and prints the statistics of 
 * the customers/cars at the end.
 * 
 * @author Ted Barnaby
 * @version 4/1/23
 */
public class CarWash {
	private PriorityQueue<Customer> pQueue;
	private LinkedList<Customer> cars;
	private LinkedListIterator<Customer> itr;
	private SimStats stats;
	private static final int WASH_TIME = 3;
	
	/**
	 * Runs the car wash simulation.
	 * 
	 * @param fileScnr Scanner for the file containing a list of cars with their arrivalTime and price.
	 */
	CarWash(Scanner fileScnr) {
		pQueue = new PriorityQueue<Customer>();
		cars = new LinkedList<Customer>();
		stats = new SimStats();
		
		itr = cars.zeroth();
		readCars(fileScnr);
		itr = cars.first();
		advanceToEnd(null, 0);
		itr = cars.first();
		printStats();
	}
	
	/**
	 * Reads cars from fileScnr into the simulation.
	 * 
	 * @param fileScnr Scanner for the file containing a list of cars with their arrivalTime and price.
	 */
	private void readCars(Scanner fileScnr) {
		int currID = 0;
		while(fileScnr.hasNext()) {
			cars.insert(new Customer(++currID, fileScnr.nextInt(), fileScnr.nextDouble()), itr);
			itr.advance();
		}
	}
	
	/**
	 * Prints the cars currently waiting in the priority queue to be washed.
	 * 
	 * @param clock Simulation clock.
	 */
	private void printWait(int clock) {
		int pSize = pQueue.size();
		Customer[] tempData = new Customer[pSize];
		
		System.out.printf("%d: Waiting: [ ", clock);
		for(int i = 0; i < pSize; i++) {
			tempData[i] = pQueue.dequeue();
			System.out.print(tempData[i].getID() + " ");
		}
		System.out.println("]");
		for(int i = 0; i < pSize; i++) {
			pQueue.enqueue(tempData[i]);
		}
	}
	
	
	/**
	 * Calculates the statistics for the customers
	 * of the car wash once the simulation is complete.
	 */
	private void calcStats() {		
		while(itr.isValid()) {
			if(itr.retrieve().getWaitTime() <= stats.getMinWait().getWaitTime()) {
				stats.setMinWait(itr.retrieve());
			}
			if(itr.retrieve().getWaitTime() >= stats.getMaxWait().getWaitTime()) {
				stats.setMaxWait(itr.retrieve());
			}
			stats.setTotalWait(stats.getTotalWait() + itr.retrieve().getWaitTime());
			stats.setTotalSales(stats.getTotalSales() + itr.retrieve().getPrice());
			itr.advance();
		}
		stats.setAvgWait(stats.getTotalWait()/stats.getNumWashes());
		stats.setAvgPrice(stats.getTotalSales()/stats.getNumWashes());
	}
	
	/**
	 * Prints the statistics for the customers
	 * of the car wash once the simulation is complete.
	 */
	private void printStats() {
		calcStats();
		for(int i = 0; i < 50; i++) {
			System.out.print("=");
		}
		System.out.println();
		
		System.out.printf("Cars Washed: %d"
				+ "\nMin wait is car ID #%d [$%.2f] at %d cycles"
				+ "\nMax wait is car ID #%d [$%.2f] at %d cycles"
				+ "\nAverage wait is %.1f"
				+ "\nTotal sales are $%.2f"
				+ "\nAverage price is $%.2f", stats.getNumWashes()
				, stats.getMinWait().getID(), stats.getMinWait().getPrice()
				, stats.getMinWait().getWaitTime(), stats.getMaxWait().getID()
				, stats.getMaxWait().getPrice(), stats.getMaxWait().getWaitTime()
				, stats.getAvgWait(), stats.getTotalSales(), stats.getAvgPrice());
	}
	
	
	
	/**
	 * Advances the simulation step by step to its end
	 * (once all the cars in the given list have been washed).
	 *
	 *@param currWash The current car in the car wash.
	 *@param clock Simulation clock.
	 */
	private void advanceToEnd(Customer currWash, int clock) {
		Customer currCar = itr.retrieve();
		
		//Enqueue the next car from itr once its arrivalTime has been reached.
		if(currCar != null)
			while(clock == currCar.getArrivalTime()) {
				pQueue.enqueue(currCar);
				System.out.printf("%d: Car %d ($%.2f) enters system\n", clock, currCar.getID(), currCar.getPrice());
				itr.advance();
				currCar = itr.retrieve();
				if(currCar == null) break;
			}
		printWait(clock);
		
		//Only do this on first execution to get car washed started.
		if(currWash == null) currWash = newWash(clock);
		
		
		//Only enqueue the next car once the currWash car has finished.
		if(clock == currWash.getEndTime()) {
			System.out.printf("%d: Car %d exits wash\n", clock, currWash.getID());
			
			if(!pQueue.isEmpty())
				currWash = newWash(clock);
			else return; //base case
		}			
		clock++;
		advanceToEnd(currWash, clock); //recursive call
	}
	
	
	/**
	 * Dequeue a new car to enter the car wash.
	 * 
	 * @param clock Simulation clock.
	 * @return New car to enter the car wash.
	 */
	private Customer newWash(int clock) {
		Customer currWash = pQueue.dequeue();
		currWash.setStartTime(clock);
		currWash.setEndTime(clock + WASH_TIME);
		currWash.setWaitTime(currWash.getStartTime() - currWash.getArrivalTime());
		System.out.printf("%d: Car %d starts wash [Wait %d]\n", clock, currWash.getID(), currWash.getWaitTime());
		stats.setNumWashes(stats.getNumWashes() + 1);
		return currWash;
	}
}
