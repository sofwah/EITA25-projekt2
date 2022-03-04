import java.util.*;
import java.io.File;
import java.io.PrintWriter;


public class Handler {
    
    private String username;
    private String[] ref;
    private int id;
    private List<String[]> acl = new ArrayList<String[]>();
    private String aclPath = "db/acl.csv";
    private int index;

    private int readInd, writeInd, createInd, deleteInd;
    private boolean read, write, create, delete;

    public Handler(String username) {
        try {
            this.acl = readAcl(aclPath); 
        } catch (Exception e) {
            System.out.println("Problem reading ACL-file, please check path or that file exists.");
            System.exit(-1);
        }

        this.username = username;
        
        this.index = getIndex();

        this.id = getId();
    }

    //Get unique ID from database.
    private int getId() {
        return Integer.parseInt(acl.get(index)[1]);
    }

    private int getIndex(){
        for(int i = 0; i < acl.size(); i++) {
            if(acl.get(i)[0].equals(username)) {
                return i;
            }
        }
        System.out.println("User with username "+username+" not found");
        return -1;
    }

    //Return string representing permissions for this user
    public String getPermissions() {
        
        StringBuilder sb = new StringBuilder();

        sb.append("Read: " + acl.get(index)[readInd]);
        sb.append("\nWrite: " + acl.get(index)[writeInd]);
        sb.append("\nCraete: "+ acl.get(index)[createInd]);
        sb.append("\nDelete: "+ acl.get(index)[deleteInd]);
        
        this.read = Boolean.parseBoolean(acl.get(index)[readInd]);
        this.write = Boolean.parseBoolean(acl.get(index)[writeInd]);
        this.delete = Boolean.parseBoolean(acl.get(index)[deleteInd]);
        this.create = Boolean.parseBoolean(acl.get(index)[createInd]);
        
        return sb.toString();
    }

    //Return journal of this user in correct format.
    public String getJournal(String path) throws Exception {
        File journal = new File(path);

        Scanner scan = new Scanner(journal);

        scan.nextLine();

        StringBuilder sb = new StringBuilder();
        while(scan.hasNext()) {
            sb.append(scan.nextLine());
            sb.append("===================================================");
        }



        return null;
    }

    private List<String[]> readAcl(String path) throws Exception {
        List<String[]> tempAclList = new ArrayList<String[]>();
        File aclFile = new File(path);

        Scanner scanFile = new Scanner(aclFile);

        ref = scanFile.nextLine().split(",",5);
        
        while(scanFile.hasNext()) {
            String[] temp = scanFile.nextLine().split(",",5);
            tempAclList.add(temp);
        }

        scanFile.close();

        return tempAclList;
    }

    public void writeToJournal(String s, File file) throws Exception {

        PrintWriter printWriter = new PrintWriter(file);
    }


}
