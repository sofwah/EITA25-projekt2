import java.io.File;
public class Government extends User {

    public Government(String username, int id) {
        super(username, id);
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
