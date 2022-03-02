import java.io.File;
import java.util.*;

public class Doctor extends User {

    private int div;

    public Doctor(String username, int id, int div) {
        super(username, id);
        this.div = div;
    }


    @Override
    public String userQueries() {
        
        StringBuilder sb = new StringBuilder();
        
        sb.append("Welcome! Your choices of action in this journal system are as folows:\n");
        sb.append("> Read journal: read <patient name>\n");
        sb.append("> Write to journal: write <patient name>\n");
        sb.append("> Create journal: create <patient name>\n");

        return sb.toString();
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
                String[] perms = s.trim().split(",", 4); 
                if(perms[1].equals(""+id) || perms[3].equals(""+div)) return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }


    public String createJournal(String patient) {

        try {
            File patFile = new File(journalPath+patient+".csv");
            if(patFile.createNewFile()) {
                log("Created journal", patient);
                return "Created new journal for "+patient;
            }
        } catch (Exception e) {
            
            e.printStackTrace();

        }

        return "Something went wrong, couldn't create journal for "+patient;

    }
}
