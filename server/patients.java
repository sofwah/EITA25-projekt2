package server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class patients {
	
	private static Connection conn;
	PreparedStatement readrec = null;
	ResultSet rs = null;
	ConnecttoDB dbConn = new ConnecttoDB();
	
	
	public patients(){

		conn = dbConn.ConnecttoDB();

	
	}
	
	public boolean getpatID(int id) {

		String getID = "select patID from patient where patID = ?";
		try {
			readrec = conn.prepareStatement(getID);
			readrec.setInt(1, id);
			rs = readrec.executeQuery();
			while (rs.next()) {

				// System.out.println(rs.getInt(1));
				// System.out.println(" test 1");
				return true;

			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("error");

		}

		System.out.println(" sorry no patient");
		return false;

	}
	public String getpatname(int id) {

		String getID = "select name from patient where patID = ?";
		try {
			readrec = conn.prepareStatement(getID);
			readrec.setInt(1, id);
			rs = readrec.executeQuery();
			while (rs.next()) {

				// System.out.println(rs.getInt(1));
				// TODO Auto-generated catch block
				return rs.getString(1);

			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("error");

		}

		System.out.println("not there");
		return " ";

	}
	
	
	
	

}
