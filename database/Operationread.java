package database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;

//Behövs dessa?
//import com.mysql.cj.jdbc.result.ResultSetMetaData;
//import com.mysql.cj.xdevapi.Statement;




public class Operationread {
	
	
	private static Connection conn;
	PreparedStatement readrec = null;
	ResultSet rs = null;
	database.ConnecttoDB dbConn = new database.ConnecttoDB();
	
	
	public Operationread(){

		conn = dbConn.ConnecttoDB();

	
	}

	public void accestoread() {
		

	 
		try {
			String getRecord = "select * from records";
			readrec = conn.prepareStatement(getRecord);
			rs = readrec.executeQuery();
			while (rs.next()) {
			
					System.out.println("#"+rs.getInt(1)+ ", Doctor: "+ rs.getString(2) +", Nurse: "+ rs.getString(3)+ ",Patient "+ rs.getString(4) + ";Department "+ rs.getString(5) + ",\n Note: "+ rs.getString(6));
				
				/*int id = rs.getInt(1);
			   String english = rs.getString("docname");
			    String spanish = rs.getString("nursename");
*/
				

			}
		} catch (Exception e) {
			System.out.println("error");
		}
	}
	
	//finns användaren i systemet
	public  int getID(int id)  {
		
	
			String getID = "select ID from person where ID = ?";
			try {
				readrec = conn.prepareStatement(getID);
				readrec.setInt(1, id);
			rs = readrec.executeQuery();
			while (rs.next()) {
			
					System.out.println(rs.getInt(1));
				// TODO Auto-generated catch block
				return rs.getInt(1);
			
			}
			
			}	catch (SQLException e) {
			e.printStackTrace();
				System.out.println("error");
	
			}
			
			System.out.println(" not there");
			return 0;
			
		

		}
			
public int getdocID(int id) {

	String getID = "select docID from doctors where docID = ?";
	try {
		readrec = conn.prepareStatement(getID);
		readrec.setInt(1, id);
	rs = readrec.executeQuery();
	while (rs.next()) {
	
			System.out.println(rs.getInt(1));
		// TODO Auto-generated catch block
		return rs.getInt(1);
	
	}
	
	}	catch (SQLException e) {
	e.printStackTrace();
		System.out.println("error");

	}
	
	System.out.println(" doctors not there");
	return 0;
	
	
}

public int getnurseID(int id) {

	String getID = "select nurseID from nurse where nurseID = ?";
	try {
		readrec = conn.prepareStatement(getID);
		readrec.setInt(1, id);
	rs = readrec.executeQuery();
	while (rs.next()) {
	
			System.out.println(rs.getInt(1));
		// TODO Auto-generated catch block
		return rs.getInt(1);
	
	}
	
	}	catch (SQLException e) {
	e.printStackTrace();
		System.out.println("error");

	}
	
	System.out.println(" nurse not  found there");
	return 0;
	
	
}

public int getgovID(int id) {

	String getID = "select id_gov from govermentpeople where id_gov = ?";
	try {
		readrec = conn.prepareStatement(getID);
		readrec.setInt(1, id);
	rs = readrec.executeQuery();
	while (rs.next()) {
	
			System.out.println(rs.getInt(1));
		// TODO Auto-generated catch block
		return rs.getInt(1);
	
	}
	
	}	catch (SQLException e) {
	e.printStackTrace();
		System.out.println("error");

	}
	
	System.out.println(" goverment not  found there");
	return 0;
	
	
}

//getdivision

	
	
	public static void main(String args[]) {
		
		Operationread opre = new Operationread();
		
	//	opre.getID(10);
		//opre.getdocID(1);
	//	opre.getnurseID(1);
	//	opre.getgovID(3);
		//opre.accestoread();
		
	}
}
