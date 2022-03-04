import java.io.File;
import java.io.FileWriter;
import java.util.*;


public class Doctor extends User {

    private String div;

    public Doctor(String username, int id, String div) {
        super(username, id);
        this.div = div;
    }


    @Override
    public String userQueries() {
        
        StringBuilder sb = new StringBuilder();
        
        String s = "Welcome! Your choices of action in this journal system are as folows:\n Read journal: read patient name\n Write to journal: write patient name\n Create journal: create patient name\n";
        sb.append("Welcome! Your choices of action in this journal system are as folows:\n");
        sb.append("> Read journal: read <patient name>\n");
        sb.append("> Write to journal: write <patient name>\n");
        sb.append("> Create journal: create <patient name>\n");

        return s;
    }


    @Override 
    protected boolean permToWriteToJournal(String patient){
        try {
            File patFile = new File(journalPath+patient+".csv");

            Scanner scan = new Scanner(patFile);
            
            List<String> permList = new ArrayList<>();


            while(scan.hasNext()) {
                String temp = scan.nextLine();
                if(temp.startsWith("Entry:")) {
                    permList.add(temp.substring(6));
                }
            }

            scan.close();

            for(String s : permList) {
                String[] perms = s.trim().split(",", 4); 
                if(perms[1].equals(username)) return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }
    //check if Doc has right to read this file, 
    @Override
    protected boolean permToReadJournal(String patient) {

        try {
            File patFile = new File(journalPath+patient+".csv");

            Scanner scan = new Scanner(patFile);
            
            List<String> permList = new ArrayList<>();


            while(scan.hasNext()) {
                String temp = scan.nextLine();
                if(temp.startsWith("Entry:")) {
                    permList.add(temp.substring(6));
                }
            }

            scan.close();

            for(String s : permList) {
                String[] perms = s.trim().split(",", 4); 
                if(perms[1].equals(username) || perms[3].equals(div)) return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

    @Override
    public String createJournal(String patientJournal, String msg, User personal, String patient) {

        try {
            File patFile = new File(journalPath+patientJournal+".csv");
            if(patFile.createNewFile()) {
                FileWriter wr = new FileWriter(patFile, true);
                StringBuilder sb = new StringBuilder();
                int patientId = getId(patient);
                sb.append("Entry: " + formattedTime + "," + username + "," + personal + "," + patient + ", ");
                sb.append(msg);
                sb.append("\n================================================\n");

                String res = sb.toString();
                wr.write(res);
                wr.flush();
                wr.close();
                log("Created journal", patientJournal);
                return "Created new journal for "+patient;
            }
        } catch (Exception e) {
            
            e.printStackTrace();

        }

        return "Something went wrong, couldn't create journal for "+patient;
    }
}

/*
public class Doctor extends User {

    private int div;

    public Doctor(String username, int id, int div) {
        super(username, id);
        this.div = div;
    }


    @Override
    public String userQueries() {
        
        StringBuilder sb = new StringBuilder();
        
        String s = "Welcome! Your choices of action in this journal system are as folows:\n Read journal: read patient name\n Write to journal: write patient name\n Create journal: create patient name\n";
        sb.append("Welcome! Your choices of action in this journal system are as folows:\n");
        sb.append("> Read journal: read <patient name>\n");
        sb.append("> Write to journal: write <patient name>\n");
        sb.append("> Create journal: create <patient name>\n");

        return s;
    }


    @Override 
    protected boolean permToWriteToJournal(String patient){
        try {
            File patFile = new File(journalPath+patient+".csv");

            Scanner scan = new Scanner(patFile);
            
            List<String> permList = new ArrayList<>();


            while(scan.hasNext()) {
                String temp = scan.nextLine();
                if(temp.startsWith("Entry:")) {
                    permList.add(temp.substring(6));
                }
            }

            scan.close();

            for(String s : permList) {
                String[] perms = s.trim().split(",", 4); 
                if(perms[1].equals(""+id)) return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }
    //check if Doc has right to read this file, 
    @Override
    protected boolean permToReadJournal(String patient) {

        try {
            File patFile = new File(journalPath+patient+".csv");

            Scanner scan = new Scanner(patFile);
            
            List<String> permList = new ArrayList<>();


            while(scan.hasNext()) {
                String temp = scan.nextLine();
                if(temp.startsWith("Entry:")) {
                    permList.add(temp.substring(6));
                }
            }

            scan.close();

            for(String s : permList) {
                String[] perms = s.trim().split(","); 
                if(perms[1].equals(Integer.toString(id)) || perms[3].equals(""+div)) return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

    @Override
    public String createJournal(String patient, String msg, User personal) {
        try {
            File patFile = new File(journalPath+patient+".csv");
            if(patFile.createNewFile()) {
                FileWriter wr = new FileWriter(patFile, true);
                StringBuilder sb = new StringBuilder();
                int patientId = getId(patient);
                sb.append("Entry: " + formattedTime + "," + id + "," + personal.getId() + "," + div + "," + patientId);
                sb.append(msg);
                String res = sb.toString();
                wr.write(res);
                wr.flush();
                wr.close();
                log("Created journal", patient);
                return "Created new journal for "+patient;
            }
        } catch (Exception e) {
            
            e.printStackTrace();

        }

        return "Something went wrong, couldn't create journal for "+patient;

    }
}
*/