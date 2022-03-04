package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Doctor {
	
	
	private static Connection conn;
	PreparedStatement readrec = null;
	ResultSet rs = null;
	ConnecttoDB dbConn = new ConnecttoDB();
	
	
	public Doctor(){

		conn = dbConn.ConnecttoDB();

	
	}
	

	
/*	public boolean getdocID(int id) {

		String getID = "select docID from doctors where docID = ?";
		try {
			readrec = conn.prepareStatement(getID);
			readrec.setInt(1, id);
			rs = readrec.executeQuery();
			while (rs.next()) {

				//System.out.println(rs.getInt(1));
				// TODO Auto-generated catch block
				int i = rs.getInt(1);
				return true;

			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("error");

		}

		System.out.println(" doctors not there");
		return false;

	}*/
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public boolean getdocID(int id) {

		String getID = "select docID from doctors where docID = ?";
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

		System.out.println(" doctors not there");
		return false;

	}
	
	
	public boolean getdocdiv(int id) {

		String getID = "select divID from doctors where docID = ?";
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

		System.out.println(" there is no doctors' division");
		return false;

	}
	
	
	
	public String getNameRec(int docId) {

		try (PreparedStatement statement = conn.prepareStatement("select d.name from record r inner join doctors d on (d.docID=r.docID) where  r.docID = ?")){
		statement.setInt(1, docId);
		ResultSet rs =  statement.executeQuery();
		rs.next();
		String username= rs.getString("name");
		System.out.println(username);
		//logging(getlatestrecID(),username,"CREATED");
		return username;
		}catch (SQLException e) {
			e.printStackTrace();
			System.out.println("error1");

	}
		return "";

		//System.out.println(" there is no doctors' division");
		

	}
	
public static void main(String args[]) {
		
		Doctor d = new Doctor();
		//d.getdocID(1);
	//	opre.getID(10);
		//opre.getdocID(1);
	//	opre.getnurseID(1);
	//	opre.getgovID(3);
		//opre.accestoread();
		d.getNameRec(1);
		
	}
}
