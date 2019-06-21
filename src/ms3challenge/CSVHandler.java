package ms3challenge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
/*
 * Has to read the given CSV file
 * correctly parse commas with double quotes
 * Keep count of number of good, bad and total
 */
import java.util.Arrays;

public class CSVHandler {

	private int good = 0;
	private int bad = 0;
	private int total = 0;
	private File f;
	private List<List<String>> csv_contents;
	private List<List<String>> bad_contents;
	private static final SimpleDateFormat frm = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
	
	/*
	 * Private methods
	 *  - open instantiates the csv file
	 *  - read_tolist reads in the contents and calls csv_split
	 *  - csv_split splits each line of the csv split based on regex for extra commas
	 *  - print_csvarr is testing method for displaying result of csv_split
	 */
	
	private void open() {
		csv_contents = new ArrayList<List<String>>();
		bad_contents = new ArrayList<List<String>>();
	}	
	private void read_tolist() {
		BufferedReader s = null;
		try {
			s = new BufferedReader(new FileReader(f));
		
			String str;
			s.readLine();
			while((str = s.readLine()) != null) {
				csv_contents.add(csv_split(str));
				total = total+1;
			}
		}catch (Exception e) {
			System.out.println("Could not instantiate BufferedReader.");
		}
	}	
	private List<String> csv_split(String test) {
		return Arrays.asList(test.split(",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)"));
	}	

	/*
	 * Public functions
	 * - add_todb puts checks validity of entry and submits it to the dbhandler
	 * - process is the public facing method to open and then read csv
	 * - create_badcsv makes the csv of failed entries
	 */
	
	public void add_todb(DBHandler db) {
		
		for(List<String> cont: csv_contents) {
			if(cont.contains("") || cont.size() != 10) {
				/*
				 * Because there is a blank space as a value, this entry is incomplete,
				 * therefore it should be written to the bad-data-<timestamp>.csv
				 * and added to the count of failed entries.
				 */
				bad++;
				bad_contents.add(cont);
			}else {
				/*
				 * This is a good entry. Should be added to the db and good count should
				 * be incremented 1.
				 */
				db.add_values(cont);
				good++;
			}
		}
		
	}	
	public void process(File file_passed) {
		f = file_passed;
		open();
		read_tolist();
	}	
	public void create_badcsv() {
		Date date = new Date();
		long time = date.getTime();
		Timestamp ts = new Timestamp(time);
		
		String filename = "bad-data-"+frm.format(ts)+".csv";
		try{
			FileWriter csv_writer = new FileWriter(filename);
			csv_writer.append("A,B,C,D,E,F,G,H,I,J");
			csv_writer.append("\n");
			for(List<String> bad_entry: bad_contents) {
				csv_writer.append(String.join(",", bad_entry));
				csv_writer.append("\n");
			}
			
			csv_writer.flush();
			csv_writer.close();
		}catch(Exception e){
			System.out.println("Could not create "+filename+".");
		}
		
	}
		
	/*
	 * Getter functions
	 */
	
	public int get_good() {
		return good;
	}
	public int get_bad() {
		return bad;
	}
	public int get_total(){
		return total;
	}
	
}
