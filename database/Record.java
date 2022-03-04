package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Record {
	private static Connection conn;
	PreparedStatement readrec = null;
	ResultSet rs = null;
	ConnecttoDB dbConn = new ConnecttoDB();
	
	
	public Record(){

		conn = dbConn.ConnecttoDB();

	
	}
	public  int getlatestrecID() {

		String getID = "SELECT recID FROM record WHERE recID=(SELECT max(recID) FROM record)";
		try {
			readrec = conn.prepareStatement(getID);
			rs = readrec.executeQuery();
			while (rs.next()) {

				System.out.println(rs.getInt(1));
				// System.out.println(" test 1");
				
				return rs.getInt(1);

			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("error");

		}

		 System.out.println(" such values dont exist");
		return 0;

	}
	public  int getrecID(int id) {

		String getID = "select recID from record where  recID = ?";
		try {
			readrec = conn.prepareStatement(getID);
			readrec.setInt(1, id);
			rs = readrec.executeQuery();
			while (rs.next()) {

				System.out.println(rs.getInt(1));
				// System.out.println(" test 1");
				
				return rs.getInt(1);

			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("error");

		}

		 System.out.println(" such values dont exist");
		return 0;

	}
	
}
