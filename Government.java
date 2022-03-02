import java.io.File;
public class Government extends User {

    public Government(String username, int id) {
        super(username, id);
    }

    @Override
    public String userQueries() {
        
        StringBuilder sb = new StringBuilder();
        
        sb.append("Welcome! Your choices of action in this journal system are as folows:\n");
        sb.append("> Read journal: read <patient name>\n");
        sb.append("> Delete journal: delete <patient name>\n");


        return sb.toString();
    }


    @Override
    protected boolean permToReadJournal(String patient) {
        return true;
    }

    @Override
    protected boolean permToWriteToJournal(String patient) {
        return false;
    }

    public String deleteJournal(String patient) {

        try {
            File patFile = new File(journalPath+patient+".csv");
            if(patFile.delete()) {
                log("Deleted file", patient);
                return "Deleted journal of " + patient;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return "Something went wrong. Couldn't delete file. Check that file exists";
    }
    
}
