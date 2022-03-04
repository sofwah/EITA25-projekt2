import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Patient extends User {

    public Patient (String username, int id) {
        super(username, id);
    }

    @Override
    protected boolean permToReadJournal(String patientJournal) {

        try {
            File patFile = new File(journalPath+patientJournal+".csv");

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
                if(perms[3].equals(username)) return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return false;
        //return patient.equals(username);

    }

    @Override
    public String userQueries() {
        
        StringBuilder sb = new StringBuilder();
        
        sb.append("Welcome! Your choices of action in this journal system are as folows:\n");
        sb.append("> Read journal: read <patient name>\n");

        return sb.toString();
    }

    /*
    @Override
    protected String readJournal(String patient) {
        try {
            File journal = new File(journalPath + username+".csv");
            Scanner scan = new Scanner(journal);

            scan.nextLine();
    
            StringBuilder sb = new StringBuilder();
            while(scan.hasNext()) {
                sb.append(scan.nextLine());
            }
            scan.close();

            return sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return "Error: No journal found";
    }
    */
    
}
