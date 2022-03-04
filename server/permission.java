package server;


import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import database.*;


public class permission {

	private Connection conn;
	PreparedStatement readrec = null;
	ResultSet rs = null;
	Operationread or = null;
	
	Doctor d =new Doctor();
	Nurse n = new Nurse();
	patients p = new patients();
	Record r = new Record();
	govermentagency g= new govermentagency();
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
	
	/**
	 * HÄR BÖRJAR VILKOREN READ WRITE,EDIT OCH DELET
	 * 
	 */

	/**
	 * READ FILE SOM LÄKARE
	 * 
	 * @param id
	 * @return
	 */

	// hämta journalen för en given id/div
	public int getJournal(int id, int idtwo) {

		if (getID(id) == 0) { // check if input id exist in the system
			loggingunknown( "UKNOWN", "READ");
			System.out.println(" U dont exit");
			
		}

	
	 if(p.getpatID(id)== true || g.getgovID(id)==id ) { //check gov and patien id and print their records
			String getJournal = "select r.recID, p.name, d.name, n.name, di.name, r.notes from record r inner join patient p on(p.patID = r.patID) "
					+ "inner join doctors d on(d.docID=r.docID) inner join nurse n on (n.nurseID =r.nurseID) "
					+ "inner join division di on (di.divID =r.divID) where r.patID=? or r.govID=?";

			try {
				readrec = conn.prepareStatement(getJournal);

				readrec.setInt(1, id);
				readrec.setInt(2, id);
				rs = readrec.executeQuery();
				while (rs.next()) {

					
					System.out.println("#" + rs.getInt(1) + " Patient:" + rs.getString(2) + "Doctor: " + rs.getString(3)
							+ "Nurse: " + rs.getString(4) + "Division: " + rs.getString(5) + " Notes: "
							+ rs.getString(6));
					
				}
					if(p.getpatID(id)==true) {
						
						String uname =p.getpatname(id);
						int rec =r.getrecpatid(id);
						logging(rec, uname, "READ");
					}else {
						String uname =g.getgovname(id);
						//getgovtid när det fixas ska gå in där
						logging(id, uname, "READ"); 
					}
			
			} catch (Exception e) {
				System.out.println("error");
			}
			
		}else if(d.getdocID(id)==true){  // allow id and division that belong doctor 

			String getJournal = "select r.recid,p.name,d.name,n.name,di.name,r.notes from record r inner join nurse n on(n.nurseid=r.nurseid) inner join patient p on(p.patid=r.patid) inner join division di on(di.divid=r.divid) inner join doctors d on(d.docid=r.docid) where r.docid=? or r.divid = (select divid from doctors  where docid=?)";

			try {

				readrec = conn.prepareStatement(getJournal);

				readrec.setInt(1, id);
			readrec.setInt(2, idtwo);
				//readrec.setInt(3, id);

				rs = readrec.executeQuery();
				while (rs.next()) {

					
					System.out.println("#" + rs.getInt(1) + " Patient:" + rs.getString(2) + "Doctor: " + rs.getString(3)
							+ "Nurse: " + rs.getString(4) + "Division: " + rs.getString(5) + " Notes: "
							+ rs.getString(6));
					
				}
				String uname= d.getdocname(id);
				int recid = r.getrecdocid(id);
				logging(recid, uname, "READ");
			} catch (Exception e) {
				System.out.println("error");
			}
			
		}else  { // if not gov or patien, or doctor then probebly nurs and print their stuffout
			String getJournal = "select r.recid,p.name,d.name,n.name,di.name,r.notes from record r inner join nurse n on(n.nurseid=r.nurseid) inner join patient p on(p.patid=r.patid) inner join division di on(di.divid=r.divid) inner join doctors d on(d.docid=r.docid) where r.nurseid=? or r.divid = (select divid from nurse  where nurseid=?)";

			try {

				readrec = conn.prepareStatement(getJournal);

				readrec.setInt(1, id);
			readrec.setInt(2, idtwo);
				//readrec.setInt(3, id);

				rs = readrec.executeQuery();
				while (rs.next()) {

					
					System.out.println("#" + rs.getInt(1) + " Patient:" + rs.getString(2) + "Doctor: " + rs.getString(3)
							+ "Nurse: " + rs.getString(4) + "Division: " + rs.getString(5) + " Notes: "
							+ rs.getString(6));
					
				}
				String uname = n.getnurname(id);
				logging(id, uname, "READ");
			} catch (Exception e) {
				System.out.println("error");
			}
		}
		return 0;
	}

	/**
	 * EDIT/WRITE(fråga om man borde kolla också division)
	 * 
	 * @param args
	 */
	// FIX se till att record som de
	public int editjournal(int id, int docornursID) {

		if (getID(docornursID) == 0) {
			loggingunknown( "UKNOWN", "EDIT/WRITTE");
			System.out.println(" U SIMPLE DONT EXIST, so leave plz ");
		}

		if (d.getdocID(docornursID) == true) {

			try (PreparedStatement statement = conn
					.prepareStatement("UPDATE record SET notes = ? WHERE  recID=? and  docID=?")){

				statement.setString(1, "nytvå"); // se till att det är en inputdialog som ska mata in vad man vil
														// ländra
				statement.setInt(2, id); // se till att det input från den nurvandr aktiva användaren
				statement.setInt(3, docornursID);
				statement.executeUpdate();
				String username= d.getdocname(docornursID);
				logging(id,username,"EDIT");

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("error");
			}
			
		
		}else if (n.getnurID(docornursID) == true) {
				try (PreparedStatement statement = conn
					.prepareStatement("UPDATE record SET notes = ? WHERE  recID=? and nurseID=? ")) {

				statement.setString(1, "tb"); // se till att det är en inputdialog som ska mata in vad man vil
														// ländra
				statement.setInt(2, id); // se till att det input från den nurvandr aktiva användaren
				statement.setInt(3, docornursID);
				
				statement.executeUpdate();
				String username = n.getnurname(docornursID);
				logging(id,username,"EDIT");

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("error");
			}
			}else {
		
System.out.println(" denied access");
			}	
		
		return 0;

	}

	/**
	 * CREATE(borde se ifall läkaren har en patient med nummer )
	 * @param args
	 */

	public void CreatARecord(int patID, int docID, int nurseID, int divID, int govID, String notes) {
	
	if(d.getdocID(docID) == true && n.getnurID(nurseID)==true && p.getpatID(patID)== true) {
		
		try (PreparedStatement statement = conn.prepareStatement("INSERT INTO record (patID, docID, nurseID, divID, govID, notes) values(?,?,?,?,?,?)")) {

			statement.setInt(1, patID); 
			statement.setInt(2, docID); 
			statement.setInt(3, nurseID);
			statement.setInt(4, divID);
			statement.setInt(5, govID);
			statement.setString(6, notes);
			
			statement.executeUpdate();
		
				String username = d.getNameRec(docID);
				int i = r.getlatestrecID() ;
				logging(i,username,"CREATED");
			
		}catch (SQLException e) {
				e.printStackTrace();
				System.out.println("error2");
			}
			System.out.println("Create a record");
		}else {
			loggingunknown( "UKNOWN", "CREATE");
			System.out.println("U CANT KEEP STEPPING");
		}
		}


	
	/**
	 * DELETE
	 * @param govID
	 * @param recID
	 */

	public void govRemove(int govID, int recID) {

		if (g.getgovID(govID) != (govID)) {
			loggingunknown( "Unknown", "DELETE");
			System.out.println("MIND U BUIZZNES");
		} else if(r.getrecID(recID)== 1) {
			System.out.println("Record doesn't exist");
		}
		else {
			try (PreparedStatement statement2 = conn
					.prepareStatement("DELETE FROM record WHERE govID =? and recID=?")) {

				statement2.setInt(1, govID);
				statement2.setInt(2, recID);

				statement2.executeUpdate();
				String username = g.getgovname(govID);
				
				logging(recID, username, "DELET");
			//	System.out.println(govID + "has deleted the record" + recID);
			} catch (SQLException e) {
				// TODO Auto-generated catch block

				e.printStackTrace();
				System.out.println("erros");
			}
		}
	}

	/**
	 * AUDIT LOGA
	 */
	public void logging(int recid, String username, String parameter) {
			
		try {
			FileWriter bw = new FileWriter("audit/log.txt", true);
			bw.write( "Personsname: " + username + " try to " + parameter +" record: " + recid + ", time of the event: "+LocalDateTime.now() + "\n");
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void loggingunknown( String username, String parameter) {

		try {

			FileWriter bw = new FileWriter("audit/log.txt", true);

			bw.write("This person is: " + username + ", state u buzznes: " + parameter + ", time of the event: "+LocalDateTime.now() + "\n");
			bw.close();
			// System.out.println("Wrote to auditLog");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {

		permission per = new permission();
		// per.aretheyallowedtoread();
		per.getJournal(6,1); //doctortesting
		// per.getJournal(6,6,"damir");
		// per.getJournal(9,"max"); //patient testing
		//per.getdocdiv(1);
		//per.editjournal(3,1);
		// per.govRemove(3, 23);
		//per.CreatARecord(9,2,8,2,3,"baratestanya"); //korrekt
		// per.CreatARecord(9,2,8,2,3," scolioses"); //där antigen läkare/nurse/patient finns inte
		//per.getlatestrecID();
		//per.getrecID(11);
		
		//d.getdocID(1);
	}
}
