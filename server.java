import java.io.*;
import java.net.*;
import javax.net.*;
import javax.net.ssl.*;

import client.Client;

import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

import database.*;


public class server implements Runnable {
  private ServerSocket serverSocket = null;
  private static int numConnectedClients = 0;
  
  public server(ServerSocket ss) throws IOException {
    serverSocket = ss;
    newListener();
  }

  public void run() {
    try {
      SSLSocket socket=(SSLSocket)serverSocket.accept();
      newListener();
      SSLSession session = socket.getSession();
      Certificate[] cert = session.getPeerCertificates();
      String subject = ((X509Certificate) cert[0]).getSubjectX500Principal().getName();
      String issuer = ((X509Certificate) cert[0]).getIssuerX500Principal().getName();
      String serialNmbr = ((X509Certificate) cert[0]).getSerialNumber().toString();
      numConnectedClients++;
      System.out.println("client connected");
      System.out.println("client name (cert subject DN field): " + subject);
      System.out.println("Issuer (cert issuer DN field): " + issuer);
      System.out.println("Serial number of certificate " + serialNmbr);
      System.out.println(numConnectedClients + " concurrent connection(s)\n");

      PrintWriter out = null;
      BufferedReader in = null;
      out = new PrintWriter(socket.getOutputStream(), true);
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

      User user;
      while (true ) {
        try {
          String username = in.readLine().trim(); //hämtar username från client
          System.out.println(username);
          AclHandler acl = new AclHandler(username);
  
          user = acl.getUser();

          //Skicka valen till client
  
          out.print(user.userQueries());

          out.flush();
          break;
        } catch(Exception e) {
          e.printStackTrace();

        }

      }


      while(true) {

        String msg = in.readLine();
        String[] arr = msg.split(" ",5);
        if(arr[0].equals("read")) {
          //String patient = arr[4];
          String patientJournal = arr[1];
          String journal = user.readJournal(patientJournal);
          out.println(journal);
          out.flush();
        } else if(arr[0].equals("write")) {

          try {
            String patient = arr[3];
            String personal = arr[2];
            String note = arr[4];
            String patientJournal = arr[1];
            
            AclHandler personAcl = new AclHandler(personal);
            User pers = personAcl.getUser();
  
            String res = user.writeToJournal(patientJournal, note, pers, patient);
            out.println(res);
            out.flush();
            
          } catch (Exception e) {
            e.printStackTrace();
          }

        } else if (arr[0].equals("create")) {
          try {
          String patient = arr[3];
          String personal = arr[2];
          String note = arr[4];
          String patientJournal = arr[1];

          AclHandler personAcl = new AclHandler(personal);
          User pers = personAcl.getUser();

          String res = user.createJournal(patientJournal, note, pers, patient);
          out.println(res);
          out.flush();
        
          } catch (Exception e) {
            e.printStackTrace();
          }



        } else if (arr[0].equals("delete")) {

          String patient = arr[1];

          String res = user.deleteJournal(patient);
          out.println(res);
          out.flush();

        } else if(arr[0].equals("q")) {
          break;
        }

      }

      //Här börjar riktig kod 
      /*
      User user;
      while (true ) {
        try {
          String username = in.readLine().trim(); //hämtar username från client
          System.out.println(username);
          AclHandler acl = new AclHandler(username);
  
          user = acl.getUser();

          //Skicka valen till client
  
          out.println(user.userQueries());

          out.flush();
          break;
        } catch(Exception e) {
          e.printStackTrace();

        }

      }


      while(true) {

        String msg = in.readLine();
        String[] arr = msg.split(" ",4);
        if(arr[0].equals("read")) {
          String patient = arr[1];
          String journal = user.readJournal(patient);
          out.println(journal);
          out.flush();
        } else if(arr[0].equals("write")) {

          try {
            String patient = arr[1];
            String personal = arr[2];
            String note = arr[3];
            
            AclHandler personAcl = new AclHandler(personal);
            User pers = personAcl.getUser();
  
            String res = user.writeToJournal(patient, note, pers);
            out.println(res);
            out.flush();
            
          } catch (Exception e) {
            e.printStackTrace();
          }

        } else if (arr[0].equals("create")) {
          String patient = arr[1];
          String personal = arr[2];
          String note = arr[3];

          AclHandler personAcl;
          try {
            personAcl = new AclHandler(personal);
            User pers = personAcl.getUser();

            String res = user.createJournal(patient, note, pers);
            out.println(res);
            out.flush();
          } catch (Exception e) {
            e.printStackTrace();
          }

        } else if (arr[0].equals("delete")) {

          String patient = arr[1];

          String res = user.deleteJournal(patient);
          out.println(res);
          out.flush();

        } else if(arr[0].equals("q")) {
          break;
        }

      }
*/


/*

      String clientMsg = null;
      while ((clientMsg = in.readLine()) != null) {


          Console console = System.console();
          String username;

          username = clientMsg; 
		      try {


            User user;
            

            if(clientMsg.toLowerCase().substring(0, 3).equals("doc")) {
              user = (Doctor ) acl.getUser();
              //System.out.println("Would you like to create, edit, or read a medical record?");

              String msgToUser = user.userQueries(); 
              out.println(msgToUser);
              out.flush();

              String str = console.readLine();
              if (str.toLowerCase().equals("create")) {
                String patientName = console.readLine("Enter name of patient");
                ((Doctor) user).createJournal(patientName);
              } else if (str.toLowerCase().equals("edit")) {
                String patientName = console.readLine("Enter name of patient");
                String msg = console.readLine("Enter message for journal");
                String personal = console.readLine("Enter nurse/doctor");

                //doc.writeToJournal(patientName,msg,);  //lista alternativen läkaren har att skriva till? (writeToPatient-metod)
              } else if (str.toLowerCase().equals("read")) {
                user.readJournal(username);
              } else if (str.toLowerCase().equals("delete")) {
                System.out.println("You do not have the permission to do that.");
              } else {
                System.out.println(" ");
              }
              
            } else if (type.equals("Nurse")) {
              Nurse nur = (Nurse ) acl.getUser();
            } else if (type.equals("Patient")) {

              Patient pat = (Patient ) acl.getUser();

              System.out.println("Would you like to read your medical record?");
              String str = console.readLine();
              if (str.toLowerCase().equals("yes") || str.toLowerCase().equals("y")) {
                pat.readJournal(username);
              } else {
                System.out.println(" ");
              }
            } else if (type.equals("Government")) {
              Government gov = (Government ) acl.getUser();

              System.out.println("Would you like to read or delete a file?");
            }
			    } catch (Exception e) {
			    	// TODO Auto-generated catch block
				    e.printStackTrace();
		    	}

          


        String rev = new StringBuilder(clientMsg).reverse().toString();
        System.out.println("received '" + clientMsg + "' from client");
        System.out.print("sending '" + rev + "' to client...");
        out.println(rev);
        out.flush();
        System.out.println("done\n");
      }*/
      in.close();
      out.close();
      socket.close();
      numConnectedClients--;
      System.out.println("client disconnected");
      System.out.println(numConnectedClients + " concurrent connection(s)\n");
    } catch (IOException e) {
      System.out.println("Client died: " + e.getMessage());
      e.printStackTrace();
      return;
    } 
  }
  
  private void newListener() { (new Thread(this)).start(); } // calls run()
  public static void main(String args[]) {
    System.out.println("\nServer Started\n");
    int port = -1;
    if (args.length >= 1) {
      port = Integer.parseInt(args[0]);
    }
    String type = "TLSv1.2";
    try {
      ServerSocketFactory ssf = getServerSocketFactory(type);
      ServerSocket ss = ssf.createServerSocket(port);
      ((SSLServerSocket)ss).setNeedClientAuth(true); // enables client authentication
      new server(ss);
    } catch (IOException e) {
      System.out.println("Unable to start Server: " + e.getMessage());
      e.printStackTrace();
    }
  }

  private static ServerSocketFactory getServerSocketFactory(String type) {
    if (type.equals("TLSv1.2")) {
      SSLServerSocketFactory ssf = null;
      try { // set up key manager to perform src.server authentication
        SSLContext ctx = SSLContext.getInstance("TLSv1.2");
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
        KeyStore ks = KeyStore.getInstance("JKS");
        KeyStore ts = KeyStore.getInstance("JKS");
        char[] password = "password".toCharArray();
        // keystore password (storepass)
        ks.load(new FileInputStream("serverkeystore"), password);  
        // truststore password (storepass)
        ts.load(new FileInputStream("servertruststore"), password); 
        kmf.init(ks, password); // certificate password (keypass)
        tmf.init(ts);  // possible to use keystore as truststore here
        ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
        ssf = ctx.getServerSocketFactory();
        return ssf;
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      return ServerSocketFactory.getDefault();
    }
    return null;
  }
}
