import java.util.Scanner;

/**
 * Simulates a car wash with a priority
 * queue.
 * 
 * @author Ted Barnaby
 * @version 11/7/23
 */
public class CarWash {
	private SimStats stats;
	private static final int WASH_TIME = 3;
	
	/**
	 * Simulates the car wash with the given arrival times and prices given in scnr.
	 * 
	 * @param scnr Scanner containing a list of cars with their arrivalTime and price.
	 */
	public void simulate(Scanner scnr) {
		stats = new SimStats();
		LinkedList<Customer> cars = readCars(scnr);
		LinkedListIterator<Customer> itr = cars.first();
		PriorityQueue<Customer> pQueue = new PriorityQueue<Customer>();
		advanceToEnd(itr, pQueue, null, 0);
		itr = cars.first();
		printStats(itr);
	}
	
	
	/*
	 * Reads cars from fileScnr into the simulation.
	 */
	private LinkedList<Customer> readCars(Scanner scnr) {
		LinkedList<Customer> cars = new LinkedList<Customer>();
		LinkedListIterator<Customer> itr = cars.zeroth();
		int currID = 0;
		while(scnr.hasNext()) {
			cars.insert(new Customer(++currID, scnr.nextInt(), scnr.nextDouble()), itr);
			itr.advance();
		}
		return cars;
	}
	
	/*
	 * Prints the cars currently waiting in the priority queue to be washed.
	 */
	private void printWait(PriorityQueue<Customer> pQueue, int clock) {
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
	
	
	/*
	 * Calculates the statistics for the customers
	 * of the car wash once the simulation is complete.
	 */
	private void calcStats(LinkedListIterator<Customer> itr) {		
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
	
	/*
	 * Prints the statistics for the customers
	 * of the car wash once the simulation is complete.
	 */
	private void printStats(LinkedListIterator<Customer> itr) {
		calcStats(itr);
		for(int i = 0; i < 50; i++) {
			System.out.print("=");
		}
		System.out.println();
		
		System.out.printf("Cars Washed: %d"
				+ "\nMin wait is car ID #%d [$%.2f] at %d cycles"
				+ "\nMax wait is car ID #%d [$%.2f] at %d cycles"
				+ "\nAverage wait is %.1f"
				+ "\nTotal sales are $%.2f"
				+ "\nAverage price is $%.2f\n", stats.getNumWashes()
				, stats.getMinWait().getID(), stats.getMinWait().getPrice()
				, stats.getMinWait().getWaitTime(), stats.getMaxWait().getID()
				, stats.getMaxWait().getPrice(), stats.getMaxWait().getWaitTime()
				, stats.getAvgWait(), stats.getTotalSales(), stats.getAvgPrice());
		
		for(int i = 0; i < 50; i++) {
			System.out.print("=");
		}
	}
	
	
	
	/*
	 * Advances the simulation step by step to its end
	 * (once all the cars in the given list have been washed).
	 */
	private void advanceToEnd(LinkedListIterator<Customer> itr, PriorityQueue<Customer> pQueue, Customer currWash, int clock) {
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
		printWait(pQueue, clock);
		
		//Only do this on first execution to get car washed started.
		if(currWash == null && !pQueue.isEmpty()) {
			currWash = pQueue.dequeue();
			newWash(currWash, clock);
		}
		
		
		//Only enqueue the next car once the currWash car has finished.
		if(clock == currWash.getEndTime()) {
			System.out.printf("%d: Car %d exits wash\n", clock, currWash.getID());
			
			if(!pQueue.isEmpty()) {
				currWash = pQueue.dequeue();
				newWash(currWash, clock);
			} else return; //base case
		}			
		clock++;
		advanceToEnd(itr, pQueue, currWash, clock); //recursive call
	}
	
	
	/*
	 * Set a car's times based on the given clock.
	 */
	private void newWash(Customer currWash, int clock) {
		currWash.setStartTime(clock);
		currWash.setEndTime(clock + WASH_TIME);
		currWash.setWaitTime(currWash.getStartTime() - currWash.getArrivalTime());
		System.out.printf("%d: Car %d starts wash [Wait %d]\n", clock, currWash.getID(), currWash.getWaitTime());
		stats.setNumWashes(stats.getNumWashes() + 1);
	}
}
