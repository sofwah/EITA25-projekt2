package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class govermentagency {

	private static Connection conn;
	PreparedStatement readrec = null;
	ResultSet rs = null;
	ConnecttoDB dbConn = new ConnecttoDB();
	
	
	public govermentagency(){

		conn = dbConn.ConnecttoDB();

	
	}
	
	public int getgovID(int id) {

		String getID = "select id_gov from govermentpeople where  id_gov = ?";
		try {
			readrec = conn.prepareStatement(getID);
			readrec.setInt(1, id);
			rs = readrec.executeQuery();
			while (rs.next()) {

				// System.out.println(rs.getInt(1));
				// System.out.println(" test 1");
				return rs.getInt(1);

			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("error");

		}

		// System.out.println(" han finns inte");
		return 0;

	}
}
