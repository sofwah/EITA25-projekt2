import java.util.*;
import java.io.File;

public class AclHandler {

    private String path = "db/acl.csv";
    private String username, type, div;
    private int id;

    public AclHandler(String username) throws Exception{

        String[] aclList = readAcl(path, username);

        this.username = username;
        this.id = Integer.parseInt(aclList[1]);
        this.div = aclList[2].trim();//Div is now a String.
        this.type = aclList[3];
    }

    public String getType(String username) throws Exception {
        String[] aclList = readAcl(path, username);
		if(aclList[2].equals("Doctor")) {
            type = "Doctor";
        } else if (aclList[2].equals("Nurse")){
            type = "Nurse";
        } else if (aclList[2].equals("Patient")){
            type = "Patient";
        } else if (aclList[2].equals("Government")){
            type = "Government";
        } 
		return type;
    }

    public User getUser() {

        if(type.equals("Patient")) {
            return new Patient(username,id);
        } else if(type.equals("Doctor")) {
            return new Doctor(username,id,div);
        } else if(type.equals("Nurse")) {
            return new Nurse(username, id, div);
        } else if(type.equals("Government")) {
            return new Government(username, id);
        }
        System.out.println("Detta gick fel");
        return null;
    }

    private String[] readAcl(String path, String username) throws Exception {

        File aclFile = new File(path);

        Scanner scanFile = new Scanner(aclFile);

        while(scanFile.hasNext()) {
            String[] temp = scanFile.nextLine().split(",",4);
            if (temp[0].equals(username)) {
                scanFile.close();
                return temp;
            }

        }

        scanFile.close();

        return null;
    }

    public static int findPersonId(String username){

        try {
            File aclFile = new File("db/acl.csv");

            Scanner scanFile = new Scanner(aclFile);
    
            while(scanFile.hasNext()) {
                String[] temp = scanFile.nextLine().split(",",3);
                if (temp[0].equals(username)) {
                    scanFile.close();
                    return Integer.parseInt(temp[1]);
                }
    
            }
            scanFile.close();
    
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }
    
}
