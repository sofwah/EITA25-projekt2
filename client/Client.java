package client;
//package src;

import java.net.*;
import java.io.*;
import javax.net.ssl.*;
import java.security.cert.X509Certificate;
import java.security.KeyStore;
import java.security.cert.*;
import java.util.Locale;

/*
 * This example shows how to set up a key manager to perform client
 * authentication.
 *
 * This program assumes that the client is not inside a firewall.
 * The application can be modified to connect to a src.server outside
 * the firewall by following SSLSocketClientWithTunneling.java.
 */

public class Client {
  public static void main(String[] args) throws Exception {
    String host = null;
    int port = -1;
    for (int i = 0; i < args.length; i++) {
      System.out.println("args[" + i + "] = " + args[i]);
    }
    if (args.length < 2) {
      System.out.println("USAGE: java client host port");
      System.exit(-1);
    }
    try { /* get input parameters */
      host = args[0];
      port = Integer.parseInt(args[1]);
    } catch (IllegalArgumentException e) {
      System.out.println("USAGE: java client host port");
      System.exit(-1);
    }

    String username = null;

    try {
      SSLSocketFactory factory = null;
      try {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Username: "); //Who is trying to access the system?
        username = read.readLine();

        System.out.print("Password: "); //The password to the keystore, 2FA
        char[] password = read.readLine().toCharArray();


        KeyStore ks = KeyStore.getInstance("JKS");
        KeyStore ts = KeyStore.getInstance("JKS");
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
        SSLContext ctx = SSLContext.getInstance("TLSv1.2");
        // keystore password (storepass)
        ks.load(new FileInputStream("client/" + username + "keystore"), password);
        // truststore password (storepass);
        ts.load(new FileInputStream("client/" + username + "truststore"), password);
        kmf.init(ks, password); // user password (keypass)
        tmf.init(ts); // keystore can be used as truststore here
        ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
        factory = ctx.getSocketFactory();
      } catch (Exception e) {
        System.out.println("Invalid username or password.");
        //throw new IOException(e.getMessage());
      }
      SSLSocket socket = (SSLSocket)factory.createSocket(host, port);
      System.out.println("\nsocket before handshake:\n" + socket + "\n");

      /*
       * send http request
       *
       * See SSLSocketClient.java for more information about why
       * there is a forced handshake here when using PrintWriters.
       */

      socket.startHandshake();
      SSLSession session = socket.getSession();
      Certificate[] cert = session.getPeerCertificates();
      String subject = ((X509Certificate) cert[0]).getSubjectX500Principal().getName();
      System.out.println("certificate name (subject DN field) on certificate received from src.server:\n" + subject + "\n");
      System.out.println("socket after handshake:\n" + socket + "\n");
      System.out.println("secure connection established\n\n");

      BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
      PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
      BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      String msg;
      boolean connection = true;

      System.out.print("sending username '" + username + "' to src.server...");
      send(username,out);
      System.out.println("done");

      System.out.println("Receiving response...");
      String received = receive(in);
      System.out.println("received '" + received + "' from src.server\n");

      while (connection){

       /*TODO: Här nedan ska vi ändra så att terminalen tar in lämpliga input av användaren, ex
        1. Inloggning
        2. Beroende på användartyp, presentera tillgängliga kommandon
        3. Skriv ut/genomför önskade ändringar
        4. Upprepa 2 och 3 tills användaren loggar ut ?
         */


        /*
        String userID = "";
        //control what kind of user this is, present choices as follows
        switch (username.toLowerCase(Locale.ROOT).substring(0,3)) {
          case "pat":
            System.out.println("Welcome " + username + "Choose one of the following optinons:\nRead Medical Record:read");
            //Send Message
            //revveive message
            userID = "pat"; 
            break;
          case "nur":
            System.out.println("Welcome " + username + "Choose one of the following optinons:\nRead: read\nWrite: write");
            userID = "nur";
            break;

          case "doc":
            System.out.println("Welcome " + username + "Choose one of the following optinons:\nRead\nWrite\nCreate");
            userID = "doc";
            break;

          case "gov":
            System.out.println("Welcome " + username + "Choose one of the following optinons:\nRead\nDelete");
            userID = "gov";
            break;
        
          default:
          /*
            System.out.println("Identification Error. Please login again");
            System.exit(-1);
          */ /*
            break;
        }

        */

        /*
        System.out.println("Quit: quit/q"); //If you want to leave application

        System.out.print(">");
        msg = read.readLine();



        if (msg.equalsIgnoreCase("quit") || msg.equalsIgnoreCase("q") ) {
          connection = false;
          System.out.println("quit 1");
          break;
        }


         */


        System.out.print(">");
        msg = read.readLine();
        if (msg.equalsIgnoreCase("quit") || msg.equalsIgnoreCase("q") ) {
          connection = false;
          System.out.println("Quitting program.");
          break;
        }


        System.out.print("sending '" + msg + "' to src.server...");
        send(msg,out);
        System.out.println("done");

        //Receive

        System.out.println("Receiving response...");
        received = receive(in);
        System.out.println("received '" + received + "' from src.server\n");

        //Send another message or quit
        /*
        msg = read.readLine();
        if (msg.equalsIgnoreCase("quit") || msg.equalsIgnoreCase("q") ) {
          connection = false;
          break;
        }

         */
         //Keep sending and receiving messages to/from server while client not quit.

        /*
        System.out.print("sending '" + msg + "' to src.server...");
        out.println(msg);
        out.flush();
        
        System.out.println("done");
        System.out.println("received '" + in.readLine() + "' from src.server\n");
        */

      }
      in.close();
      out.close();
      read.close();
      socket.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  } //main ends

  public static void send(String msg, PrintWriter out) {
    System.out.print("sending '" + msg + "' to src.server...");
    out.println(msg);
    out.flush();
  }

  public static String receive(BufferedReader in) throws IOException {
    String received = in.readLine();
    return received;
  }
}
