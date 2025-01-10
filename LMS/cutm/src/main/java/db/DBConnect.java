package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
	
	private static Connection Conn;

	public static Connection getconn() {
		
		try {
			if(Conn==null) {
				 Class.forName("com.mysql.cj.jdbc.Driver");
				Conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/LMS?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true", "root", "264351");
			}
			
		} catch (Exception e){
			e.printStackTrace();
		}
		
		
		return Conn;
	}
}
