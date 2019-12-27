import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;



public class SQLConnection {

	public static Connection dbConnector() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/de-store?user=Qasim&password=545127");	
			return conn; 
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e); 
			return null; 
		}
		
	}
	
	public static Connection dbConnector2() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/accounting_database?user=Qasim2&password=545127");
			
			return conn; 
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e); 
			return null; 
		}
		
	}
}
