import java.io.File;
import java.util.*;
import java.io.*;

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
                if(perms[1].equals(""+id)) return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }*/

    /*
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
                if(perms[1].equals(Integer.toString(id)) || perms[3].equals(div)) return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }*/

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

            } else if(searchEntries(f)) {
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

    /*
    @Override
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
*/
    @Override
    public String createFile(String patient, String user, String msg) {

        File patFile = new File(journalPath+patient+"-"+div+".csv");
        
        try {
            if(patFile.createNewFile()) {
                PrintWriter pw = new PrintWriter(patFile);
                AclHandler userAcl = new AclHandler(user);
                AclHandler patientAcl = new AclHandler(patient);
                User personal = userAcl.getUser();
                User patientUser = patientAcl.getUser();

                pw.println("Entry: "+formattedTime+";"+username+"-"+id+";"+user+"-"+personal.getId()+";"+patient+"-"+patientUser.getId()+";"+msg);
                pw.println("================================================================");
                pw.flush();
                pw.close();
                log("Created new journal", patient);
                return "Created file";
            }
                
        } catch (Exception e) {
            e.printStackTrace();

        }
        return "Something went wrong.";
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
