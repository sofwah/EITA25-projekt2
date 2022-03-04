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

/*
    @Override
    protected boolean permToReadJournal(String patient) {
        return true;
    }

    @Override
    protected boolean permToWriteToJournal(String patient) {
        return false;
    }

    @Override
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
*/
    @Override
    public String deleteFile(String patient) {

        try {
            File dir = new File(journalPath);
            File[] fileList = dir.listFiles((path) -> path.toString().contains(patient));


            for (File patFile : fileList) {
                if(patFile.delete()) {
                    log("Deleted file"+patFile.toString(), patient);
                }
            }
            return "Deleted journal of "+patient;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return "Something went wrong. Couldn't delete file. Check that file exists";
    }

    @Override
    public String deleteFile(String patient, String div) {

        try {

            File patFile = new File(journalPath+patient+"-"+div);

            if(patFile.delete()) {
                log("Deleted file"+patFile.toString(), patient);
            }
            return "Deleted journal of " + patient + ", div: " +div;

        } catch(Exception e) {
            e.printStackTrace();
        }
        return "Something went wrong. Couldn't delete file. Check that file exists";
    }

    //return list of files this patient has permission to read.
    @Override
    public File[] filesPermittedToRead(String patient) {
        File dir = new File(journalPath);
        File[] fileList = dir.listFiles((path) -> path.toString().contains(patient));

        return fileList;
    }

    
}
