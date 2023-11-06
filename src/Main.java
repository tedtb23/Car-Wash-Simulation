import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * Main driver for CarWash simulation.
 * @author Ted Barnaby
 * @version 4/1/23
 */
public class Main {
	public static void main(String[] args) {
		CarWash carWash;
		try {
			Scanner scnr = new Scanner(System.in);
			System.out.println("Input path/name of file containing cars in the format of 'ArrivalTime(int) Price(float)'");
			String fileName = scnr.next();
			FileReader fileRead = new FileReader(fileName);
			scnr.close();
			scnr = new Scanner(fileRead);
			carWash = new CarWash(scnr);
			scnr.close();
		}catch(FileNotFoundException e) {
			System.err.print(e);
		}
	}
}
