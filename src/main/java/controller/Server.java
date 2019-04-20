package controller;

import java.io.IOException;
import java.io.*;
import java.net.*;
import java.util.Scanner;

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
     * This method starts the server;
     * It also handles every new client that joins the server
     * and allows bidirectional communication between client and server
     * @throws IOException
     */
    public void startServer() throws IOException {

        int i = 1;

        try {
           ss = new ServerSocket(port);
           System.out.println("the server is ready on port: " + port);



           while (i<5) {

               Socket s = ss.accept();
               Scanner input = new Scanner(s.getInputStream());
               PrintWriter output = new PrintWriter(s.getOutputStream());
               Scanner stdin = new Scanner(System.in);
               String sInput= stdin.nextLine();
               System.out.println("A new client is here: Client" +i);

               String text = input.nextLine();
               if (text.equals("end")){break;}

               System.out.println("From Client"+i +": " + text);



               output.println("From Server:" + sInput);
               output.flush();


             /*  PrintWriter writer = new PrintWriter(output, true);
               System.out.println(input);
               writer.println("welcome Client" +i);
               writer.println("Sent by The SERVER!");

                 InputStream in = s.getInputStream();
               BufferedReader reader = new BufferedReader(new InputStreamReader(in));
               String text = reader.readLine();
               System.out.println(text);*/

               i = i+1;
               /*input.close();
               output.close();
               s.close();*/
           }




       } catch (IOException e){
           System.out.println(e.getMessage());
           e.printStackTrace();
       }

        ss.close();

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
