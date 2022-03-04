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

    /*
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
    } */

    //return list of files this patient has permission to read.
    @Override
    public File[] filesPermittedToRead(String patient) {
        List<File> permittedFiles = new ArrayList<>();

        File patFile = new File(journalPath+"/"+patient+"-"+div+".csv");
        if(!patFile.exists()) {
            return null;
        } 
        permittedFiles.add(patFile);


        File[] existingFiles = new File(journalPath).listFiles((fileName) -> fileName.toString().contains(patient));

        for (File f : existingFiles) {

            if(f.toString().equals(patFile.toString())) {

            }else if(searchEntries(f)) {
                permittedFiles.add(f);
            }
        }
        File[] files = new File[permittedFiles.size()];
        for (int i = 0; i< permittedFiles.size(); i++) {
            files[i] = permittedFiles.get(i);
        }
        
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


    private boolean searchEntries(File f) {
        try {
            Scanner scan = new Scanner(f);

            while (scan.hasNext()) {
                String temp = scan.nextLine();
                if(temp.startsWith("Entry")) {
                    scan.close();
                    return temp.contains(username+"-"+id);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return false;
    }
    @Override
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
                if(patFile.toString().contains(div)) {

                    while(scan.hasNext()) {
                        sb.append(scan.nextLine());
                    }
                    scan.close();
                } else {

                    while(scan.hasNext()) {
                        String entry = scan.nextLine();
                        if(entry.startsWith("Entry:")) {
                            if(entry.contains(username+"-"+id)) {
                                sb.append(entry);
                                while(scan.hasNext() && !entry.startsWith("=")) {
                                    sb.append(scan.nextLine());
                                }
                            }
                        }
                    }
                }
            }

            log("Read Journal", patient);

            return sb.toString();
        } catch(Exception e) {
            e.printStackTrace();
        }

        return "Error: Something went wrong";
    }
    
}
