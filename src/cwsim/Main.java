package cwsim;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Main driver for CarWash simulation.
 * @author Ted Barnaby
 * @version 11/8/23
 */
public class Main {
	public static void main(String[] args) {
		CarWash carWash = new CarWash();
		try {
			Scanner cScnr = new Scanner(System.in);
			Scanner fScnr;
			printCommands(carWash.getWashTime());
			String cmnd = cScnr.next();
			
			while(!cmnd.equals("q")) {
				if(cmnd.equals("t")) {
					System.out.println("Input the new cycles per wash (integer between 1 and 10).");
					try{
						int time = cScnr.nextInt();
						carWash.setWashTime(time);
					}catch (InputMismatchException mismatch) {
						System.err.println("Not a valid integer");
						cScnr.next();
					}
					printCommands(carWash.getWashTime());
					cmnd = cScnr.next();
					continue;
				}
				FileReader fileRead = new FileReader(cmnd);
				fScnr = new Scanner(fileRead);
				carWash.simulate(fScnr);
				fScnr.close();
				printCommands(carWash.getWashTime());
				cmnd = cScnr.next();
			}
			cScnr.close();
			
		}catch(FileNotFoundException e) {
			System.err.println(e.getMessage());
			main(args);
		}
	}

	private static void printCommands(int washTime) {
		System.out.println("\nInput path\\name of file containing cars in the format of \"ArrivalTime(int) Price(double)\".");
		System.out.printf("Or input 't' to update the cycles per wash (currently set to %d).\n", washTime);
		System.out.println("Or input 'q' to quit.");
	}
}
