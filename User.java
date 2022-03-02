import java.io.File;
import java.io.PrintWriter;
import java.util.*;

import java.text.SimpleDateFormat;  
import java.util.Date;

public abstract class User {

    protected String username;
    protected String type;
    protected int id;
    protected String journalPath = "db/Journals";
    private File logFile = new File("db/log.txt");

    protected SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
    protected Date date = new Date();
    protected String formattedTime = formatter.format(date);  


    public User(String username, int id) {

        this.username = username;
        this.id = id;
    }
    @Override
    public String toString() {
        return username;
    }

    public String userQueries() {
        return "Undefined user, can't find your permissions in system";
    }

    protected boolean permToWriteToJournal(String patient) {
        return false;
    }

    protected boolean permToReadJournal(String patient) {
        return false;
    }

    protected String readJournal(String patient) {
        
        if(!permToReadJournal(patient)) {
            return "Permission denied to journal of " + patient;
        }
        
        try {
            File patFile = new File(journalPath+patient+".csv");
            Scanner scan = new Scanner(patFile);

            StringBuilder sb  = new StringBuilder();

            while(scan.hasNext()) {
                sb.append(scan.nextLine());
            }
            scan.close();

            log("Read Journal", patient);

            return sb.toString();
        } catch(Exception e) {
            e.printStackTrace();
        }

        return "Error: Something went wrong";
    }

    protected String writeToJournal(String patient, String msg, User personal ) {
        
        if(!permToWriteToJournal(patient)) {
            return "Error: access denied to journal of "+patient;
        }

        try {
            File patFile = new File(journalPath+patient+".csv");
            PrintWriter printWriter = new PrintWriter(patFile);
            StringBuilder sb = new StringBuilder();

            int patientId = getId(patient);
            if(personal instanceof Doctor) {
                sb.append("Entry: "+formattedTime+","+personal.getId()+","+id+","+patientId); //Doc nuerse div patient
            } else if (personal instanceof Nurse) {
                sb.append("Entry: "+formattedTime+","+id+","+personal.getId()+","+patientId); //Doc nuerse div patient
            }

            sb.append(msg);
            String res = sb.toString();
            printWriter.println(res);
            printWriter.flush();  
            printWriter.close();
            log("Wrote to Journal", patient);
            return "Wrote following to Journal:\n"+res;

        } catch (Exception e) {
            e.printStackTrace();
        }

        
        return "Something went wrong, could not write to file.";
    }

    //To find id of user when no instance is present
    public int getId(String username) {
        
        AclHandler.findPersonId(username);
        
        return 0;
    }

    public int getId() {
        return this.id;
    }

    protected void log(String operation, String patient){
        try {
            StringBuilder sb = new StringBuilder();
            PrintWriter w = new PrintWriter(logFile);

            sb.append("Log: "+formattedTime+", By: "+username+", With id: " + id +", Operation: " + operation + ", Patient: "+patient);
            sb.append("\n================================================\n");
            
            w.println(sb.toString());
            w.flush();
            w.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
}
