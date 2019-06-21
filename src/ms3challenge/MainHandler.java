package ms3challenge;

import java.io.*;

/*
 * Consume CSV file
 * Parse data
 * Insert to SQLite In-Memory DB
 * 
 */

public class MainHandler {

	private CSVHandler c_handler;
	private DBHandler d_handler;
	
	public void run(File f) {
		
		/*
		 * CSV Handling portion
		 * - Pass the csv filename to the handler
		 * - handler processes the CSV info
		 */
		c_handler = new CSVHandler();
		d_handler  = new DBHandler();
		
		c_handler.process(f);
		
		/*
		 * DB Handling portion
		 * - Construct db shape
		 * - pass db handler to CSV handler
		 * - CSV handler provides db handler with entries
		 * - db handler adds each entry to db
		 */
		
		d_handler.construct_db();
		c_handler.add_todb(d_handler);
		c_handler.create_badcsv();
		//d_handler.select_all();
		d_handler.close_connection();
		
		try {
			write_log();
		} catch (IOException e) {
			System.out.printf("Failed to write to log.");
		}
	}
	
	
	/*
	 * Writes the log file at the end of the run.
	 */
	
	public void write_log() throws IOException {
		PrintWriter wr = new PrintWriter(new FileWriter("csvreader.log"));
		wr.printf("Correctly processed entries: %d", c_handler.get_good());
		wr.println("");
		wr.printf("Failed to process: %d",c_handler.get_bad());
		wr.println("");
		wr.printf("Total received: %d", c_handler.get_total());
		wr.close();
	}
	
	/*
	 * Opens the interface and lets it select the file.
	 */
	
	public static void main(String args[]) {
		InterfaceHandler int_h = new InterfaceHandler();
		try {
			int_h.set_gui("CSV Parser -- Select a CSV File");
		} catch (IOException e) {
			System.out.println("Failed to create interface.");
			e.printStackTrace();
		}
	}
}
