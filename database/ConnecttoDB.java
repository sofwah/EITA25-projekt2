package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class ConnecttoDB {

	Connection conn = null;

	public Connection ConnecttoDB(){

		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospitalsystem", "root", "grupp13");// Establishing
																														// connection
			System.out.println("Connected With the database successfully");
			// Crating PreparedStatement object
			// PreparedStatement preparedStatement=connection.prepareStatement("select *
			// from division");
			// Creating Java ResultSet object
			// ResultSet resultSet=preparedStatement.executeQuery();
			// while(resultSet.next()){
			// int rollNo=resultSet.getInt("divID");
			// String name=resultSet.getString("division name");

			// Printing Results
			// System.out.println(rollNo+" "+name);
			// }
		} catch (SQLException e) {
			System.out.println("Error while connecting to the database");
		}
	

	return conn;

}

	public static void main(String[] args)throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		 ConnecttoDB connDatabase = new  ConnecttoDB();
		    connDatabase.ConnecttoDB();
	}
}
