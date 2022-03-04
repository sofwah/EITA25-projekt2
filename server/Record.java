package server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.*;

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
	
	public int getrecpatid(int id) {

		String getID = "select r.recID from record r inner join patient p on(p.patID=r.patID) where r.patID=?";
		
		
		try {
			readrec = conn.prepareStatement(getID);
			readrec.setInt(1, id);
			rs = readrec.executeQuery();
			while (rs.next()) {
					//list.add(rs.getInt(1));
				//System.out.print(rs.getInt("recid"));
				// System.out.println(list);
				return rs.getInt(1);
			
			}
			

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("error");

		}

		 System.out.println("end");
		return 0;

	}
	
	
	public int getrecdocid(int id) {

		String getID = "select r.recID from record r inner join doctors d on(d.docID=r.docID) where r.docID=?";
		
		
		try {
			readrec = conn.prepareStatement(getID);
			readrec.setInt(1, id);
			rs = readrec.executeQuery();
			while (rs.next()) {
					//list.add(rs.getInt(1));
				//System.out.print(rs.getInt("recid"));
				// System.out.println(list);
				return rs.getInt(1);
			
			}
			

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("error");

		}

		 System.out.println("end");
		return 0;

	}
	public static void main(String args[]) {
		Record r = new Record();
		r.getrecpatid(9);
		r.getrecdocid(6);
		
	}
	
}
