package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Nurse {

	private static Connection conn;
	PreparedStatement readrec = null;
	ResultSet rs = null;
	ConnecttoDB dbConn = new ConnecttoDB();
	
	
	public Nurse() {
		conn = dbConn.ConnecttoDB();
	}
	
	
	
	
	
	public boolean getnurID(int id) {

		String getID = "select nurseID from nurse where nurseID = ?";
		try {
			readrec = conn.prepareStatement(getID);
			readrec.setInt(1, id);
			rs = readrec.executeQuery();
			while (rs.next()) {

				// System.out.println(rs.getInt(1));
				// TODO Auto-generated catch block
				return true;

			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("error");

		}

		System.out.println(" nurse not there");
		return false;

	}
	
}
