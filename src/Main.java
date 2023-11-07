import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * Main driver for CarWash simulation.
 * @author Ted Barnaby
 * @version 11/7/23
 */
public class Main {
	public static void main(String[] args) {
		CarWash carWash = new CarWash();
		try {
			Scanner cScnr = new Scanner(System.in);
			Scanner fScnr;
			System.out.println("Input path/name of file containing cars in the format of 'ArrivalTime(int) Price(float)'");
			System.out.println("Or input 'q' to quit");
			String cmnd = cScnr.next();
			
			while(!cmnd.equals("q")) {
				String fileName = cmnd;
				FileReader fileRead = new FileReader(fileName);
				fScnr = new Scanner(fileRead);
				carWash.simulate(fScnr);
				fScnr.close();
				System.out.println("\n\nInput path/name of file containing cars in the format of 'ArrivalTime(int) Price(float)'");
				System.out.println("Or input 'q' to quit");
				cmnd = cScnr.next();
			}
			cScnr.close();
			
		}catch(FileNotFoundException e) {
			System.err.print(e);
			
		}
	}
}
