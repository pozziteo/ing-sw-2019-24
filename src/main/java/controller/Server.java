package controller;

import java.io.IOException;
import java.io.*;
import java.net.*;

public class Server {

    private int port;
    private ServerSocket ss;

    /**
     * This Method creates the server
     * @param port is the port required for creating the socket
     */
    public Server(int port){
        this.port = port;
    }

    /**
     * This method handles every new client that joins the server.
     * This method allows bidirectional communication between client and server
     * @throws IOException
     */
    public void startServer() throws IOException {

       try {
           ss = new ServerSocket(port);

           System.out.println("the server is ready on port: " + port);

           while (true) {
               Socket s = ss.accept();

               System.out.println("A new client is here.");

               InputStream input = s.getInputStream();
               OutputStream output = s.getOutputStream();

               PrintWriter writer = new PrintWriter(output, true);
               writer.println("Sent by The SERVER!");

           }
       } catch (IOException e){
           System.out.println(e.getMessage());
           e.printStackTrace();
       } finally {
           ss.close();
       }
    }


    /**
     * Main Method
     * @param args
     * @throws IOException
     */

    public static void main(String[] args) throws IOException{

        Server server = new Server(6666);
        System.out.println("the server is running...");

        server.startServer();

    }
}
