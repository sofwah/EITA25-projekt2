package database;

import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class permission {

	private Connection conn;
	PreparedStatement readrec = null;
	ResultSet rs = null;
	Operationread or = null;

	ConnecttoDB dbConn = new ConnecttoDB();

	public permission() {
		conn = dbConn.ConnecttoDB();
	}

	public int getID(int id) {

		String getID = "select ID from person where ID = ?";
		try {
			readrec = conn.prepareStatement(getID);
			readrec.setInt(1, id);
			rs = readrec.executeQuery();
			while (rs.next()) {

				// System.out.println(rs.getInt(1));
				// TODO Auto-generated catch block
				return rs.getInt(1);

			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("error");

		}

		System.out.println(" not there");
		return 0;

	}

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
	/*
	 * public void get() {
	 * 
	 * 
	 * try { String getIddoctor =
	 * "SELECT d.name, n.name, r.recID FROM record r INNER JOIN doctors d ON (d.docID=r.docID) INNER JOIN nurse n ON (n.nurseID=r.nurseID) where r.recID = 1;"
	 * ;
	 * 
	 * readrec = conn.prepareStatement( getIddoctor); rs = readrec.executeQuery();
	 * while (rs.next()) {
	 * 
	 * System.out.println("#"+rs.getInt(1)+ ", Doctor: "+ rs.getString(2)
	 * +", Nurse: "+ rs.getString(3)+ ",Patient "+ rs.getString(4) + ";Department "+
	 * rs.getString(5) + ",\n Note: "+ rs.getString(6) );
	 * 
	 * /*int id = rs.getInt(1); String english = rs.getString("docname"); String
	 * spanish = rs.getString("nursename");
	 */

	/*
	 * } } catch (Exception e) { System.out.println("error"); }
	 * 
	 */

	/**
	 * HÄR BÖRJAR VILLKOREN READ WRITE,EDIT OCH DELET
	 * 
	 * @param id
	 * @return
	 */

	/**
	 * READ FILE SOM LÄKARE
	 * 
	 * @param id
	 * @return
	 */

	// hämta journalen för en given id/div
	public int getJournal(int id, String username) {

		if (getID(id) == 0) {

			System.out.println(" sorry aint sorry");
		}

		// if(getdocID(id) == true ) {
		// String getJournal = "select r.recID, p.name, d.name from record r INNER JOIN
		// patient p on(p.patID =r.patID) INNER JOIN doctors d ON(d.docID =r.docID)
		// where r.docID =? ";

//	}else
		if (getgovID(id) == id) {
			System.out.println(" stop");

		} else {

			String getJournal = "select r.recID, p.name, d.name, n.name, di.name, r.notes from record r inner join patient p on(p.patID = r.patID) inner join doctors d on(d.docID=r.docID) inner join nurse n on (n.nurseID =r.nurseID) inner join division di on (di.divID =r.divID) where r.docID =? or r.nurseID=? or r.patID=?";

			try {

				readrec = conn.prepareStatement(getJournal);

				readrec.setInt(1, id);
				readrec.setInt(2, id);
				readrec.setInt(3, id);

				rs = readrec.executeQuery();
				while (rs.next()) {

					// System.out.println("#"+rs.getInt(1)+ ", Doctor: "+ rs.getString(2) +", Nurse:
					// "+ rs.getString(3)+ ",Patient "+ rs.getString(4) + ";Department "+
					// rs.getString(5) + ",\n Note: "+ rs.getString(6));

					System.out.println("#" + rs.getInt(1) + " Patient:" + rs.getString(2) + "Doctor: " + rs.getString(3)
							+ "Nurse: " + rs.getString(4) + "Division: " + rs.getString(5) + " Notes: "
							+ rs.getString(6));
					/*
					 * int id = rs.getInt(1); String english = rs.getString("docname"); String
					 * spanish = rs.getString("nursename"); }
					 */
				}

				logging(id, username, "READ");
			} catch (Exception e) {
				System.out.println("error");
			}
			// return rs.getInt(1);

			// }else {
			// System.out.println(" doctors not there");
		}
//	}
		return 0;
	}

	/**
	 * EDIT
	 * 
	 * @param args
	 */
	// FIX se till att record som de
	public int editjournal(int id, int docornursID) {

		if (getID(docornursID) == 0) {

			System.out.println(" sorry aint sorry");
		}

		if (getdocID(docornursID) == false) { // något fel här
			if (getnurID(docornursID) == false) {
				System.out.println(" denied access");
			}
		} else {

			try (PreparedStatement statement = conn
					.prepareStatement("UPDATE record SET notes = ? WHERE  recID=? and (nurseID=? or docID=?)")) {

				statement.setString(1, "testnytvå"); // se till att det är en inputdialog som ska mata in vad man vil
														// ländra
				statement.setInt(2, id); // se till att det input från den nurvandr aktiva användaren
				statement.setInt(3, docornursID);
				statement.setInt(4, docornursID);
				statement.executeUpdate();
				// logging(recid,username,"Edit");

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("error");
			}
		}
		return 0;

	}

	/**
	 * WRITE
	 * 
	 * @param args
	 */

	public void CreatARecord(int patID, int docID, int nurseID, int divID, int govID, String notes) {
	
		if(getdocID(docID) == true && getnurID(nurseID)==true && getpatID(patID)== true) {
		
		try (PreparedStatement statement = conn.prepareStatement("INSERT INTO record (patID, docID, nurseID, divID, govID, notes) values(?,?,?,?,?,?)")) {

			statement.setInt(1, patID); 
			statement.setInt(2, docID); 
			statement.setInt(3, nurseID);
			statement.setInt(4, divID);
			statement.setInt(5, govID);
			statement.setString(6, notes);
			
			statement.executeUpdate();
			
			
			try (PreparedStatement statement2 = conn.prepareStatement("select d.name from record r inner join doctors d on (d.docID=r.docID) where  r.docID = ?")){
				ResultSet rs =  statement2.executeQuery();
				String username= rs.getString("namn");
				
				logging(getlatestrecID(),username,"CREATED");
				}catch (SQLException e) {
					e.printStackTrace();
					System.out.println("error1");

			} 
		}catch (SQLException e) {
				e.printStackTrace();
				System.out.println("error2");

			}

		
			System.out.println("Create a record");
		
		}else {
			System.out.println("U CANT KEEP STEPPING");
		}
	

	
}

	

	/**
	 * DELETE
	 * 
	 * @param args
	 */

	public void govRemove(int govID, int recID) {

		if (getgovID(govID) != (govID)) {
			System.out.println("MIND U BUIZZNES");
		} else {
			try (PreparedStatement statement2 = conn
					.prepareStatement("DELETE FROM record WHERE govID =? and recID=?")) {

				statement2.setInt(1, govID);
				statement2.setInt(2, recID);

				statement2.executeUpdate();
				System.out.println(govID + "has deleted the record" + recID);
			} catch (SQLException e) {
				// TODO Auto-generated catch block

				e.printStackTrace();
				System.out.println("erros");
			}
		}
	}

	/**
	 * AUDIT LOGA
	 * 
	 * @param args
	 */
	public static void logging(int id, String username, String parameter) {

		try {

			// Scanner sr = new Scanner(new File("audit/log.txt"));// läs in från klassen
			// journal alla ändringar som gjorde

			// StringBuffer sb=new StringBuffer();

			// sb.append( id+ ", "+ username+", "+ parameter+LocalDateTime.now()+ "\n");
			FileWriter bw = new FileWriter("audit/log.txt", true);

			bw.write(id + ", " + username + ", " + parameter + LocalDateTime.now() + "\n");
			// sr.close();
			bw.close();
			// System.out.println("Wrote to auditLog");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {

		permission per = new permission();
		// per.aretheyallowedtoread();
		// per.getJournal(1,"sofia");
		// per.getdocdiv(1);
		// per.editjournal(1,4);
		// per.govRemove(3, 5);
		per.CreatARecord(9,2,8,2,3,"fracture"); //korrekt
		// per.CreatARecord(9,2,8,2,3," scolioses"); //där antigen läkare/nurse/patient finns inte
		//per.getlatestrecID();
		//per.getrecID(11);
	}
}
