import java.io.File;
import java.util.*;

public class Nurse extends User {

    private String div;

    public Nurse(String username, int id, String div) {
        super(username, id);
        this.div = div;
    }

    @Override
    public String userQueries() {
        
        StringBuilder sb = new StringBuilder();
        
        sb.append("Welcome! Your choices of action in this journal system are as folows:\n");
        sb.append("> Read journal: read <patient name>\n");
        sb.append("> Write to journal: write <patient name>\n");

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
                if(perms[2].equals(""+id)) return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }

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
                String[] perm = s.trim().split(",", 4); 
                if(perm[2].equals(""+id) || perm[3].equals(div) ) return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    //return list of files this patient has permission to read.
    @Override
    public File[] filesPermittedToRead(String patient) {
        File patFile = new File(journalPath+"/"+patient+"-"+div+".csv");
        if(!patFile.exists()) {
            return null;
        }
        File[] files = new File[1];
        files[0] = patFile;
        return files;
    }

    @Override
    public File[] filesPermittedToWrite(String patient) {
        File patFile = new File(journalPath+"/"+patient+"-"+div+".csv");
        if(!patFile.exists()) {
            return null;
        }
        File[] files = new File[1];
        files[0] = patFile;
        return files;
    }
    
}
