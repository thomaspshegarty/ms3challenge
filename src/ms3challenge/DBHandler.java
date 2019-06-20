package ms3challenge;

import java.sql.*;
import java.util.List;

public class DBHandler {

	private String conn_url = "jdbc:sqlite::memory:";
	private Connection conn;
	
	public void construct_db() {
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection(conn_url);
			
			String create = "CREATE TABLE IF NOT EXISTS csv (\n"
				+ "A text, "
				+ "B text, "
				+ "C text, "
				+ "D text, "
				+ "E text, "
				+ "F text, "
				+ "G text, "
				+ "H text,"
				+ "I text,"
				+ "J text"
				+ ");";
			
			Statement s = conn.createStatement();
			s.execute(create);
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("Could not create in-memory DB.");
			e.printStackTrace();
		}
	}	
	public void add_values(List<String> entry) {
		try{
			String insert = "INSERT INTO csv VALUES(?,?,?,?,?,?,?,?,?,?);";
			PreparedStatement s = conn.prepareStatement(insert);
			int index = 1;
			for(String v: entry) {
				s.setString(index, v);
				index = index+1;
			}
			s.executeUpdate();
		}catch(Exception e) {
			System.out.println("Failed to insert into csv table.");
			System.out.println(entry.size());
			e.printStackTrace();
		}
	}
	
	/*
	 * Just a testing method for examining the results of the full db update
	 */
	
    public void select_all(){
        String sql = "SELECT * FROM csv";
        
        try (Statement s  = conn.createStatement();
             ResultSet rs    = s.executeQuery(sql)){
        	
            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getString(1)+","+
                		rs.getString(2)+","+
                		rs.getString(3)+","+
                		rs.getString(4)+","+
                		rs.getString(5)+","+
                		rs.getString(6)+","+
                		rs.getString(7)+","+
                		rs.getString(8)+","+
                		rs.getString(9)+","+
                		rs.getString(10));
                System.out.println("");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void close_connection() {
    	try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
}