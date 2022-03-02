import java.io.*;
import java.net.*;
import javax.net.*;
import javax.net.ssl.*;

import client.Client;

import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Locale;

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

      String clientMsg = null;
      String username = null;
      Console console = System.console();
      String str;
      boolean firstTime = true;

      while ((clientMsg = receive(in)) != null) {
        /* TODO: det är här vi hanterar alla input från användaren
        Kontrollera kommando och gör ändringar/skriv ut data beroende på det
         */

         /* TODO
         
          Koppling har skett
          Få ut ID från CN -> Handler handler  = new Handler(String CN)
          handler.getPermissions(); //Vad personen får göra.
          handler.getJournal(); //Returnera journal
          handler.getJournal(Stirng name/ int id); //Returnera journal



          //Läsa journal, skriva journal, radera journal, 


          Får in username
          Kolla med ACl, Hämtar id och type. Returnerar en 
          
         */

        if (firstTime) {
          System.out.println("received '" + clientMsg + "' from client");

          String shortenedClientMsg = clientMsg.toLowerCase(Locale.ROOT).substring(0,3);
          if (shortenedClientMsg.equals("doc")) {
            str = "Would you like to create, edit, or read a medical record?";
          } else {
            str = "Not doctor";
          }


          System.out.print("sending '" + str + "' to client...");
          out.println(str);
          out.flush();
          System.out.println("done\n");

          firstTime = false;
        } else {

          //if kommando == ...

        }



        /*
        if (firstTime) {
          username = subject;
          System.out.println("First connection");

          try {
            AclHandler acl = new AclHandler(username);
            String type = acl.getType(username);

            if(type.equals("Doctor") || username == "Doctor1") {
              //Doctor doc = (Doctor ) acl.getUser();
              send("Would you like to create, edit, or read a medical record?", out);
              /*String str = console.readLine();
              if (str.toLowerCase().equals("create")) {
                //doc.createJournal(patientens namn);
              } else if (str.toLowerCase().equals("edit")) {
                //doc.writeToPatient(username);  //lista alternativen läkaren har att skriva till? (writeToPatient-metod)
              } else if (str.toLowerCase().equals("read")) {
                doc.readJournal(username);
              } else if (str.toLowerCase().equals("delete")) {
                System.out.println("You do not have the permission to do that.");
              } else {
                System.out.println(" ");
              }*/
/*
            } else if (type.equals("Nurse")) {
              //Nurse nur = (Nurse ) acl.getUser();
              send("Would you like to edit, or read a medical record?", out);

            } else if (type.equals("Patient")) {

              //Patient pat = (Patient ) acl.getUser();

              send("Would you like to read your medical record?", out);
              /*String str = console.readLine();
              if (str.toLowerCase().equals("yes") || str.toLowerCase().equals("y")) {
                pat.readJournal(username);
              } else {
                System.out.println(" ");
              } */
/*
            } else if (type.equals("Government")) {
              //Government gov = (Government ) acl.getUser();

              send("Would you like to read or delete a file?", out);
            }
          } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }

          firstTime = false;
        }
        */




/*
        String rev = new StringBuilder(clientMsg).reverse().toString();
        System.out.println("received '" + clientMsg + "' from client");
        System.out.print("sending '" + rev + "' to client...");
        out.println(rev);
        out.flush();
        System.out.println("done\n");

 */


      }
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

  public static void send(String msg, PrintWriter out) {
    System.out.print("sending '" + msg + "' to client...");
    out.println(msg);
    out.flush();
  }

  public static String receive(BufferedReader in) throws IOException {
    String received = in.readLine();
    return received;
  }
}

