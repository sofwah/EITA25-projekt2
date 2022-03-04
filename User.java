import java.io.File;
import java.io.PrintWriter;
import java.util.*;
import java.io.FileWriter;
import java.text.SimpleDateFormat;  
import java.util.Date;

public abstract class User {

    protected String username;
    protected String type;
    protected int id;
    protected String journalPath = "db/Journals/";
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

    public File[] filesPermittedToRead(String patient) {
        return new File(journalPath).listFiles();
    }

    
    public File[] filesPermittedToWrite(String patient) {
        return new File(journalPath).listFiles();
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

    protected String readFile(String patient) {
        
        File[] files = filesPermittedToRead(patient);
        
        if(files == null || files.length == 0 ) {
            return "No permission to read any medical records.";
        }
        try {
            StringBuilder sb  = new StringBuilder();
            Scanner scan;
            for (File patFile : files) {
                scan = new Scanner(patFile);    

                while(scan.hasNext()) {
                    sb.append(scan.nextLine());
                }
                scan.close();
            }

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
            FileWriter printWriter = new FileWriter(patFile, true);
            StringBuilder sb = new StringBuilder();

            int patientId = getId(patient);
            if(personal instanceof Doctor) {
                sb.append("Entry: "+formattedTime+","+personal.getId()+","+id+","+patientId); //Doc nuerse div patient
            } else if (personal instanceof Nurse) {
                sb.append("Entry: "+formattedTime+","+id+","+personal.getId()+","+patientId); //Doc nuerse div patient
            }

            sb.append(msg);
            String res = sb.toString();
            printWriter.write(res);
            printWriter.flush();  
            printWriter.close();
            log("Wrote to Journal", patient);
            return "Wrote following to Journal:\n"+res;

        } catch (Exception e) {
            e.printStackTrace();
        }

        
        return "Something went wrong, could not write to file.";
    }

    protected String writeToFile(String patient, String msg, User personal ) {
        
        File[] files = filesPermittedToWrite(patient);


        if(files == null || files.length == 0) return "Permission denied";

        try {
            StringBuilder sb = new StringBuilder();
            String res = "";
            for (File patFile  : files ) {

                FileWriter printWriter = new FileWriter(patFile, true);    
    
                int patientId = getId(patient);
                if(personal instanceof Doctor) {
                    sb.append("Entry: "+formattedTime+","+personal.getId()+","+id+","+patientId); //Doc nuerse div patient
                } else if (personal instanceof Nurse) {
                    sb.append("Entry: "+formattedTime+","+id+","+personal.getId()+","+patientId); //Doc nuerse div patient
                }
    
                sb.append(","+msg);
                res = sb.toString();
                printWriter.write(res);
                printWriter.flush();  
                printWriter.close();
                log("Wrote to Journal", patient);
            }
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
            FileWriter w = new FileWriter(logFile,true);

            sb.append("Log: "+formattedTime+", By: "+username+", With id: " + id +", Operation: " + operation + ", Patient: "+patient);
            sb.append("\n================================================\n");
            
            w.write(sb.toString());
            w.flush();
            w.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String createJournal(String patient) {
        return "Operation not allowed.";
    }

    public String deleteJournal(String patient) {
        return "Operation not allowed.";
    } 
    
}
