package cwsim;
import java.util.Scanner;

/**
 * Simulates a car wash with a priority
 * queue.
 * 
 * @author Ted Barnaby
 * @version 11/8/23
 */
public class CarWash {
	private int washTime = 3;

	public void setWashTime(int washTime) {
		if(washTime <= 0 || washTime > 10) {
			System.err.println("Wash Time Out of Range.");
		}else {
			System.out.printf("Wash Time Set To %d.\n", washTime);
			this.washTime = washTime;
		}
	}

	public int getWashTime() {
		return washTime;
	}

	/**
	 * Simulates the car wash with the given arrival times and prices given in scnr.
	 * 
	 * @param scnr Scanner containing a list of cars with their arrivalTime and price.
	 */
	public void simulate(Scanner scnr) {
		SimStats stats = new SimStats();
		LinkedList<Customer> cars = readCars(scnr);
		
		if(cars.isEmpty()) {
			System.out.println("Empty List");
			return;
		}
		System.out.printf("\nStarting car wash with %d cycle wash time...\n\n", washTime);
		stats.setNumWashes(cars.size());
		
		LinkedListIterator<Customer> itr = cars.first();
		PriorityQueue<Customer> pQueue = new PriorityQueue<Customer>();
		
		advanceToEnd(itr, pQueue, null, 0);
		itr = cars.first();
		calcStats(itr, stats);
		printStats(stats);
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
	private void calcStats(LinkedListIterator<Customer> itr, SimStats stats) {
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
	private void printStats(SimStats stats) {
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
		System.out.println();
	}
	
	
	
	/*
	 * Advances the simulation step by step to its end
	 * (once all the cars in the given list have been washed).
	 */
	private void advanceToEnd(LinkedListIterator<Customer> itr, PriorityQueue<Customer> pQueue, Customer currWash, int clock) {
		enqueueCars(itr, pQueue, clock);
		printWait(pQueue, clock);
		currWash = dequeueCar(pQueue, currWash, clock);
		if(itr.retrieve() == null && currWash == null) return; //base case
		clock++;
		advanceToEnd(itr, pQueue, currWash, clock);
	}
	
	/*
	 * Enqueue the next cars appropriate for the given clock cycle.
	 */
	private void enqueueCars(LinkedListIterator<Customer> itr, PriorityQueue<Customer> pQueue, int clock) {
		Customer currCar = itr.retrieve();
		
		if(currCar != null)
			while(clock >= currCar.getArrivalTime()) {
				if(clock != currCar.getArrivalTime()) currCar.setArrivalTime(clock); //change currCar's arrivalTime if arrivalTimes in the file are out of order.
				pQueue.enqueue(currCar);
				System.out.printf("%d: Car %d ($%.2f) enters system\n", clock, currCar.getID(), currCar.getPrice());
				itr.advance();
				currCar = itr.retrieve();
				if(currCar == null) break;
			}
	}
	
	/*
	 * Dequeue the current washed car if it exists and its end time has been reached.
	 */
	private Customer dequeueCar(PriorityQueue<Customer> pQueue, Customer currWash, int clock) {
		if(!pQueue.isEmpty()) {
			if(currWash == null) {
				currWash = pQueue.dequeue();
				newWash(currWash, clock);
			}else if(clock == currWash.getEndTime()) {
				System.out.printf("%d: Car %d exits wash\n", clock, currWash.getID());
				currWash = pQueue.dequeue();
				newWash(currWash, clock);
			}
			return currWash;
		}else if(currWash != null) {
			if(clock == currWash.getEndTime()) {
				System.out.printf("%d: Car %d exits wash\n", clock, currWash.getID());
			}else return currWash;
		}	
		return null;
	}
	
	/*
	 * Set a car's times based on the given clock.
	 */
	private void newWash(Customer currWash, int clock) {
		currWash.setStartTime(clock);
		currWash.setEndTime(clock + washTime);
		currWash.setWaitTime(currWash.getStartTime() - currWash.getArrivalTime());
		System.out.printf("%d: Car %d starts wash [Wait %d]\n", clock, currWash.getID(), currWash.getWaitTime());
	}
}
