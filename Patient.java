
import java.io.*;

public class Patient extends User {

    public Patient (String username, int id) {
        super(username, id);
    }

    /*
    @Override
    protected boolean permToReadJournal(String patient) {

        return patient.equals(username);

    }*/
    //return list of files this patient has permission to read.
    @Override
    public File[] filesPermittedToRead(String patient) {
        File dir = new File(journalPath);
        File[] fileList = dir.listFiles((path) -> path.toString().contains(patient));

        return fileList;
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
